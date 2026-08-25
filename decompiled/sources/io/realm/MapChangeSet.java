package io.realm;

/* JADX INFO: loaded from: classes.dex */
public interface MapChangeSet<T> {
    T[] getChanges();

    T[] getDeletions();

    T[] getInsertions();

    boolean isEmpty();
}
