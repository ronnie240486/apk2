package com.xplay.ibotv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.leanback.widget.VerticalGridView;

/* JADX INFO: loaded from: classes.dex */
public class LiveVerticalGridView extends VerticalGridView {

    /* JADX INFO: renamed from: K */
    private int f334K;

    /* JADX INFO: renamed from: L */
    private boolean f335L;

    public LiveVerticalGridView(Context context) {
        super(context);
        this.f334K = 0;
        this.f335L = true;
    }

    @Override // androidx.leanback.widget.BaseGridView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int selectedPosition = getSelectedPosition();
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 19) {
                if (keyCode == 20 && selectedPosition != -1 && getAdapter() != null && selectedPosition == getAdapter().getItemCount() - 1 && this.f335L) {
                    setSelectedPosition(0);
                    return true;
                }
            } else if (selectedPosition != -1 && getAdapter() != null) {
                int itemCount = getAdapter().getItemCount();
                if (selectedPosition != 0 || !this.f335L) {
                    return false;
                }
                setSelectedPosition(itemCount - 1);
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public int getPage() {
        return this.f334K;
    }

    public void setLoop(boolean z) {
        this.f335L = z;
    }

    public void setPage(int i) {
        this.f334K = i;
    }

    public LiveVerticalGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f334K = 0;
        this.f335L = true;
    }

    public LiveVerticalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f334K = 0;
        this.f335L = true;
    }
}
