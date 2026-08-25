package com.boxbrapks.model;

import org.json.JSONObject;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;

/* JADX INFO: loaded from: classes.dex */
public class UserAccountModel implements Cloneable {
    public int accessStreamId = -1;
    public String accountName;
    public String accountType;

    /* JADX INFO: renamed from: id */
    public long f104id;
    public String m3uEpgUrl;
    public String m3uUrl;
    public String password;
    public String url;
    public JSONObject userInfo;
    public String username;

    static {
        pro.classesInit0(2);
    }

    public native Object clone() throws CloneNotSupportedException;

    public native String getAccountName();

    public native String getAccountType();

    public native long getId();

    public native String getM3uEpgUrl();

    public native String getPassword();

    public native String getUrl();

    public native JSONObject getUserInfo();

    public native String getUsername();

    public native String getm3uUrl();

    public native void setAccountName(String str);

    public native void setAccountType(String str);

    public native void setId(long j);

    public native void setM3uEpgUrl(String str);

    public native void setPassword(String str);

    public native void setUrl(String str);

    public native void setUserInfo(JSONObject jSONObject);

    public native void setUsername(String str);

    public native void setm3uUrl(String str);
}
