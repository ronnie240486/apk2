package com.xplay.ibotv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.leanback.widget.HorizontalGridView;

/* JADX INFO: loaded from: classes.dex */
public class LiveHorizontalGridView extends HorizontalGridView {

    /* JADX INFO: renamed from: K */
    private int f332K;

    /* JADX INFO: renamed from: L */
    private boolean f333L;

    public LiveHorizontalGridView(Context context) {
        super(context);
        this.f332K = 0;
        this.f333L = true;
    }

    @Override // androidx.leanback.widget.BaseGridView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int selectedPosition = getSelectedPosition();
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 21) {
                if (keyCode == 22 && selectedPosition != -1 && getAdapter() != null && selectedPosition == getAdapter().getItemCount() - 1 && this.f333L) {
                    setSelectedPosition(0);
                    return true;
                }
            } else if (selectedPosition != -1 && getAdapter() != null) {
                int itemCount = getAdapter().getItemCount();
                if (selectedPosition != 0 || !this.f333L) {
                    return false;
                }
                setSelectedPosition(itemCount - 1);
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public int getPage() {
        return this.f332K;
    }

    public void setLoop(boolean z) {
        this.f333L = z;
    }

    public void setPage(int i) {
        this.f332K = i;
    }

    public LiveHorizontalGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f332K = 0;
        this.f333L = true;
    }

    public LiveHorizontalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f332K = 0;
        this.f333L = true;
    }
}
