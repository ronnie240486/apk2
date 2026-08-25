package org.bson;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Arrays;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes2.dex */
public final class BsonRegularExpression extends BsonValue {
    private final String options;
    private final String pattern;

    public BsonRegularExpression(String str, String str2) {
        this.pattern = (String) Assertions.notNull("pattern", str);
        this.options = str2 == null ? "" : sortOptionCharacters(str2);
    }

    private String sortOptionCharacters(String str) {
        char[] charArray = str.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || BsonRegularExpression.class != obj.getClass()) {
            return false;
        }
        BsonRegularExpression bsonRegularExpression = (BsonRegularExpression) obj;
        return this.options.equals(bsonRegularExpression.options) && this.pattern.equals(bsonRegularExpression.pattern);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.REGULAR_EXPRESSION;
    }

    public String getOptions() {
        return this.options;
    }

    public String getPattern() {
        return this.pattern;
    }

    public int hashCode() {
        return this.options.hashCode() + (this.pattern.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("BsonRegularExpression{pattern='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.pattern, '\'', ", options='");
        sbM26m.append(this.options);
        sbM26m.append('\'');
        sbM26m.append('}');
        return sbM26m.toString();
    }

    public BsonRegularExpression(String str) {
        this(str, null);
    }
}
