package com.google.android.exoplayer2;

import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda2 implements ListenerSet.Event {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((Player.Listener) obj).onAudioAttributesChanged((AudioAttributes) this.f$0);
                break;
            case 1:
                ((Player.Listener) obj).onMediaMetadataChanged((MediaMetadata) this.f$0);
                break;
            case 2:
                ((Player.Listener) obj).onTrackSelectionParametersChanged((TrackSelectionParameters) this.f$0);
                break;
            case 3:
                ((ExoPlayerImpl.ComponentListener) this.f$0).lambda$onMetadata$4((Player.Listener) obj);
                break;
            case 4:
                ((Player.Listener) obj).onMetadata((Metadata) this.f$0);
                break;
            case 5:
                ((Player.Listener) obj).onCues((List<Cue>) this.f$0);
                break;
            case 6:
                ((Player.Listener) obj).onDeviceInfoChanged((DeviceInfo) this.f$0);
                break;
            case 7:
                ((Player.Listener) obj).onCues((CueGroup) this.f$0);
                break;
            default:
                ((Player.Listener) obj).onVideoSizeChanged((VideoSize) this.f$0);
                break;
        }
    }
}
