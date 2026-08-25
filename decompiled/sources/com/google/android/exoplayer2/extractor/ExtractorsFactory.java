package com.google.android.exoplayer2.extractor;

import android.net.Uri;
import com.xplay.ibotv.apps.BaseActivity$$ExternalSyntheticLambda0;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface ExtractorsFactory {
    public static final ExtractorsFactory EMPTY = BaseActivity$$ExternalSyntheticLambda0.INSTANCE$28;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.extractor.ExtractorsFactory$-CC, reason: invalid class name */
    /* JADX INFO: loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        static {
            ExtractorsFactory extractorsFactory = ExtractorsFactory.EMPTY;
        }

        public static /* synthetic */ Extractor[] lambda$static$0() {
            return new Extractor[0];
        }
    }

    Extractor[] createExtractors();

    Extractor[] createExtractors(Uri uri, Map<String, List<String>> map);
}
