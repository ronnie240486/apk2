package com.getkeepsafe.relinker;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class MissingLibraryException extends RuntimeException {
    /* JADX WARN: Illegal instructions before constructor call */
    public MissingLibraryException(String str, String[] strArr, String[] strArr2) {
        StringBuilder sbM29m = Insets$$ExternalSyntheticOutline0.m29m("Could not find '", str, "'. Looked for: ");
        sbM29m.append(Arrays.toString(strArr));
        sbM29m.append(", but only found: ");
        super(Insets$$ExternalSyntheticOutline0.m23m(sbM29m, Arrays.toString(strArr2), "."));
    }
}
