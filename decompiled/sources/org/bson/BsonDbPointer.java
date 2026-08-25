package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes3.dex */
public class BsonDbPointer extends BsonValue {

    /* JADX INFO: renamed from: id */
    private final ObjectId f363id;
    private final String namespace;

    public BsonDbPointer(String str, ObjectId objectId) {
        if (str == null) {
            throw new IllegalArgumentException("namespace can not be null");
        }
        if (objectId == null) {
            throw new IllegalArgumentException("id can not be null");
        }
        this.namespace = str;
        this.f363id = objectId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BsonDbPointer bsonDbPointer = (BsonDbPointer) obj;
        return this.f363id.equals(bsonDbPointer.f363id) && this.namespace.equals(bsonDbPointer.namespace);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.DB_POINTER;
    }

    public ObjectId getId() {
        return this.f363id;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int hashCode() {
        return this.f363id.hashCode() + (this.namespace.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("BsonDbPointer{namespace='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.namespace, '\'', ", id=");
        sbM26m.append(this.f363id);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
