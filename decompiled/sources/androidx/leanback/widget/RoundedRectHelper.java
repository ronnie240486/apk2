package androidx.leanback.widget;

import android.view.View;
import androidx.leanback.C0398R;

/* JADX INFO: loaded from: classes2.dex */
final class RoundedRectHelper {
    private RoundedRectHelper() {
    }

    public static void setClipToRoundedOutline(View view, boolean z) {
        RoundedRectHelperApi21.setClipToRoundedOutline(view, z, view.getResources().getDimensionPixelSize(C0398R.dimen.lb_rounded_rect_corner_radius));
    }
}
