package androidx.recyclerview.widget;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
class LayoutState {
    public int mAvailable;
    public int mCurrentPosition;
    public boolean mInfinite;
    public int mItemDirection;
    public int mLayoutDirection;
    public boolean mStopInFocusable;
    public boolean mRecycle = true;
    public int mStartLine = 0;
    public int mEndLine = 0;

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("LayoutState{mAvailable=");
        sbM26m.append(this.mAvailable);
        sbM26m.append(", mCurrentPosition=");
        sbM26m.append(this.mCurrentPosition);
        sbM26m.append(", mItemDirection=");
        sbM26m.append(this.mItemDirection);
        sbM26m.append(", mLayoutDirection=");
        sbM26m.append(this.mLayoutDirection);
        sbM26m.append(", mStartLine=");
        sbM26m.append(this.mStartLine);
        sbM26m.append(", mEndLine=");
        return Insets$$ExternalSyntheticOutline0.m21m(sbM26m, this.mEndLine, '}');
    }
}
