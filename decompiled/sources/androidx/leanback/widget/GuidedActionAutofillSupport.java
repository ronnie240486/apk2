package androidx.leanback.widget;

import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public interface GuidedActionAutofillSupport {

    /* JADX INFO: loaded from: classes2.dex */
    public interface OnAutofillListener {
        void onAutofill(View view);
    }

    void setOnAutofillListener(OnAutofillListener onAutofillListener);
}
