package sj.com.voiceclock.sutil;


import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonUtil {


    /**
     *
     *
     * @param obj obj对象
     * @return 返回jsonobject
     */
    public static org.json.JSONObject obj2JsonObj(Object obj) {
        org.json.JSONObject jobj = new org.json.JSONObject();
        try {
            Class c = obj.getClass();
            Method m[] = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++) {
                if (m[i].getName().indexOf("get") == 0 && m[i].getName().indexOf("DB")==-1) {

                    Log.i("o2m", "方法名：" + m[i].getName().toString().replace("get", "") + m[i].getGenericReturnType() + " 值：" + m[i].invoke(obj));
                    if (m[i].invoke(obj) != null)
                        if (m[i].getGenericReturnType().toString().equals("class java.util.Date")) {
                            Date val = (Date) m[i].invoke(obj);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            jobj.put(m[i].getName().toString().replace("get", "").toLowerCase(), sdf.format(val));
                        } else {
                            jobj.put(m[i].getName().toString().replace("get", "").toLowerCase(), m[i].invoke(obj, new Object[0]).toString());
                        }
                }
            }
        } catch (Throwable e) {
            System.err.println(e);
        }
        return jobj;
    }

    /**
     * 按章节点得到相应的内容
     *
     * @param jsonString json字符串
     * @param note       节点
     * @return 节点对应的内容
     */
    public static String getNoteJson(String jsonString, String note) {
        if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
            try {
                JSONObject object = JSONObject.parseObject(jsonString);
                return object.getString(note);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 按章节点得到相应的内容
     *
     * @param jsonString json字符串
     * @param note       节点
     * @return 节点对应的内容
     */
    public static int getNoteInt(String jsonString, String note) {
        int code = -1;
        if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
            try {
                JSONObject object = JSONObject.parseObject(jsonString);
                return object.getIntValue(note);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return code;
    }



    /**
     * 把相对应节点的内容封装为对象
     *
     * @param jsonString json字符串
     * @param beanClazz  要封装成的目标对象
     * @param note       节点
     * @return 目标对象
     */
    public static <T> T parserObject(String jsonString, String note, Class<T> beanClazz) {
        if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
            try {
                String noteJson = getNoteJson(jsonString, note);
                return JSON.parseObject(noteJson, beanClazz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 按照节点得到节点内容，转化为一个数组
     *
     * @param jsonString json字符串
     * @param beanClazz  集合里存入的数据对象
     * @param note       节点
     * @return 含有目标对象的集合
     */
    public static <T> List<T> parserArray(String jsonString, String note, Class<T> beanClazz) {
        if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
            List<T> objects;
            try {
                String noteJson = getNoteJson(jsonString, note);
                if (!TextUtils.isEmpty(noteJson)) {
                    objects = JSON.parseArray(noteJson, beanClazz);
                    return objects;
                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 把对象转化为json字符串
     *
     * @param obj 要转化的对象
     * @return 返回json字符串
     */
    public static String toJsonString(Object obj) {
        if (obj != null) {
            try {
                return JSON.toJSONStringWithDateFormat(obj,"yyyy-MM-dd HH:mm:ss");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("对象不能为空");
        }
        return null;
    }

    /**
     * 把json封装为Map 对象数组
     *
     * @param jsonString
     * @param beanClazz  要封装成的目标对象
     * @return
     */
    public static <T> T jsonString2Bean(String jsonString, Class<T> beanClazz) {
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                return JSON.parseObject(jsonString, beanClazz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 把jsonString转化为多个bean对象数组
     *
     * @param jsonString
     * @param beanClazz
     * @return
     */
    public static <T> List<T> jsonString2Beans(String jsonString, Class<T> beanClazz) {
        if (!TextUtils.isEmpty(jsonString)) {
            List<T> objects;
            try {
                objects = JSON.parseArray(jsonString, beanClazz);
                return objects;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将json 数组转换为Map 对象
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonString2Map(String jsonString) {
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                return JSON.parseObject(jsonString, new TypeReference<Map<String, Object>>() {
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     * 把相对应节点的内容封装为Map 对象
     *
     * @param jsonString
     * @param note       节点
     * @return
     */
    public static Map<String, Object> parseMap(String jsonString, String note) {
        if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
            String noteJson = getNoteJson(jsonString, note);
            if (!TextUtils.isEmpty(noteJson)) {
                try {
                    return JSON.parseObject(noteJson, new TypeReference<Map<String, Object>>() {
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 把相对应节点的内容封装为Map 对象数组
     *
     * @param jsonString
     * @param note       节点
     * @return
     */
    public static List<Map<String, Object>> parseMaps(String jsonString, String note) {
        if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
            String noteJson = getNoteJson(jsonString, note);
            if (!TextUtils.isEmpty(noteJson)) {
                try {
                    return JSON.parseObject(noteJson, new TypeReference<List<Map<String, Object>>>() {});
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        return null;
    }
}
