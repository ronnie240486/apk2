package androidx.constraintlayout.core.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.os.EnvironmentCompat;

/* JADX INFO: loaded from: classes3.dex */
public class CLParsingException extends Exception {
    private final String mElementClass;
    private final int mLineNumber;
    private final String mReason;

    public CLParsingException(String str, CLElement cLElement) {
        this.mReason = str;
        if (cLElement != null) {
            this.mElementClass = cLElement.getStrClass();
            this.mLineNumber = cLElement.getLine();
        } else {
            this.mElementClass = EnvironmentCompat.MEDIA_UNKNOWN;
            this.mLineNumber = 0;
        }
    }

    public String reason() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mReason);
        sb.append(" (");
        sb.append(this.mElementClass);
        sb.append(" at line ");
        return Insets$$ExternalSyntheticOutline0.m22m(sb, this.mLineNumber, ")");
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("CLParsingException (");
        sbM26m.append(hashCode());
        sbM26m.append(") : ");
        sbM26m.append(reason());
        return sbM26m.toString();
    }
}
