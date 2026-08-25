package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class MovieInfoResponse implements Serializable {
    public MovieInfo info;

    public MovieInfo getInfo() {
        return this.info;
    }

    public void setInfo(MovieInfo movieInfo) {
        this.info = movieInfo;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("MovieInfoResponse{info=");
        sbM26m.append(this.info);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
