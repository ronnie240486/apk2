package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class MovieCreditModel implements Serializable {
    public String backdrop_path;
    public String character;
    public String credit_id;

    /* JADX INFO: renamed from: id */
    public int f323id;
    public String original_language;
    public String original_title;
    public String overview;
    public String poster_path;
    public String release_date;
    public String title;
    public float vote_average;
    public int vote_count;

    public String getBackdrop_path() {
        return this.backdrop_path;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getCredit_id() {
        return this.credit_id;
    }

    public int getId() {
        return this.f323id;
    }

    public String getOriginal_language() {
        return this.original_language;
    }

    public String getOriginal_title() {
        return this.original_title;
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

    public String getTitle() {
        return this.title;
    }

    public float getVote_average() {
        return this.vote_average;
    }

    public int getVote_count() {
        return this.vote_count;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("MovieCreditModels{id='");
        sbM26m.append(this.f323id);
        sbM26m.append('\'');
        sbM26m.append("character='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.character, '\'', ", credit_id='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.credit_id, '\'', ", release_date='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.release_date, '\'', ", vote_count=");
        sbM26m.append(this.vote_count);
        sbM26m.append(", vote_average=");
        sbM26m.append(this.vote_average);
        sbM26m.append(", title='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.title, '\'', ", original_language='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.original_language, '\'', ", original_title='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.original_title, '\'', ", backdrop_path='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.backdrop_path, '\'', ", overview='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.overview, '\'', ", poster_path='");
        sbM26m.append(this.poster_path);
        sbM26m.append('\'');
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
