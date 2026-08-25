package p030.p031.p032.p033.p034.p035;

import java.nio.charset.Charset;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;

/* JADX INFO: renamed from: ﹶˆᴵﹶᵎᴵ.ᐧʻˑʻـٴ.ˏʼᵔـʽᴵ.ﹳﹶʼʽﹳ.ﹶˑﹶˋיˋ.ٴʽˏˈʾˉ.ᵎٴᴵˆᴵ, reason: contains not printable characters */
/* JADX INFO: loaded from: classes3.dex */
public class C2859 {
    private static final String AESTYPE = "AES";
    private static final Charset defaultCharset;

    public static native String bytesToHex(byte[] bArr);

    private static native byte[] decode(byte[] bArr, String str);

    private static native byte[] encode(byte[] bArr, String str);

    public static native String encodePass(String str);

    public static native String encodeStr(String str, String str2);

    public static native String encodeToMD5(String str);

    public static native String encodeToMD516(String str);

    public static native byte[] hexToByteArray(String str);

    /* JADX INFO: renamed from: ʿﹶـٴᵢʿᐧʼˉᵢʾˉˎˆˎיﹳˏʿﹶ, reason: contains not printable characters */
    public static native String m2370(String str, String str2);

    static {
        pro.classes3Init0(2);
        defaultCharset = Charset.forName("UTF-8");
    }
}
