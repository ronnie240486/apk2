package androidx.core.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.util.Log;
import io.realm.Realm;
import java.util.HashMap;
import kotlin.jvm.internal.InlineMarker;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Insets$$ExternalSyntheticOutline0 implements TrackSelector.InvalidationListener {
    public static final /* synthetic */ Insets$$ExternalSyntheticOutline0 INSTANCE = new Insets$$ExternalSyntheticOutline0();

    /* JADX INFO: renamed from: m */
    public static float m9m(float f, float f2, float f3, float f4) {
        return ((f - f2) * f3) + f4;
    }

    /* JADX INFO: renamed from: m */
    public static int m10m(String str, int i, int i2) {
        return (str.hashCode() + i) * i2;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
    public void onTrackSelectionsInvalidated() {
        DownloadHelper.lambda$new$2();
    }

    /* JADX INFO: renamed from: m */
    public static View m11m(ViewGroup viewGroup, int i, ViewGroup viewGroup2, boolean z) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup2, z);
    }

    /* JADX INFO: renamed from: m */
    public static Object m12m(Realm realm, Class cls, String str, String str2) {
        return realm.where(cls).equalTo(str, str2).findFirst();
    }

    /* JADX INFO: renamed from: m */
    public static String m13m(int i, String str, int i2) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(i2);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m14m(RecyclerView recyclerView, StringBuilder sb) {
        sb.append(recyclerView.exceptionLabel());
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m15m(String str, int i) {
        return str + i;
    }

    /* JADX INFO: renamed from: m */
    public static String m16m(String str, int i, String str2) {
        return str + i + str2;
    }

    /* JADX INFO: renamed from: m */
    public static String m17m(String str, long j) {
        return str + j;
    }

    /* JADX INFO: renamed from: m */
    public static String m18m(String str, Fragment fragment, String str2) {
        return str + fragment + str2;
    }

    /* JADX INFO: renamed from: m */
    public static String m19m(String str, String str2) {
        return str + str2;
    }

    /* JADX INFO: renamed from: m */
    public static String m20m(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    /* JADX INFO: renamed from: m */
    public static String m21m(StringBuilder sb, int i, char c) {
        sb.append(i);
        sb.append(c);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m22m(StringBuilder sb, int i, String str) {
        sb.append(i);
        sb.append(str);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m23m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m24m(StringBuilder sb, String str, String str2, String str3) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m25m(StringBuilder sb, String str, String str2, String str3, String str4) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static StringBuilder m26m(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    /* JADX INFO: renamed from: m */
    public static StringBuilder m27m(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(i);
        sb.append(str2);
        return sb;
    }

    /* JADX INFO: renamed from: m */
    public static StringBuilder m28m(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    /* JADX INFO: renamed from: m */
    public static StringBuilder m29m(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb;
    }

    /* JADX INFO: renamed from: m */
    public static void m30m(int i, Canvas canvas, int i2, int i3) {
        InlineMarker.finallyStart(i);
        canvas.restoreToCount(i2);
        InlineMarker.finallyEnd(i3);
    }

    /* JADX INFO: renamed from: m */
    public static void m31m(int i, HashMap map, String str, int i2, String str2, int i3, String str3, int i4, String str4) {
        map.put(str, Integer.valueOf(i));
        map.put(str2, Integer.valueOf(i2));
        map.put(str3, Integer.valueOf(i3));
        map.put(str4, Integer.valueOf(i4));
    }

    /* JADX INFO: renamed from: m */
    public static void m32m(Context context, int i, TextView textView) {
        textView.setTextColor(context.getResources().getColor(i));
    }

    /* JADX INFO: renamed from: m */
    public static void m33m(FragmentTransaction fragmentTransaction, Fragment fragment, String str) {
        fragmentTransaction.remove(fragment);
        fragmentTransaction.addToBackStack(str);
        fragmentTransaction.commit();
    }

    /* JADX INFO: renamed from: m */
    public static void m34m(String str, int i, String str2) {
        Log.m72w(str2, str + i);
    }

    /* JADX INFO: renamed from: m */
    public static void m35m(String str, String str2, String str3) {
        Log.m72w(str3, str + str2);
    }

    /* JADX INFO: renamed from: m */
    public static void m36m(StringBuilder sb, String str, char c, String str2) {
        sb.append(str);
        sb.append(c);
        sb.append(str2);
    }

    /* JADX INFO: renamed from: m */
    public static void m37m(StringBuilder sb, String str, String str2, String str3, String str4) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
    }
}
