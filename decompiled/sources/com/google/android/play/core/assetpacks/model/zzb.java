package com.google.android.play.core.assetpacks.model;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes.dex */
public final class zzb {
    public static String zza(String str, String str2) {
        return Insets$$ExternalSyntheticOutline0.m24m(new StringBuilder(str.length() + 1 + String.valueOf(str2).length()), str, ":", str2);
    }

    public static String zzb(String str, String str2, String str3) {
        int length = String.valueOf(str2).length();
        StringBuilder sb = new StringBuilder(str.length() + 2 + length + String.valueOf(str3).length());
        Insets$$ExternalSyntheticOutline0.m37m(sb, str, ":", str2, ":");
        sb.append(str3);
        return sb.toString();
    }
}
