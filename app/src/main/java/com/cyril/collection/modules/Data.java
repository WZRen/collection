package com.cyril.collection.modules;

/**
 * Created by cyril on 2017/1/13.
 */
public class Data<T> {
    private UserInfo<T> userInfo;
    private boolean isLogin;
    private int unread;
    private int untast;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public int getUntast() {
        return untast;
    }

    public void setUntast(int untast) {
        this.untast = untast;
    }
}
