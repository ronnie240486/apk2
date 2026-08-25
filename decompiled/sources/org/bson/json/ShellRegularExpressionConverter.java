package org.bson.json;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import org.bson.BsonRegularExpression;

/* JADX INFO: loaded from: classes2.dex */
class ShellRegularExpressionConverter implements Converter<BsonRegularExpression> {
    @Override // org.bson.json.Converter
    public void convert(BsonRegularExpression bsonRegularExpression, StrictJsonWriter strictJsonWriter) {
        StringBuilder sbM29m = Insets$$ExternalSyntheticOutline0.m29m("/", bsonRegularExpression.getPattern().equals("") ? "(?:)" : bsonRegularExpression.getPattern().replace("/", "\\/"), "/");
        sbM29m.append(bsonRegularExpression.getOptions());
        strictJsonWriter.writeRaw(sbM29m.toString());
    }
}
