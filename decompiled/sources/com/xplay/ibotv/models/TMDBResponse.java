package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class TMDBResponse implements Serializable {
    public String backdrop_path;

    /* JADX INFO: renamed from: id */
    public int f327id;
    public String imdb_id;
    public String original_language;
    public String overview;
    public String poster_path;
    public String release_date;
    public int runtime;
    public float vote_average;

    public String getBackdrop_path() {
        return this.backdrop_path;
    }

    public int getId() {
        return this.f327id;
    }

    public String getImdb_id() {
        return this.imdb_id;
    }

    public String getOriginal_language() {
        return this.original_language;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getPoster_path() {
        return this.poster_path;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public float getVote_average() {
        return this.vote_average;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("TMDBResponse{backdrop_path='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.backdrop_path, '\'', ", id='");
        sbM26m.append(this.f327id);
        sbM26m.append('\'');
        sbM26m.append(", imdb_id='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.imdb_id, '\'', ", original_language='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.original_language, '\'', ", overview='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.overview, '\'', ", poster_path='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.poster_path, '\'', ", release_date='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.release_date, '\'', ", runtime=");
        sbM26m.append(this.runtime);
        sbM26m.append(", vote_average=");
        sbM26m.append(this.vote_average);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
