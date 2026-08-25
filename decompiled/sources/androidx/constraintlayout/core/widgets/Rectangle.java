package androidx.constraintlayout.core.widgets;

/* JADX INFO: loaded from: classes2.dex */
public class Rectangle {
    public int height;
    public int width;

    /* JADX INFO: renamed from: x */
    public int f42x;

    /* JADX INFO: renamed from: y */
    public int f43y;

    public boolean contains(int i, int i2) {
        int i3;
        int i4 = this.f42x;
        return i >= i4 && i < i4 + this.width && i2 >= (i3 = this.f43y) && i2 < i3 + this.height;
    }

    public int getCenterX() {
        return (this.f42x + this.width) / 2;
    }

    public int getCenterY() {
        return (this.f43y + this.height) / 2;
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        this.f42x = i;
        this.f43y = i2;
        this.width = i3;
        this.height = i4;
    }
}
