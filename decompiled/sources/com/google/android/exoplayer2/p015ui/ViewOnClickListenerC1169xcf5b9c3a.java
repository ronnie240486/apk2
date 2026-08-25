package com.google.android.exoplayer2.p015ui;

import android.view.View;

/* JADX INFO: renamed from: com.google.android.exoplayer2.ui.StyledPlayerControlView$SettingViewHolder$$ExternalSyntheticLambda0 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ViewOnClickListenerC1169xcf5b9c3a implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ViewOnClickListenerC1169xcf5b9c3a(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((StyledPlayerControlView.SettingViewHolder) this.f$0).lambda$new$0(view);
                break;
            case 1:
                ((StyledPlayerControlView.AudioTrackSelectionAdapter) this.f$0).lambda$onBindViewHolderAtZeroPosition$0(view);
                break;
            default:
                ((StyledPlayerControlView.TextTrackSelectionAdapter) this.f$0).lambda$onBindViewHolderAtZeroPosition$0(view);
                break;
        }
    }
}
