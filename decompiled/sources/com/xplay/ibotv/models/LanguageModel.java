package com.xplay.ibotv.models;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class LanguageModel implements Serializable {

    @SerializedName("code")
    private String code;

    /* JADX INFO: renamed from: id */
    @SerializedName(TtmlNode.ATTR_ID)
    private String f322id;

    @SerializedName("name")
    private String name;

    @SerializedName("words")
    private WordModels wordModels;

    public String getCode() {
        return this.code;
    }

    public String getId() {
        return this.f322id;
    }

    public String getName() {
        return this.name;
    }

    public WordModels getWordModel() {
        return this.wordModels;
    }
}
