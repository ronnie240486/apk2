package com.boxbrapks.task;

import android.content.Context;
import android.os.AsyncTask;
import com.boxbrapks.model.LoginTaskResponse;
import com.boxbrapks.model.UserAccountModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;

/* JADX INFO: loaded from: classes2.dex */
public class BxValidateLoginTask extends AsyncTask<Void, Void, ArrayList<PortalDataObj>> {
    private Context context;
    private String password;
    private LoginTaskResponse responseHandler;
    private String shift;
    UserAccountModel userModel;
    private String username;

    static {
        pro.classes2Init0(5);
    }

    private native ArrayList<PortalDataObj> getAllPortals(URL url) throws Exception;

    private native URL getBaseUrl() throws Exception;

    private native boolean validateM3uPortal(PortalDataObj portalDataObj) throws IOException;

    private native boolean validateXcUser(PortalDataObj portalDataObj);

    public native boolean checkStreamAccessiable(PortalDataObj portalDataObj, String str, String str2);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public native ArrayList<PortalDataObj> doInBackground(Void... voidArr);

    public native void init(Context context, LoginTaskResponse loginTaskResponse, UserAccountModel userAccountModel);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public native void onPostExecute(ArrayList<PortalDataObj> arrayList);

    /* JADX INFO: loaded from: classes3.dex */
    public class PortalDataObj {
        public boolean validContentLenghtCheck;
        public String accountType = null;
        public String validContentType = null;
        public URL xcPortalUrl = null;
        public URL m3uUrl = null;
        public URL m3uEpgUrl = null;
        public String requestMethod = null;
        public int accessStreamId = -1;

        public PortalDataObj() {
        }
    }
}
