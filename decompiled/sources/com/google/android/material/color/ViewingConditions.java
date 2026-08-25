package com.google.android.material.color;

/* JADX INFO: loaded from: classes.dex */
final class ViewingConditions {
    public static final ViewingConditions DEFAULT;

    /* JADX INFO: renamed from: aw */
    private final float f203aw;

    /* JADX INFO: renamed from: c */
    private final float f204c;

    /* JADX INFO: renamed from: fl */
    private final float f205fl;
    private final float flRoot;

    /* JADX INFO: renamed from: n */
    private final float f206n;
    private final float nbb;

    /* JADX INFO: renamed from: nc */
    private final float f207nc;
    private final float ncb;
    private final float[] rgbD;

    /* JADX INFO: renamed from: z */
    private final float f208z;

    static {
        float[] fArrWhitePointD65 = ColorUtils.whitePointD65();
        float fYFromLstar = (float) ((((double) ColorUtils.yFromLstar(50.0f)) * 63.66197723675813d) / 100.0d);
        float[][] fArr = Cam16.XYZ_TO_CAM16RGB;
        float f = (fArrWhitePointD65[2] * fArr[0][2]) + (fArrWhitePointD65[1] * fArr[0][1]) + (fArrWhitePointD65[0] * fArr[0][0]);
        float f2 = (fArrWhitePointD65[2] * fArr[1][2]) + (fArrWhitePointD65[1] * fArr[1][1]) + (fArrWhitePointD65[0] * fArr[1][0]);
        float f3 = (fArrWhitePointD65[2] * fArr[2][2]) + (fArrWhitePointD65[1] * fArr[2][1]) + (fArrWhitePointD65[0] * fArr[2][0]);
        float fLerp = ((double) 1.0f) >= 0.9d ? MathUtils.lerp(0.59f, 0.69f, 1.0000002f) : MathUtils.lerp(0.525f, 0.59f, 1.9999999f);
        float fExp = (1.0f - (((float) Math.exp(((-fYFromLstar) - 42.0f) / 92.0f)) * 0.2777778f)) * 1.0f;
        double d = fExp;
        if (d > 1.0d) {
            fExp = 1.0f;
        } else if (d < 0.0d) {
            fExp = 0.0f;
        }
        float[] fArr2 = {(((100.0f / f) * fExp) + 1.0f) - fExp, (((100.0f / f2) * fExp) + 1.0f) - fExp, (((100.0f / f3) * fExp) + 1.0f) - fExp};
        float f4 = 1.0f / ((5.0f * fYFromLstar) + 1.0f);
        float f5 = f4 * f4 * f4 * f4;
        float f6 = 1.0f - f5;
        float fCbrt = (0.1f * f6 * f6 * ((float) Math.cbrt(((double) fYFromLstar) * 5.0d))) + (f5 * fYFromLstar);
        float fYFromLstar2 = ColorUtils.yFromLstar(50.0f) / fArrWhitePointD65[1];
        double d2 = fYFromLstar2;
        float fSqrt = ((float) Math.sqrt(d2)) + 1.48f;
        float fPow = 0.725f / ((float) Math.pow(d2, 0.2d));
        float[] fArr3 = {(float) Math.pow(((double) ((fArr2[0] * fCbrt) * f)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr2[1] * fCbrt) * f2)) / 100.0d, 0.42d), (float) Math.pow(((double) ((fArr2[2] * fCbrt) * f3)) / 100.0d, 0.42d)};
        float[] fArr4 = {(fArr3[0] * 400.0f) / (fArr3[0] + 27.13f), (fArr3[1] * 400.0f) / (fArr3[1] + 27.13f), (fArr3[2] * 400.0f) / (fArr3[2] + 27.13f)};
        DEFAULT = new ViewingConditions(fYFromLstar2, ((fArr4[2] * 0.05f) + (fArr4[0] * 2.0f) + fArr4[1]) * fPow, fPow, fPow, fLerp, 1.0f, fArr2, fCbrt, (float) Math.pow(fCbrt, 0.25d), fSqrt);
    }

    private ViewingConditions(float f, float f2, float f3, float f4, float f5, float f6, float[] fArr, float f7, float f8, float f9) {
        this.f206n = f;
        this.f203aw = f2;
        this.nbb = f3;
        this.ncb = f4;
        this.f204c = f5;
        this.f207nc = f6;
        this.rgbD = fArr;
        this.f205fl = f7;
        this.flRoot = f8;
        this.f208z = f9;
    }

    public float getAw() {
        return this.f203aw;
    }

    public final float getC() {
        return this.f204c;
    }

    public final float getFl() {
        return this.f205fl;
    }

    public float getFlRoot() {
        return this.flRoot;
    }

    public float getN() {
        return this.f206n;
    }

    public float getNbb() {
        return this.nbb;
    }

    public final float getNc() {
        return this.f207nc;
    }

    public final float getNcb() {
        return this.ncb;
    }

    public float[] getRgbD() {
        return this.rgbD;
    }

    public final float getZ() {
        return this.f208z;
    }
}
