package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: SafeCollector.common.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m314d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, m315d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, m316k = 1, m317mv = {1, 6, 0}, m319xi = 48)
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7 implements Flow<Integer> {
    public final /* synthetic */ int[] $this_asFlow$inlined;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7$1 */
    /* JADX INFO: compiled from: SafeCollector.common.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7", m324f = "Builders.kt", m325i = {0}, m326l = {115}, m327m = "collect", m328n = {"$this$asFlow_u24lambda_u2d13"}, m329s = {"L$0"})
    public static final class C26141 extends ContinuationImpl {
        public int I$0;
        public int I$1;
        public FlowCollector L$0;
        public int[] L$1;
        public int label;
        public /* synthetic */ Object result;

        public C26141(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7.this.collect(null, this);
        }
    }

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7(int[] iArr) {
        this.$this_asFlow$inlined = iArr;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.Flow
    @Nullable
    public Object collect(@NotNull FlowCollector<? super Integer> flowCollector, @NotNull Continuation<? super Unit> continuation) {
        C26141 c26141;
        int[] iArr;
        int i;
        FlowCollector flowCollector2;
        int length;
        if (continuation instanceof C26141) {
            c26141 = (C26141) continuation;
            int i2 = c26141.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c26141.label = i2 - Integer.MIN_VALUE;
            } else {
                c26141 = new C26141(continuation);
            }
        } else {
            c26141 = new C26141(continuation);
        }
        Object obj = c26141.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i3 = c26141.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            iArr = this.$this_asFlow$inlined;
            i = 0;
            flowCollector2 = flowCollector;
            length = iArr.length;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            length = c26141.I$1;
            i = c26141.I$0;
            iArr = c26141.L$1;
            flowCollector2 = c26141.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (i < length) {
            int i4 = iArr[i];
            i++;
            Integer numBoxInt = Boxing.boxInt(i4);
            c26141.L$0 = flowCollector2;
            c26141.L$1 = iArr;
            c26141.I$0 = i;
            c26141.I$1 = length;
            c26141.label = 1;
            if (flowCollector2.emit(numBoxInt, c26141) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
