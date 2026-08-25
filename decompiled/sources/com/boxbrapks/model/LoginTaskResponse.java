package com.boxbrapks.model;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface LoginTaskResponse {
    void onLoginFailed(String str);

    void onLoginSuccess(String str, String str2, String str3);

    void onLoginSuccess(ArrayList<UserAccountModel> arrayList);
}
