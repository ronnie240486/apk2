package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes3.dex */
public class BsonDateTime extends BsonValue implements Comparable<BsonDateTime> {
    private final long value;

    public BsonDateTime(long j) {
        this.value = j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((BsonDateTime) obj).value;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DATE_TIME;
    }

    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("BsonDateTime{value=");
        sbM26m.append(this.value);
        sbM26m.append('}');
        return sbM26m.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(BsonDateTime bsonDateTime) {
        return Long.valueOf(this.value).compareTo(Long.valueOf(bsonDateTime.value));
    }
}
