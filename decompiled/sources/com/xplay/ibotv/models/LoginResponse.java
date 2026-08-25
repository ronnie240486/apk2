package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class LoginResponse {
    private ServerModel server_info;
    private LoginModel user_info;

    public ServerModel getServerModel() {
        return this.server_info;
    }

    public LoginModel getUser_info() {
        return this.user_info;
    }

    public void setServerModel(ServerModel serverModel) {
        this.server_info = serverModel;
    }

    public void setUser_info(LoginModel loginModel) {
        this.user_info = loginModel;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("LoginResponse{user_info=");
        sbM26m.append(this.user_info);
        sbM26m.append('\'');
        sbM26m.append("server_info=");
        sbM26m.append(this.server_info);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
