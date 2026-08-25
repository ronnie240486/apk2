package com.google.android.material.color;

/* JADX INFO: loaded from: classes3.dex */
final class MathUtils {
    private MathUtils() {
    }

    public static float differenceDegrees(float f, float f2) {
        return 180.0f - Math.abs(Math.abs(f - f2) - 180.0f);
    }

    public static float lerp(float f, float f2, float f3) {
        return (f3 * f2) + ((1.0f - f3) * f);
    }

    public static float sanitizeDegrees(float f) {
        if (f < 0.0f) {
            return (f % 360.0f) + 360.0f;
        }
        return f >= 360.0f ? f % 360.0f : f;
    }

    public static int sanitizeDegrees(int i) {
        if (i < 0) {
            return (i % 360) + 360;
        }
        return i >= 360 ? i % 360 : i;
    }
}
