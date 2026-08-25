package com.boxbrapks.task;

import android.content.Context;
import android.os.AsyncTask;
import com.boxbrapks.model.LoginTaskResponse;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;
import p030.p031.p032.p033.p034.p035.C2859;

/* JADX INFO: loaded from: classes2.dex */
public class BxCheckUserTask extends AsyncTask<Void, Void, String> {
    private static final char[] hexArray;
    private Context context;
    private String password;
    private LoginTaskResponse responseHandler;
    private String url;
    private String username;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public native String doInBackground(Void... voidArr);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public native void onPostExecute(String str);

    public BxCheckUserTask(Context context, LoginTaskResponse responseHandler, String username, String password, String url) {
        this.responseHandler = responseHandler;
        this.username = username;
        this.password = password;
        this.context = context;
        this.url = url;
    }

    static {
        pro.classes2Init0(2);
        hexArray = C2859.m2370("ScKit-705e569699924ee396934af855d63f4898eb4657d49055602952b6e51b1157d1", "ScKit-13bd00a15a594f80").toCharArray();
    }
}
