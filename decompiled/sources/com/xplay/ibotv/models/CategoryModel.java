package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class CategoryModel implements Serializable {

    /* JADX INFO: renamed from: id */
    @SerializedName("category_id")
    private String f318id;

    @SerializedName("category_name")
    private String name;

    public CategoryModel(String str, String str2) {
        this.f318id = str;
        this.name = str2;
    }

    public String getId() {
        return this.f318id;
    }

    public String getName() {
        String str = this.name;
        return (str == null || !str.contains("!@#%")) ? this.name : this.name.split("!@#%")[1];
    }

    public void setId(String str) {
        this.f318id = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("CategoryModel{category_id='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.f318id, '\'', ", category_name='");
        sbM26m.append(this.name);
        sbM26m.append('\'');
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
