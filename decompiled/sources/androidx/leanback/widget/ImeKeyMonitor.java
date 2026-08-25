package androidx.leanback.widget;

import android.view.KeyEvent;
import android.widget.EditText;

/* JADX INFO: loaded from: classes2.dex */
public interface ImeKeyMonitor {

    /* JADX INFO: loaded from: classes.dex */
    public interface ImeKeyListener {
        boolean onKeyPreIme(EditText editText, int i, KeyEvent keyEvent);
    }

    void setImeKeyListener(ImeKeyListener imeKeyListener);
}
