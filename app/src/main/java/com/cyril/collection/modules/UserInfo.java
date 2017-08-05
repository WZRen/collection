package com.cyril.collection.modules;

/**
 * Created by cyril on 2017/1/13.
 */
public class UserInfo<T> {
    private String uid;
    private String email;
    private String qq;
    private String ctime;
    private String lastLogin;
    private T gems;

    public T getGems() {
        return gems;
    }

    public void setGems(T gems) {
        this.gems = gems;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
