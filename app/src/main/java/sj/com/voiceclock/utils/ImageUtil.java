package sj.com.voiceclock.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
////import com.orhanobut.logger.Logger;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMMessage;
//import com.hyphenate.util.*;
//import com.zs.imakemobile.R;
//import com.zs.imakemobile.adapter.MessageAdapter;

/**
 * Created with IntelliJ IDEA. User: Minsanity Date: 2014/12/29 Time: 10:07
 * 相关图片操作的工具类
 */
public class ImageUtil {
//	public static String getImagePath(String remoteUrl) {
//		String imageName = remoteUrl.substring(remoteUrl.lastIndexOf("/") + 1,
//				remoteUrl.length());
////		String path = com.easemob.util.PathUtil.getInstance().getImagePath() + "/" + imageName;
////		Logger.d("msg", "image path:" + path);
//		return path;
//	}



	public static Bitmap readBitMap(Context context, int resId){

		BitmapFactory.Options opt = new BitmapFactory.Options();

		opt.inPreferredConfig = Config.RGB_565;


		// 获取资源图片

		InputStream is = context.getResources().openRawResource(resId);

		return BitmapFactory.decodeStream(is, null, opt);


	}
	public static BitmapDrawable readBitMapDrawable(Context context, int resId){


		return  new BitmapDrawable(context.getResources(),readBitMap(context,resId));


	}


	/**
	 * 获取图片文件的信息，是否旋转了90度，如果是则反转
	 *
	 * @param bitmap
	 *            需要旋转的图片
	 * @param path
	 *            图片的路径
	 */
	public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
		int degree = getPicRotate(path);
		if (degree != 0) {
			Matrix m = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			m.setRotate(degree); // 旋转angle度
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
		}
		return bitmap;
	}

	public static Bitmap convertGreyImg(Bitmap img) {
		if (img == null)
			return null;
		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];
				int alpha = (grey & 0xff000000);
				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	/**
	 * 读取图片文件旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return 图片旋转的角度
	 */
	public static int getPicRotate(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * Drawable 转成Bitmap
	 *
	 * @param drawable
	 *            被转的Drawable
	 * @return bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	/**
	 * Bitmap转成Drawable
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap, Context context) {
		return new BitmapDrawable(context.getResources(), bitmap);
	}

	public static byte[] bmpToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 60, baos);
		return baos.toByteArray();
	}

	/**
	 * 根据路径获得图片并压缩返回bitmap用于显示
	 *
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 计算图片的缩放值
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * bitmap转换stream
	 *
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapStream(Bitmap bitmap,
										 Bitmap.CompressFormat paramCompressFormat, int quality) {
		if (bitmap != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(paramCompressFormat, quality, stream);
			return stream.toByteArray();
		} else {
			return null;
		}
	}

	/**
	 * Bitmap转换成byte数组
	 */
	public static byte[] File2Bytes(File file) {

		byte[] bytes = null;

		InputStream is;
		try {
			is = new FileInputStream(file);

			bytes = new byte[is.available()];

			is.read(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 有些控件UI设计师没有给点击效果，这个和是否敬业有关，但是作为Awesome developer. 对自己的产品还是要有爱。他们不做，我们做。
	 * <p/>
	 * 仿Android4.0以上的Holo主题，点击之后通过Alpha值的改变来 区分点击效果。 <br />
	 * <b>API17以下的，LayoutButtonDrawable必须只能接受一个BitmapDrawable</b>
	 */
	public static final class LayerButtonDrawable extends LayerDrawable {

		// 这个颜色提供给点击之后的的状态
		private LightingColorFilter pressedFilter = new LightingColorFilter(
				Color.LTGRAY, 1);

		// Alpha值 当这个控件被disable
		private int disabledAlpha = 100;
		// Alpha值 enable
		private int fullAlpha = 255;

		public LayerButtonDrawable(Drawable d) {
			super(new Drawable[] { d });
		}

		@Override
		public boolean isStateful() {
			return true;
		}

		@Override
		protected boolean onStateChange(int[] states) {
			boolean enabled = false;
			boolean pressed = false;

			for (int state : states) {
				if (state == android.R.attr.state_enabled)
					enabled = true;
				else if (state == android.R.attr.state_pressed)
					pressed = true;
			}
			mutate();
			if (enabled && pressed) { // 当正常状态点击
				setColorFilter(pressedFilter);
			} else if (!enabled) {
				setColorFilter(null);
				setAlpha(disabledAlpha);
			} else {
				setColorFilter(null);
				setAlpha(fullAlpha);
			}
			invalidateSelf();
			return super.onStateChange(states);
		}

		@Override
		public Drawable mutate() {
			return super.mutate();
		}
	}

	/**
	 * UI设计的界面有各种圆角，有的上面有下面木有，有的下面有上面没有。照老办法drawable目录中会多出很多xml。 developer自有妙计
	 */
	public static final class RadiusDrawable extends ShapeDrawable {

		/**
		 * 构建一个带圆角的Drawable
		 *
		 * @param color
		 *            背景纯色（这种圆角背景图片一般都是纯色）
		 * @param topLeft
		 *            左上方圆角
		 * @param topRight
		 *            右上方圆角
		 * @param bottomLeft
		 *            左下方圆角
		 * @param bottomRight
		 *            右下方圆角
		 */
		public RadiusDrawable(int color, float topLeft, float topRight,
							  float bottomLeft, float bottomRight) {

			RoundRectShape roundRectShape = new RoundRectShape(new float[] {
					topLeft, topLeft, topRight, topRight, bottomLeft,
					bottomLeft, bottomRight, bottomRight }, null, null);

			getPaint().setColor(color);
			setShape(roundRectShape);
		}

		@Override
		public int getIntrinsicHeight() {
			Rect rect = getBounds();
			if (rect != null) {
				return rect.height();
			}
			return super.getIntrinsicHeight();
		}

		@Override
		public int getIntrinsicWidth() {
			Rect rect = getBounds();
			if (rect != null) {
				return rect.width();
			}
			return super.getIntrinsicWidth();
		}
	}
	/**
	 * 通过比例对图片进行压缩
	 */
	public static Bitmap getBitmap(String path, int height, int width) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		// 只返回图片的相关信息,不返回
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		int dw = (int) Math.ceil(options.outWidth / width);
		int dh = (int) Math.ceil(options.outHeight / height);
		if (dw > 1 && dh > 1) {
			if (dw > dh) {
				options.inSampleSize = dw;
			} else {
				options.inSampleSize = dh;
			}
		}
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap getBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

//	/**
//	 * 用于将给定的内容生成成一维码 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
//	 *
//	 * @param content
//	 *            将要生成一维码的内容
//	 * @return 返回生成好的一维码bitmap
//	 * @throws com.google.zxing.WriterException
//	 *             WriterException异常
//	 */
//	public static Bitmap createBarCode(String content) throws WriterException {
//		// 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
//		BitMatrix matrix = new MultiFormatWriter().encode(content,
//				BarcodeFormat.CODE_128, 500, 200);
//		int width = matrix.getWidth();
//		int height = matrix.getHeight();
//		int[] pixels = new int[width * height];
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (matrix.get(x, y)) {
//					pixels[y * width + x] = 0xff000000;
//				}else{
//					pixels[y * width + x] = 0xffffffff;
//				}
//			}
//		}

//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Config.ARGB_8888);
//		// 通过像素数组生成bitmap,具体参考api
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		Bitmap bgbitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() + 20, Config.ARGB_8888);
//		Canvas canvas = new Canvas(bgbitmap);
//		Paint paint = new Paint();
//		paint.setStyle(Style.FILL);
//		paint.setColor(0xffffffff);
//		canvas.drawRect(new Rect(0, 0, bgbitmap.getWidth(), bgbitmap.getHeight()), paint);
//		canvas.drawBitmap(bitmap, 0, 10, paint);
//		return bgbitmap;
//	}

//	/**
//	 * 将指定的内容生成成二维码
//	 *
//	 * @param content
//	 *            将要生成二维码的内容
//	 * @return 返回生成好的二维码事件
//	 * @throws WriterException
//	 *             WriterException异常
//	 */
//	public static Bitmap createQrCode(String content) throws WriterException {
//		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
//		BitMatrix matrix = new MultiFormatWriter().encode(content,
//				BarcodeFormat.QR_CODE, 600, 600);
//		int width = matrix.getWidth();
//		int height = matrix.getHeight();
//		// 二维矩阵转为一维像素数组,也就是一直横着排了
//		int[] pixels = new int[width * height];
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (matrix.get(x, y)) {
//					pixels[y * width + x] = 0xff000000;
//				}
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Config.ARGB_8888);
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		Bitmap bgbitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() + 20, Config.ARGB_8888);
//		Canvas canvas = new Canvas(bgbitmap);
//		Paint paint = new Paint();
//		paint.setStyle(Style.FILL);
//		paint.setColor(0xffffffff);
//		canvas.drawRect(new Rect(0, 0, bgbitmap.getWidth(), bgbitmap.getHeight()), paint);
//		canvas.drawBitmap(bitmap, 0, 10, paint);
//		// 通过像素数组生成bitmap,具体参考api
//		return bgbitmap;
//	}

	/**
	 * 根据路径删除图片
	 *
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		if (path != null && !path.equals("")) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
	}
//
//	/**
//	 * 返回百度地图上的marker图标，带数字
//	 *
//	 * @param bgbitmap
//	 * @param index
//	 * @return
//	 */
//	public static Bitmap createIndexMarkerIcon(Context context,Bitmap bgbitmap, int index,
//											   float scale) {
//		Bitmap b = bgbitmap.copy(Config.ARGB_8888, true);
//
//		Canvas c = new Canvas(b);
//		Paint p = new Paint();
//		p.setColor(0xffffffff);
//		p.setAntiAlias(true);
//		int size = Float.valueOf(context.getResources().getDimension(R.dimen.marker_text_size)).intValue();
//		int top = Float.valueOf(context.getResources().getDimension(R.dimen.marker_text_top)).intValue();
//		p.setTextSize(size);
//		p.setTextAlign(Align.LEFT);
//		float w = p.measureText(index + "");
//		c.drawText(index + "", (b.getWidth() - w) / 2-3, top, p);
//		if(scale != 1){
//			Matrix matrix = new Matrix();
//			matrix.postScale(scale, scale); // 长和宽放大缩小的比例
//			b = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
//					b.getHeight(), matrix, true);
//		}
//		return b;
//	}
	public static Bitmap scaleBitmap(Bitmap bitmap, float sw,float sh) {
		Matrix matrix = new Matrix();
		matrix.postScale(sw, sh); //长和宽放大缩小的比例
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	/**
	 * 根据路径获得图片并压缩返回bitmap用于显示
	 *
	 * @param filePath
	 * @return
	 */
	//显示九宫格图片时 图片设置到400~800px,240~480px;
	public static Bitmap getSmallBitmap(String filePath,int height,int width) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, height, width);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}
	public static Bitmap rotateBitmapByDegree(Bitmap bm, String path) {
		int degree = getPicRotate(path);
		Bitmap returnBm = null;
		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			if (degree != 0)
				returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;

	}

	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * 此方法在获取网路视频的缩略图时只能在2.x版本上使用
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width,
										   int height, int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		File f = new File(videoPath);
		String tname = f.getName()+"_thum";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f.getParent()+"/"+tname);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out !=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return BitmapFactory.decodeFile(f.getParent()+"/"+tname);
	}
	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * 此方法在获取网路视频的缩略图时只能在2.x版本上使用
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	public static String getVideoThumbnailPath(String videoPath, int width,
											   int height, int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		File f = new File(videoPath);
		String tname = f.getName()+"_thum";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f.getParent()+"/"+tname);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out !=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return f.getParent()+"/"+tname;
	}




	/** 水平方向模糊度 */
	private static float hRadius = 3;
	/** 竖直方向模糊度 */
	private static float vRadius = 3;
	/** 模糊迭代度 */
	private static int iterations =3;

	/**
	 * 图片高斯模糊处理
	 * 
	 */
	public static Drawable BlurImages(Context context,Bitmap bmp) {

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < iterations; i++) {
			blur(inPixels, outPixels, width, height, hRadius);
			blur(outPixels, inPixels, height, width, vRadius);
		}
		blurFractional(inPixels, outPixels, width, height, hRadius);
		blurFractional(outPixels, inPixels, height, width, vRadius);
		bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
		Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
		return drawable;
	}

	/**
	 * 图片高斯模糊算法
	 */
	public static void blur(int[] in, int[] out, int width, int height, float radius) {
		int widthMinus1 = width - 1;
		int r = (int) radius;
		int tableSize = 2 * r + 1;
		int divide[] = new int[256 * tableSize];

		for (int i = 0; i < 256 * tableSize; i++)
			divide[i] = i / tableSize;

		int inIndex = 0;

		for (int y = 0; y < height; y++) {
			int outIndex = y;
			int ta = 0, tr = 0, tg = 0, tb = 0;

			for (int i = -r; i <= r; i++) {
				int rgb = in[inIndex + clamp(i, 0, width - 1)];
				ta += (rgb >> 24) & 0xff;
				tr += (rgb >> 16) & 0xff;
				tg += (rgb >> 8) & 0xff;
				tb += rgb & 0xff;
			}

			for (int x = 0; x < width; x++) {
				out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];

				int i1 = x + r + 1;
				if (i1 > widthMinus1)
					i1 = widthMinus1;
				int i2 = x - r;
				if (i2 < 0)
					i2 = 0;
				int rgb1 = in[inIndex + i1];
				int rgb2 = in[inIndex + i2];

				ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
				tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
				tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
				tb += (rgb1 & 0xff) - (rgb2 & 0xff);
				outIndex += height;
			}
			inIndex += width;
		}
	}

	/**
	 * 图片高斯模糊算法
	 * 
	 */
	public static void blurFractional(int[] in, int[] out, int width, int height, float radius) {
		radius -= (int) radius;
		float f = 1.0f / (1 + 2 * radius);
		int inIndex = 0;

		for (int y = 0; y < height; y++) {
			int outIndex = y;

			out[outIndex] = in[0];
			outIndex += height;
			for (int x = 1; x < width - 1; x++) {
				int i = inIndex + x;
				int rgb1 = in[i - 1];
				int rgb2 = in[i];
				int rgb3 = in[i + 1];

				int a1 = (rgb1 >> 24) & 0xff;
				int r1 = (rgb1 >> 16) & 0xff;
				int g1 = (rgb1 >> 8) & 0xff;
				int b1 = rgb1 & 0xff;
				int a2 = (rgb2 >> 24) & 0xff;
				int r2 = (rgb2 >> 16) & 0xff;
				int g2 = (rgb2 >> 8) & 0xff;
				int b2 = rgb2 & 0xff;
				int a3 = (rgb3 >> 24) & 0xff;
				int r3 = (rgb3 >> 16) & 0xff;
				int g3 = (rgb3 >> 8) & 0xff;
				int b3 = rgb3 & 0xff;
				a1 = a2 + (int) ((a1 + a3) * radius);
				r1 = r2 + (int) ((r1 + r3) * radius);
				g1 = g2 + (int) ((g1 + g3) * radius);
				b1 = b2 + (int) ((b1 + b3) * radius);
				a1 *= f;
				r1 *= f;
				g1 *= f;
				b1 *= f;
				out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
				outIndex += height;
			}
			out[outIndex] = in[width - 1];
			inIndex += width;
		}
	}
	public static int clamp(int x, int a, int b) {
		return (x < a) ? a : (x > b) ? b : x;
	}

	/**
	 * 按照指定长宽压缩
	 * @param srcBitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap bitmapZoomBySize(Bitmap srcBitmap,int newWidth,int newHeight)
	{
		int srcWidth = srcBitmap.getWidth();
		int srcHeight = srcBitmap.getHeight();

		float scaleWidth = ((float) newWidth) / srcWidth;
		float scaleHeight = ((float) newHeight) / srcHeight;

		return scaleBitmap(srcBitmap, scaleWidth, scaleHeight);
	}


	/**
	 * @param IdentifyID  文件识别符
	 * @param sName  现有文件名
	 * @return  返回文件名
	 * 例子1： 传入 1002为识别符  现有文件名为 1002.mp4  那么返回 1002~1.mp4
	 * 例子2： 传入 1002为识别符  现有文件名为 ""（没保存过）  那么返回 1002.mp4
	 * 例子3： 传入 1002为识别符  现有文件名为 1002～1.mp4（没保存过）  那么返回 1002.mp4
	 */

	public static   String GetIdentifyImgName(String IdentifyID,String sName)
	{
		String sRet = sName;
		if (sName.equals(IdentifyID + ""))   //等于标志 用.1 作为增量
		{
			sRet = IdentifyID + "~1";
		}
		else if (TextUtils.isEmpty(sName))    //没有文件名 用标志作为文件名
		{
			sRet = IdentifyID + "";
		}
		else         //递增增量
		{
			sRet = IdentifyID + "~" + (Long.parseLong( sName.substring(sName.indexOf("~") + 1 )) + 1 );

		}
		return  sRet;
	}

	public static byte[] getBytes(File file) {
		int size = (int) file.length();
		byte[] bytes = new byte[size];
		try {
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
			buf.read(bytes, 0, bytes.length);
			buf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
}
