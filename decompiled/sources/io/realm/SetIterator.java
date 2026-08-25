package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.OsSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes.dex */
abstract class SetIterator<E> implements Iterator<E> {
    public final BaseRealm baseRealm;
    public final OsSet osSet;
    private int pos = -1;

    public SetIterator(OsSet osSet, BaseRealm baseRealm) {
        this.osSet = osSet;
        this.baseRealm = baseRealm;
    }

    public E getValueAtIndex(int i) {
        return (E) this.osSet.getValueAtIndex(i);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return ((long) (this.pos + 1)) < this.osSet.size();
    }

    @Override // java.util.Iterator
    public E next() {
        this.pos++;
        long size = this.osSet.size();
        int i = this.pos;
        if (i < size) {
            return getValueAtIndex(i);
        }
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Cannot access index ");
        sbM26m.append(this.pos);
        sbM26m.append(" when size is ");
        sbM26m.append(size);
        sbM26m.append(". Remember to check hasNext() before using next().");
        throw new NoSuchElementException(sbM26m.toString());
    }
}
