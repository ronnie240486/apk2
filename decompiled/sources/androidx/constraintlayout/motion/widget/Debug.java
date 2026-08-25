package androidx.constraintlayout.motion.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.evgenii.jsevaluator.BuildConfig;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.CharBuffer;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"LogConditional"})
public class Debug {
    public static void dumpLayoutParams(ViewGroup layout, String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(") ");
        sbM26m.append(str);
        sbM26m.append("  ");
        String string = sbM26m.toString();
        int childCount = layout.getChildCount();
        System.out.println(str + " children " + childCount);
        for (int i = 0; i < childCount; i++) {
            View childAt = layout.getChildAt(i);
            PrintStream printStream = System.out;
            StringBuilder sbM28m = Insets$$ExternalSyntheticOutline0.m28m(string, "     ");
            sbM28m.append(getName(childAt));
            printStream.println(sbM28m.toString());
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            for (Field field : layoutParams.getClass().getFields()) {
                try {
                    Object obj = field.get(layoutParams);
                    if (field.getName().contains("To") && !obj.toString().equals("-1")) {
                        System.out.println(string + "       " + field.getName() + " " + obj);
                    }
                } catch (IllegalAccessException unused) {
                }
            }
        }
    }

    public static void dumpPoc(Object obj) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(")");
        String string = sbM26m.toString();
        Class<?> cls = obj.getClass();
        PrintStream printStream = System.out;
        StringBuilder sbM28m = Insets$$ExternalSyntheticOutline0.m28m(string, "------------- ");
        sbM28m.append(cls.getName());
        sbM28m.append(" --------------------");
        printStream.println(sbM28m.toString());
        for (Field field : cls.getFields()) {
            try {
                Object obj2 = field.get(obj);
                if (field.getName().startsWith("layout_constraint") && ((!(obj2 instanceof Integer) || !obj2.toString().equals("-1")) && ((!(obj2 instanceof Integer) || !obj2.toString().equals("0")) && ((!(obj2 instanceof Float) || !obj2.toString().equals(BuildConfig.VERSION_NAME)) && (!(obj2 instanceof Float) || !obj2.toString().equals("0.5")))))) {
                    System.out.println(string + "    " + field.getName() + " " + obj2);
                }
            } catch (IllegalAccessException unused) {
            }
        }
        PrintStream printStream2 = System.out;
        StringBuilder sbM28m2 = Insets$$ExternalSyntheticOutline0.m28m(string, "------------- ");
        sbM28m2.append(cls.getSimpleName());
        sbM28m2.append(" --------------------");
        printStream2.println(sbM28m2.toString());
    }

    public static String getActionType(MotionEvent event) {
        int action = event.getAction();
        for (Field field : MotionEvent.class.getFields()) {
            try {
                if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(Integer.TYPE) && field.getInt(null) == action) {
                    return field.getName();
                }
            } catch (IllegalAccessException unused) {
            }
        }
        return "---";
    }

    public static String getCallFrom(int n) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[n + 2];
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(")");
        return sbM26m.toString();
    }

    public static String getLoc() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(") ");
        sbM26m.append(stackTraceElement.getMethodName());
        sbM26m.append("()");
        return sbM26m.toString();
    }

    public static String getLocation() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(")");
        return sbM26m.toString();
    }

    public static String getLocation2() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(")");
        return sbM26m.toString();
    }

    public static String getName(View view) {
        try {
            return view.getContext().getResources().getResourceEntryName(view.getId());
        } catch (Exception unused) {
            return "UNKNOWN";
        }
    }

    public static String getState(MotionLayout layout, int stateId) {
        return getState(layout, stateId, -1);
    }

    public static void logStack(String tag, String msg, int n) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int iMin = Math.min(n, stackTrace.length - 1);
        String strM19m = " ";
        for (int i = 1; i <= iMin; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
            sbM26m.append(stackTrace[i].getFileName());
            sbM26m.append(":");
            sbM26m.append(stackTrace[i].getLineNumber());
            sbM26m.append(") ");
            sbM26m.append(stackTrace[i].getMethodName());
            String string = sbM26m.toString();
            strM19m = Insets$$ExternalSyntheticOutline0.m19m(strM19m, " ");
            Log.v(tag, msg + strM19m + string + strM19m);
        }
    }

    public static void printStack(String msg, int n) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int iMin = Math.min(n, stackTrace.length - 1);
        String strM19m = " ";
        for (int i = 1; i <= iMin; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
            sbM26m.append(stackTrace[i].getFileName());
            sbM26m.append(":");
            sbM26m.append(stackTrace[i].getLineNumber());
            sbM26m.append(") ");
            String string = sbM26m.toString();
            strM19m = Insets$$ExternalSyntheticOutline0.m19m(strM19m, " ");
            System.out.println(msg + strM19m + string + strM19m);
        }
    }

    public static String getState(MotionLayout layout, int stateId, int len) {
        int length;
        if (stateId == -1) {
            return "UNDEFINED";
        }
        String resourceEntryName = layout.getContext().getResources().getResourceEntryName(stateId);
        if (len == -1) {
            return resourceEntryName;
        }
        if (resourceEntryName.length() > len) {
            resourceEntryName = resourceEntryName.replaceAll("([^_])[aeiou]+", "$1");
        }
        if (resourceEntryName.length() <= len || (length = resourceEntryName.replaceAll("[^_]", "").length()) <= 0) {
            return resourceEntryName;
        }
        return resourceEntryName.replaceAll(CharBuffer.allocate((resourceEntryName.length() - len) / length).toString().replace((char) 0, '.') + "_", "_");
    }

    public static String getName(Context context, int id) {
        if (id == -1) {
            return "UNKNOWN";
        }
        try {
            return context.getResources().getResourceEntryName(id);
        } catch (Exception unused) {
            return Insets$$ExternalSyntheticOutline0.m15m("?", id);
        }
    }

    public static String getName(Context context, int[] id) {
        String resourceEntryName;
        try {
            String str = id.length + "[";
            int i = 0;
            while (i < id.length) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(i == 0 ? "" : " ");
                String string = sb.toString();
                try {
                    resourceEntryName = context.getResources().getResourceEntryName(id[i]);
                } catch (Resources.NotFoundException unused) {
                    resourceEntryName = "? " + id[i] + " ";
                }
                str = string + resourceEntryName;
                i++;
            }
            return str + "]";
        } catch (Exception e) {
            Log.v("DEBUG", e.toString());
            return "UNKNOWN";
        }
    }

    public static void dumpLayoutParams(ViewGroup.LayoutParams param, String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(".(");
        sbM26m.append(stackTraceElement.getFileName());
        sbM26m.append(":");
        sbM26m.append(stackTraceElement.getLineNumber());
        sbM26m.append(") ");
        sbM26m.append(str);
        sbM26m.append("  ");
        String string = sbM26m.toString();
        PrintStream printStream = System.out;
        StringBuilder sbM29m = Insets$$ExternalSyntheticOutline0.m29m(" >>>>>>>>>>>>>>>>>>. dump ", string, "  ");
        sbM29m.append(param.getClass().getName());
        printStream.println(sbM29m.toString());
        for (Field field : param.getClass().getFields()) {
            try {
                Object obj = field.get(param);
                String name = field.getName();
                if (name.contains("To") && !obj.toString().equals("-1")) {
                    System.out.println(string + "       " + name + " " + obj);
                }
            } catch (IllegalAccessException unused) {
            }
        }
        System.out.println(" <<<<<<<<<<<<<<<<< dump " + string);
    }
}
