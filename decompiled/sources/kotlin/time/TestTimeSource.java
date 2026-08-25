package kotlin.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import kotlin.Metadata;
import kotlin.SinceKotlin;

/* JADX INFO: compiled from: TimeSources.kt */
/* JADX INFO: loaded from: classes3.dex */
@SinceKotlin(version = "1.3")
@Metadata(m313bv = {}, m314d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\r\u0010\u000eJ\u001a\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006J\u001b\u0010\t\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\b\u0010\u0006R\u0016\u0010\u000b\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u000b\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, m315d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/Duration;", TypedValues.TransitionType.S_DURATION, "", "overflow-LRDsOJo", "(J)V", "overflow", "plusAssign-LRDsOJo", "plusAssign", "", "reading", "J", "<init>", "()V", "kotlin-stdlib"}, m316k = 1, m317mv = {1, 7, 1})
@ExperimentalTime
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
    }

    /* JADX INFO: renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m2109overflowLRDsOJo(long duration) {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("TestTimeSource will overflow if its reading ");
        sbM26m.append(this.reading);
        sbM26m.append("ns is advanced by ");
        sbM26m.append((Object) Duration.m2025toStringimpl(duration));
        sbM26m.append('.');
        throw new IllegalStateException(sbM26m.toString());
    }

    /* JADX INFO: renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m2110plusAssignLRDsOJo(long duration) {
        long j;
        long jM2022toLongimpl = Duration.m2022toLongimpl(duration, getUnit());
        if (jM2022toLongimpl == Long.MIN_VALUE || jM2022toLongimpl == Long.MAX_VALUE) {
            double dM2019toDoubleimpl = this.reading + Duration.m2019toDoubleimpl(duration, getUnit());
            if (dM2019toDoubleimpl > 9.223372036854776E18d || dM2019toDoubleimpl < -9.223372036854776E18d) {
                m2109overflowLRDsOJo(duration);
            }
            j = (long) dM2019toDoubleimpl;
        } else {
            long j2 = this.reading;
            j = j2 + jM2022toLongimpl;
            if ((jM2022toLongimpl ^ j2) >= 0 && (j2 ^ j) < 0) {
                m2109overflowLRDsOJo(duration);
            }
        }
        this.reading = j;
    }

    @Override // kotlin.time.AbstractLongTimeSource
    /* JADX INFO: renamed from: read, reason: from getter */
    public final long getReading() {
        return this.reading;
    }
}
