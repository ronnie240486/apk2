package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.UnknownHostException;
import org.checkerframework.dataflow.qual.Pure;

/* JADX INFO: loaded from: classes.dex */
public final class Log {
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_LEVEL_INFO = 1;
    public static final int LOG_LEVEL_OFF = Integer.MAX_VALUE;
    public static final int LOG_LEVEL_WARNING = 2;

    @GuardedBy("lock")
    private static int logLevel = 0;

    @GuardedBy("lock")
    private static boolean logStackTraces = true;
    private static final Object lock = new Object();

    @GuardedBy("lock")
    private static Logger logger = Logger.DEFAULT;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel {
    }

    /* JADX INFO: loaded from: classes2.dex */
    public interface Logger {
        public static final Logger DEFAULT = new Logger() { // from class: com.google.android.exoplayer2.util.Log.Logger.1
            @Override // com.google.android.exoplayer2.util.Log.Logger
            /* JADX INFO: renamed from: d */
            public void mo74d(String str, String str2) {
                android.util.Log.d(str, str2);
            }

            @Override // com.google.android.exoplayer2.util.Log.Logger
            /* JADX INFO: renamed from: e */
            public void mo75e(String str, String str2) {
                android.util.Log.e(str, str2);
            }

            @Override // com.google.android.exoplayer2.util.Log.Logger
            /* JADX INFO: renamed from: i */
            public void mo76i(String str, String str2) {
                android.util.Log.i(str, str2);
            }

            @Override // com.google.android.exoplayer2.util.Log.Logger
            /* JADX INFO: renamed from: w */
            public void mo77w(String str, String str2) {
                android.util.Log.w(str, str2);
            }
        };

        /* JADX INFO: renamed from: d */
        void mo74d(String str, String str2);

        /* JADX INFO: renamed from: e */
        void mo75e(String str, String str2);

        /* JADX INFO: renamed from: i */
        void mo76i(String str, String str2);

        /* JADX INFO: renamed from: w */
        void mo77w(String str, String str2);
    }

    private Log() {
    }

    @Pure
    private static String appendThrowableString(String str, @Nullable Throwable th) {
        String throwableString = getThrowableString(th);
        if (TextUtils.isEmpty(throwableString)) {
            return str;
        }
        StringBuilder sbM28m = Insets$$ExternalSyntheticOutline0.m28m(str, "\n  ");
        sbM28m.append(throwableString.replace("\n", "\n  "));
        sbM28m.append('\n');
        return sbM28m.toString();
    }

    @Pure
    /* JADX INFO: renamed from: d */
    public static void m66d(@Size(max = 23) String str, String str2) {
        synchronized (lock) {
            if (logLevel == 0) {
                logger.mo74d(str, str2);
            }
        }
    }

    @Pure
    /* JADX INFO: renamed from: e */
    public static void m68e(@Size(max = 23) String str, String str2) {
        synchronized (lock) {
            if (logLevel <= 3) {
                logger.mo75e(str, str2);
            }
        }
    }

    @Pure
    public static int getLogLevel() {
        int i;
        synchronized (lock) {
            i = logLevel;
        }
        return i;
    }

    @Nullable
    @Pure
    public static String getThrowableString(@Nullable Throwable th) {
        synchronized (lock) {
            try {
                if (th == null) {
                    return null;
                }
                if (isCausedByUnknownHostException(th)) {
                    return "UnknownHostException (no network)";
                }
                if (logStackTraces) {
                    return android.util.Log.getStackTraceString(th).trim().replace("\t", "    ");
                }
                return th.getMessage();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Pure
    /* JADX INFO: renamed from: i */
    public static void m70i(@Size(max = 23) String str, String str2) {
        synchronized (lock) {
            if (logLevel <= 1) {
                logger.mo76i(str, str2);
            }
        }
    }

    @Pure
    private static boolean isCausedByUnknownHostException(@Nullable Throwable th) {
        while (th != null) {
            if (th instanceof UnknownHostException) {
                return true;
            }
            th = th.getCause();
        }
        return false;
    }

    public static void setLogLevel(int i) {
        synchronized (lock) {
            logLevel = i;
        }
    }

    public static void setLogStackTraces(boolean z) {
        synchronized (lock) {
            logStackTraces = z;
        }
    }

    public static void setLogger(Logger logger2) {
        synchronized (lock) {
            logger = logger2;
        }
    }

    @Pure
    /* JADX INFO: renamed from: w */
    public static void m72w(@Size(max = 23) String str, String str2) {
        synchronized (lock) {
            if (logLevel <= 2) {
                logger.mo77w(str, str2);
            }
        }
    }

    @Pure
    /* JADX INFO: renamed from: d */
    public static void m67d(@Size(max = 23) String str, String str2, @Nullable Throwable th) {
        m66d(str, appendThrowableString(str2, th));
    }

    @Pure
    /* JADX INFO: renamed from: e */
    public static void m69e(@Size(max = 23) String str, String str2, @Nullable Throwable th) {
        m68e(str, appendThrowableString(str2, th));
    }

    @Pure
    /* JADX INFO: renamed from: i */
    public static void m71i(@Size(max = 23) String str, String str2, @Nullable Throwable th) {
        m70i(str, appendThrowableString(str2, th));
    }

    @Pure
    /* JADX INFO: renamed from: w */
    public static void m73w(@Size(max = 23) String str, String str2, @Nullable Throwable th) {
        m72w(str, appendThrowableString(str2, th));
    }
}
