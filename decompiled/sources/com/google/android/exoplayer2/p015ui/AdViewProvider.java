package com.google.android.exoplayer2.p015ui;

import android.view.ViewGroup;
import androidx.annotation.Nullable;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface AdViewProvider {

    /* JADX INFO: renamed from: com.google.android.exoplayer2.ui.AdViewProvider$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
    }

    List<AdOverlayInfo> getAdOverlayInfos();

    @Nullable
    ViewGroup getAdViewGroup();
}
