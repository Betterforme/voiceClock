package sj.com.voiceclock.model.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity
public class User {
    private Long userid;

    private String username;

    private String password;

    private String city;

    private String age;

    private String sex;

    private Date dob;

    private String email;

    private String userIcon;

    private Short status;

    private Date lastLoginTime;

    @Generated(hash = 1680713196)
    public User(Long userid, String username, String password, String city,
            String age, String sex, Date dob, String email, String userIcon,
            Short status, Date lastLoginTime) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.city = city;
        this.age = age;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.userIcon = userIcon;
        this.status = status;
        this.lastLoginTime = lastLoginTime;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}