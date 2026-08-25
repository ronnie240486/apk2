package com.google.android.exoplayer2.audio;

import android.os.Handler;
import java.util.concurrent.Executor;

/* JADX INFO: renamed from: com.google.android.exoplayer2.audio.DefaultAudioSink$StreamEventCallbackV29$$ExternalSyntheticLambda0 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ExecutorC1047x59586989 implements Executor {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Handler f$0;

    public /* synthetic */ ExecutorC1047x59586989(Handler handler, int i) {
        this.$r8$classId = i;
        this.f$0 = handler;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0002. Please report as an issue. */
    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.$r8$classId) {
        }
        this.f$0.post(runnable);
    }
}
