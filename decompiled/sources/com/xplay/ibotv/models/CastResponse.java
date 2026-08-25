package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CastResponse {
    public List<CastModel> cast;

    /* JADX INFO: renamed from: id */
    public int f316id;

    public List<CastModel> getCast() {
        return this.cast;
    }

    public int getId() {
        return this.f316id;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("CastResponse{id=");
        sbM26m.append(this.f316id);
        sbM26m.append(", cast=");
        sbM26m.append(this.cast);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
