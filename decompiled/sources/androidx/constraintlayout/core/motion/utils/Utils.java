package androidx.constraintlayout.core.motion.utils;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/* JADX INFO: loaded from: classes2.dex */
public class Utils {
    public static DebugHandle ourHandle;

    /* JADX INFO: loaded from: classes.dex */
    public interface DebugHandle {
        void message(String str);
    }

    private static int clamp(int i) {
        int i2 = (i & (~(i >> 31))) - 255;
        return (i2 & (i2 >> 31)) + 255;
    }

    public static void log(String str, String str2) {
        System.out.println(str + " : " + str2);
    }

    public static void logStack(String str, int i) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int iMin = Math.min(i, stackTrace.length - 1);
        String strM19m = " ";
        for (int i2 = 1; i2 <= iMin; i2++) {
            StackTraceElement stackTraceElement = stackTrace[i2];
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
            sbM26m.append(stackTrace[i2].getFileName());
            sbM26m.append(":");
            sbM26m.append(stackTrace[i2].getLineNumber());
            sbM26m.append(") ");
            sbM26m.append(stackTrace[i2].getMethodName());
            String string = sbM26m.toString();
            strM19m = Insets$$ExternalSyntheticOutline0.m19m(strM19m, " ");
            System.out.println(str + strM19m + string + strM19m);
        }
    }

    public static void loge(String str, String str2) {
        System.err.println(str + " : " + str2);
    }

    public static int rgbaTocColor(float f, float f2, float f3, float f4) {
        int iClamp = clamp((int) (f * 255.0f));
        int iClamp2 = clamp((int) (f2 * 255.0f));
        return (iClamp << 16) | (clamp((int) (f4 * 255.0f)) << 24) | (iClamp2 << 8) | clamp((int) (f3 * 255.0f));
    }

    public static void setDebugHandle(DebugHandle debugHandle) {
        ourHandle = debugHandle;
    }

    public static void socketSend(String str) {
        try {
            OutputStream outputStream = new Socket("127.0.0.1", 5327).getOutputStream();
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInterpolatedColor(float[] fArr) {
        return (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f));
    }

    public static void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String strSubstring = (stackTraceElement.getMethodName() + "                  ").substring(0, 17);
        String strSubstring2 = "    ".substring(Integer.toString(stackTraceElement.getLineNumber()).length());
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(")");
        sbM26m.append(strSubstring2);
        sbM26m.append(strSubstring);
        String string = sbM26m.toString();
        System.out.println(string + " " + str);
        DebugHandle debugHandle = ourHandle;
        if (debugHandle != null) {
            debugHandle.message(string + " " + str);
        }
    }
}
