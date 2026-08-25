package com.google.common.base;

import com.google.android.exoplayer2.C1022C;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
public final class Charsets {

    @GwtIncompatible
    public static final Charset US_ASCII = Charset.forName(C1022C.ASCII_NAME);
    public static final Charset ISO_8859_1 = Charset.forName(C1022C.ISO88591_NAME);
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    @GwtIncompatible
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");

    @GwtIncompatible
    public static final Charset UTF_16LE = Charset.forName(C1022C.UTF16LE_NAME);

    @GwtIncompatible
    public static final Charset UTF_16 = Charset.forName(C1022C.UTF16_NAME);

    private Charsets() {
    }
}
