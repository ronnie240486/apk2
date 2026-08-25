package com.google.android.exoplayer2.text;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface TextOutput {

    /* JADX INFO: renamed from: com.google.android.exoplayer2.text.TextOutput$-CC, reason: invalid class name */
    /* JADX INFO: loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        @Deprecated
        public static void $default$onCues(TextOutput textOutput, List list) {
        }
    }

    void onCues(CueGroup cueGroup);

    @Deprecated
    void onCues(List<Cue> list);
}
