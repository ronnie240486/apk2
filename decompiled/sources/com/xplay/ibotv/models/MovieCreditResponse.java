package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MovieCreditResponse {
    public List<MovieCreditModel> cast;

    public List<MovieCreditModel> getCast() {
        return this.cast;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("MovieCreditResponse{cast=");
        sbM26m.append(this.cast);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
