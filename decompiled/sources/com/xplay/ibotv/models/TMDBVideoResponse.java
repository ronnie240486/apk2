package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class TMDBVideoResponse {

    /* JADX INFO: renamed from: id */
    public int f329id;
    public List<TMDBVideoModel> results;

    public int getId() {
        return this.f329id;
    }

    public List<TMDBVideoModel> getResults() {
        return this.results;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("TMDBVideoResponse{id=");
        sbM26m.append(this.f329id);
        sbM26m.append(", results=");
        sbM26m.append(this.results);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
