package com.boxbrapks;

import java.io.IOException;
import java.security.PublicKey;
import org.json.JSONObject;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;
import p030.p031.p032.p033.p034.p035.C2859;

/* JADX INFO: loaded from: classes.dex */
public class BxAppSecurity {
    private static String ALLOWED_CHARACTERS;
    private static String KEY_FACTORY_ALGORITHM;
    private static String SIGNATURE_ALGORITHM;
    private static String TAG;

    static {
        pro.classesInit0(0);
        TAG = C2859.m2370("ScKit-4686d4320d4908aad635b32bc2bfc76b229d721546b65faaeca76a1dc6b4e437", "ScKit-2f2f51a0069e5526");
        SIGNATURE_ALGORITHM = C2859.m2370("ScKit-aee197150837a0a7f2304d4ea3b7b971", "ScKit-2f2f51a0069e5526");
        KEY_FACTORY_ALGORITHM = C2859.m2370("ScKit-f534d8a9feef94d536faa0021f08bdc3", "ScKit-2f2f51a0069e5526");
        ALLOWED_CHARACTERS = C2859.m2370("ScKit-cfe07c3508acc902f4e4f601d45cf9f252c4b45853c8d0cebcc4f470e719cefdd8d5f7cae33142f403edd34d76ce86cd4f4f1306b39f208dc2504b379f6f6d60", "ScKit-2f2f51a0069e5526");
    }

    public static native byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception;

    public static native PublicKey generatePublicKey(String str) throws IOException;

    public static native String getActivateData(String str, String str2);

    public static native String getAddData(String str, String str2, String str3, String str4, String str5);

    public static native String getDecodedString(String str);

    public static native String getDeleteData(String str, String str2);

    private static native String getEncryptKey(int i);

    private static native int getEncryptKeyPosition(String str);

    private static native String getEncryptPositionString(int i);

    public static native JSONObject getFileData(int i);

    public static native JSONObject getJsonData(String str);

    public static native String getStringData(String str, String str2, boolean z, String str3);

    public static native JSONObject getUserObject(String str, String str2);

    public static native boolean verify(PublicKey publicKey, String str, String str2);

    public static native boolean verifyPurchase(String str, String str2, String str3) throws IOException;
}
