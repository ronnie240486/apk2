package com.google.android.play.core.missingsplits;

import android.app.Application;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class MissingSplitsDetectingApplication extends Application {
    private boolean onCreateCalled = false;

    static {
        pro.classes2Init0(0);
    }

    @Override // android.app.Application
    public final native void onCreate();

    @Deprecated
    public native void onCreateCustom();
}
