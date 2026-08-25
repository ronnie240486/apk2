package androidx.startup;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class StartupLogger {
    private static final String TAG = "StartupLogger";

    private StartupLogger() {
    }

    /* JADX INFO: renamed from: e */
    public static void m53e(@NonNull String str, @Nullable Throwable th) {
        Log.e(TAG, str, th);
    }

    /* JADX INFO: renamed from: i */
    public static void m54i(@NonNull String str) {
        Log.i(TAG, str);
    }

    /* JADX INFO: renamed from: w */
    public static void m55w(@NonNull String str) {
        Log.w(TAG, str);
    }
}
