package com.android.volley;

import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AsyncCache {

    /* JADX INFO: loaded from: classes.dex */
    public interface OnGetCompleteCallback {
        void onGetComplete(@Nullable Cache.Entry entry);
    }

    /* JADX INFO: loaded from: classes3.dex */
    public interface OnWriteCompleteCallback {
        void onWriteComplete();
    }

    public abstract void clear(OnWriteCompleteCallback onWriteCompleteCallback);

    public abstract void get(String str, OnGetCompleteCallback onGetCompleteCallback);

    public abstract void initialize(OnWriteCompleteCallback onWriteCompleteCallback);

    public abstract void invalidate(String str, boolean z, OnWriteCompleteCallback onWriteCompleteCallback);

    public abstract void put(String str, Cache.Entry entry, OnWriteCompleteCallback onWriteCompleteCallback);

    public abstract void remove(String str, OnWriteCompleteCallback onWriteCompleteCallback);
}
