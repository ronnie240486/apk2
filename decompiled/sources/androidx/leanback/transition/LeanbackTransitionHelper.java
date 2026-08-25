package androidx.leanback.transition;

import android.content.Context;
import androidx.annotation.RestrictTo;
import androidx.leanback.C0398R;

/* JADX INFO: loaded from: classes2.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class LeanbackTransitionHelper {
    private LeanbackTransitionHelper() {
    }

    public static Object loadTitleInTransition(Context context) {
        return TransitionHelper.loadTransition(context, C0398R.transition.lb_title_in);
    }

    public static Object loadTitleOutTransition(Context context) {
        return TransitionHelper.loadTransition(context, C0398R.transition.lb_title_out);
    }
}
