package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CastModel implements Serializable {
    public boolean adult;
    public int cast_id;
    public String character;
    public String credit_id;
    public int gender;

    /* JADX INFO: renamed from: id */
    public int f315id;
    public String known_for_department;
    public String name;
    public int order;
    public String original_name;
    public float popularity;
    public String profile_path;

    public int getCast_id() {
        return this.cast_id;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getCredit_id() {
        return this.credit_id;
    }

    public int getGender() {
        return this.gender;
    }

    public int getId() {
        return this.f315id;
    }

    public String getKnown_for_department() {
        return this.known_for_department;
    }

    public String getName() {
        return this.name;
    }

    public int getOrder() {
        return this.order;
    }

    public String getOriginal_name() {
        return this.original_name;
    }

    public float getPopularity() {
        return this.popularity;
    }

    public String getProfile_path() {
        return this.profile_path;
    }

    public boolean isAdult() {
        return this.adult;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("CastModel{adult='");
        sbM26m.append(this.adult);
        sbM26m.append('\'');
        sbM26m.append(", gender=");
        sbM26m.append(this.gender);
        sbM26m.append(", id=");
        sbM26m.append(this.f315id);
        sbM26m.append(", known_for_department='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.known_for_department, '\'', ", name='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.name, '\'', ", original_name='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.original_name, '\'', ", popularity=");
        sbM26m.append(this.popularity);
        sbM26m.append(", profile_path='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.profile_path, '\'', ", cast_id=");
        sbM26m.append(this.cast_id);
        sbM26m.append(", character='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.character, '\'', ", credit_id='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.credit_id, '\'', ", order=");
        return Insets$$ExternalSyntheticOutline0.m21m(sbM26m, this.order, '}');
    }
}
