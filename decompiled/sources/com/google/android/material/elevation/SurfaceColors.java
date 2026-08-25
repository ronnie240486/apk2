package com.google.android.material.elevation;

import android.content.Context;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import com.google.android.material.C1207R;
import com.google.android.material.color.MaterialColors;

/* JADX INFO: loaded from: classes2.dex */
public enum SurfaceColors {
    SURFACE_0(C1207R.dimen.m3_sys_elevation_level0),
    SURFACE_1(C1207R.dimen.m3_sys_elevation_level1),
    SURFACE_2(C1207R.dimen.m3_sys_elevation_level2),
    SURFACE_3(C1207R.dimen.m3_sys_elevation_level3),
    SURFACE_4(C1207R.dimen.m3_sys_elevation_level4),
    SURFACE_5(C1207R.dimen.m3_sys_elevation_level5);

    private final int elevationResId;

    SurfaceColors(int i) {
        this.elevationResId = i;
    }

    @ColorInt
    public static int getColorForElevation(@NonNull Context context, @Dimension float f) {
        return new ElevationOverlayProvider(context).compositeOverlay(MaterialColors.getColor(context, C1207R.attr.colorSurface, 0), f);
    }

    @ColorInt
    public int getColor(@NonNull Context context) {
        return getColorForElevation(context, context.getResources().getDimension(this.elevationResId));
    }
}
