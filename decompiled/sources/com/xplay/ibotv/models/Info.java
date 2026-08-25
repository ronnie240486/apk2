package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class Info implements Serializable {
    private String movie_image;
    private String plot;
    private String rating;
    private String releasedate;
    private String tmdb_id;

    public String getMovie_image() {
        return this.movie_image;
    }

    public String getPlot() {
        String str = this.plot;
        return str == null ? "" : str;
    }

    public String getRating() {
        return this.rating;
    }

    public String getReleasedate() {
        return this.releasedate;
    }

    public String getTmdb_id() {
        String str = this.tmdb_id;
        return str == null ? "" : str;
    }

    public void setMovie_image(String str) {
        this.movie_image = str;
    }

    public void setPlot(String str) {
        this.plot = str;
    }

    public void setRating(String str) {
        this.rating = str;
    }

    public void setReleasedate(String str) {
        this.releasedate = str;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Info{movie_image='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.movie_image, '\'', ", plot='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.plot, '\'', ", releasedate='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.releasedate, '\'', ", rating='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.rating, '\'', ", tmdb_id='");
        sbM26m.append(this.tmdb_id);
        sbM26m.append('\'');
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
