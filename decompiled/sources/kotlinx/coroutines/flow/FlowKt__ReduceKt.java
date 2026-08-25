package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Reduce.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m314d1 = {"\u0000,\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u001a!\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001aE\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\"\u0010\u0004\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001a#\u0010\n\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001aG\u0010\n\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\"\u0010\u0004\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001ay\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\f*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\r\u001a\u0002H\f2H\b\u0004\u0010\u000e\u001aB\b\u0001\u0012\u0013\u0012\u0011H\f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u0011H\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\f0\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000fH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a!\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001a#\u0010\u0016\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001as\u0010\u0017\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u0018\"\b\b\u0001\u0010\u0001*\u0002H\u0018*\b\u0012\u0004\u0012\u0002H\u00010\u00022F\u0010\u000e\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0018¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u0011H\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a!\u0010\u001b\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001a#\u0010\u001c\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, m315d2 = {"first", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "predicate", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "firstOrNull", "fold", "R", "initial", "operation", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "acc", "value", "(Lkotlinx/coroutines/flow/Flow;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "last", "lastOrNull", "reduce", ExifInterface.LATITUDE_SOUTH, "accumulator", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "single", "singleOrNull", "kotlinx-coroutines-core"}, m316k = 5, m317mv = {1, 6, 0}, m319xi = 48, m320xs = "kotlinx/coroutines/flow/FlowKt")
final /* synthetic */ class FlowKt__ReduceKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0, 0}, m326l = {183}, m327m = "first", m328n = {"result", "collector$iv"}, m329s = {"L$0", "L$1"})
    public static final class C26591<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$first$$inlined$collectWhile$1 L$1;
        public int label;
        public /* synthetic */ Object result;

        public C26591(Continuation<? super C26591> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.first(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0, 0, 0}, m326l = {183}, m327m = "first", m328n = {"predicate", "result", "collector$iv"}, m329s = {"L$0", "L$1", "L$2"})
    public static final class C26603<T> extends ContinuationImpl {
        public Function2 L$0;
        public Ref.ObjectRef L$1;
        public FlowKt__ReduceKt$first$$inlined$collectWhile$2 L$2;
        public int label;
        public /* synthetic */ Object result;

        public C26603(Continuation<? super C26603> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.first(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0, 0}, m326l = {183}, m327m = "firstOrNull", m328n = {"result", "collector$iv"}, m329s = {"L$0", "L$1"})
    public static final class C26621<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 L$1;
        public int label;
        public /* synthetic */ Object result;

        public C26621(Continuation<? super C26621> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.firstOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$3 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0, 0}, m326l = {183}, m327m = "firstOrNull", m328n = {"result", "collector$iv"}, m329s = {"L$0", "L$1"})
    public static final class C26633<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 L$1;
        public int label;
        public /* synthetic */ Object result;

        public C26633(Continuation<? super C26633> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.firstOrNull(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 176)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0}, m326l = {44}, m327m = "fold", m328n = {"accumulator"}, m329s = {"L$0"})
    public static final class C26641<T, R> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C26641(Continuation<? super C26641> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__ReduceKt.fold(null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m314d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, m315d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, m316k = 3, m317mv = {1, 6, 0}, m319xi = 176)
    public static final class C26652<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<R> $accumulator;
        public final /* synthetic */ Function3<R, T, Continuation<? super R>, Object> $operation;

        /* JADX WARN: Multi-variable type inference failed */
        public C26652(Ref.ObjectRef<R> objectRef, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3) {
            this.$accumulator = objectRef;
            this.$operation = function3;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0013  */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            FlowKt__ReduceKt$fold$2$emit$1 flowKt__ReduceKt$fold$2$emit$1;
            Ref.ObjectRef objectRef;
            if (continuation instanceof FlowKt__ReduceKt$fold$2$emit$1) {
                flowKt__ReduceKt$fold$2$emit$1 = (FlowKt__ReduceKt$fold$2$emit$1) continuation;
                int i = flowKt__ReduceKt$fold$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    flowKt__ReduceKt$fold$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    flowKt__ReduceKt$fold$2$emit$1 = new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
                }
            } else {
                flowKt__ReduceKt$fold$2$emit$1 = new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
            }
            Object obj = flowKt__ReduceKt$fold$2$emit$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = flowKt__ReduceKt$fold$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Ref.ObjectRef objectRef2 = this.$accumulator;
                Function3<R, T, Continuation<? super R>, Object> function3 = this.$operation;
                T t2 = objectRef2.element;
                flowKt__ReduceKt$fold$2$emit$1.L$0 = objectRef2;
                flowKt__ReduceKt$fold$2$emit$1.label = 1;
                Object objInvoke = function3.invoke((R) t2, t, flowKt__ReduceKt$fold$2$emit$1);
                if (objInvoke == coroutine_suspended) {
                    return coroutine_suspended;
                }
                obj = (T) objInvoke;
                objectRef = objectRef2;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = flowKt__ReduceKt$fold$2$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            objectRef.element = (T) obj;
            return Unit.INSTANCE;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Nullable
        public final Object emit$$forInline(T t, @NotNull Continuation<? super Unit> continuation) {
            InlineMarker.mark(4);
            new FlowKt__ReduceKt$fold$2$emit$1(this, continuation);
            InlineMarker.mark(5);
            Ref.ObjectRef<R> objectRef = this.$accumulator;
            objectRef.element = (T) this.$operation.invoke((R) objectRef.element, t, (Continuation<? super R>) continuation);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$last$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0}, m326l = {155}, m327m = "last", m328n = {"result"}, m329s = {"L$0"})
    public static final class C26661<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C26661(Continuation<? super C26661> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.last(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$last$2 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m314d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, m315d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    public static final class C26672<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<Object> $result;

        public C26672(Ref.ObjectRef<Object> objectRef) {
            this.$result = objectRef;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            this.$result.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0}, m326l = {167}, m327m = "lastOrNull", m328n = {"result"}, m329s = {"L$0"})
    public static final class C26681<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C26681(Continuation<? super C26681> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.lastOrNull(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$2 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m314d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, m315d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    public static final class C26692<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<T> $result;

        public C26692(Ref.ObjectRef<T> objectRef) {
            this.$result = objectRef;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            this.$result.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0}, m326l = {22}, m327m = "reduce", m328n = {"accumulator"}, m329s = {"L$0"})
    public static final class C26701<S, T extends S> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C26701(Continuation<? super C26701> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.reduce(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$reduce$2 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m314d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, m315d2 = {"<anonymous>", "", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    public static final class C26712<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<Object> $accumulator;
        public final /* synthetic */ Function3<S, T, Continuation<? super S>, Object> $operation;

        /* JADX WARN: Multi-variable type inference failed */
        public C26712(Ref.ObjectRef<Object> objectRef, Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> function3) {
            this.$accumulator = objectRef;
            this.$operation = function3;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0013  */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            FlowKt__ReduceKt$reduce$2$emit$1 flowKt__ReduceKt$reduce$2$emit$1;
            Ref.ObjectRef<Object> objectRef;
            if (continuation instanceof FlowKt__ReduceKt$reduce$2$emit$1) {
                flowKt__ReduceKt$reduce$2$emit$1 = (FlowKt__ReduceKt$reduce$2$emit$1) continuation;
                int i = flowKt__ReduceKt$reduce$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    flowKt__ReduceKt$reduce$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    flowKt__ReduceKt$reduce$2$emit$1 = new FlowKt__ReduceKt$reduce$2$emit$1(this, continuation);
                }
            } else {
                flowKt__ReduceKt$reduce$2$emit$1 = new FlowKt__ReduceKt$reduce$2$emit$1(this, continuation);
            }
            Object obj = flowKt__ReduceKt$reduce$2$emit$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = flowKt__ReduceKt$reduce$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                objectRef = this.$accumulator;
                Object obj2 = objectRef.element;
                if (obj2 != NullSurrogateKt.NULL) {
                    Function3<S, T, Continuation<? super S>, Object> function3 = this.$operation;
                    flowKt__ReduceKt$reduce$2$emit$1.L$0 = objectRef;
                    flowKt__ReduceKt$reduce$2$emit$1.label = 1;
                    t = (T) function3.invoke((S) obj2, t, flowKt__ReduceKt$reduce$2$emit$1);
                    if (t == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                Ref.ObjectRef<Object> objectRef2 = flowKt__ReduceKt$reduce$2$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
                objectRef = objectRef2;
                t = (T) obj;
            }
            objectRef.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$single$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0}, m326l = {57}, m327m = "single", m328n = {"result"}, m329s = {"L$0"})
    public static final class C26721<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public int label;
        public /* synthetic */ Object result;

        public C26721(Continuation<? super C26721> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.single(null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$single$2 */
    /* JADX INFO: compiled from: Reduce.kt */
    @Metadata(m314d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, m315d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    public static final class C26732<T> implements FlowCollector, SuspendFunction {
        public final /* synthetic */ Ref.ObjectRef<Object> $result;

        public C26732(Ref.ObjectRef<Object> objectRef) {
            this.$result = objectRef;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        @Nullable
        public final Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
            Ref.ObjectRef<Object> objectRef = this.$result;
            if (!(objectRef.element == NullSurrogateKt.NULL)) {
                throw new IllegalArgumentException("Flow has more than one element".toString());
            }
            objectRef.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$singleOrNull$1 */
    /* JADX INFO: compiled from: Reduce.kt */
    /* JADX INFO: loaded from: classes3.dex */
    @Metadata(m316k = 3, m317mv = {1, 6, 0}, m319xi = 48)
    @DebugMetadata(m323c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m324f = "Reduce.kt", m325i = {0, 0}, m326l = {183}, m327m = "singleOrNull", m328n = {"result", "collector$iv"}, m329s = {"L$0", "L$1"})
    public static final class C26741<T> extends ContinuationImpl {
        public Ref.ObjectRef L$0;
        public FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 L$1;
        public int label;
        public /* synthetic */ Object result;

        public C26741(Continuation<? super C26741> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.singleOrNull(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object first(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) {
        C26591 c26591;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$first$$inlined$collectWhile$1 flowKt__ReduceKt$first$$inlined$collectWhile$1;
        if (continuation instanceof C26591) {
            c26591 = (C26591) continuation;
            int i = c26591.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26591.label = i - Integer.MIN_VALUE;
            } else {
                c26591 = new C26591(continuation);
            }
        } else {
            c26591 = new C26591(continuation);
        }
        Object obj = c26591.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26591.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<T> flowCollector = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                @Nullable
                public Object emit(T t, @NotNull Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    throw new AbortFlowException(this);
                }
            };
            try {
                c26591.L$0 = objectRef2;
                c26591.L$1 = flowCollector;
                c26591.label = 1;
                if (flow.collect(flowCollector, c26591) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$first$$inlined$collectWhile$1 = flowCollector;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$first$$inlined$collectWhile$1);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$first$$inlined$collectWhile$1 = c26591.L$1;
            objectRef = c26591.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$first$$inlined$collectWhile$1);
            }
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Expected at least one element");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object firstOrNull(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) {
        C26621 c26621;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1;
        if (continuation instanceof C26621) {
            c26621 = (C26621) continuation;
            int i = c26621.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26621.label = i - Integer.MIN_VALUE;
            } else {
                c26621 = new C26621(continuation);
            }
        } else {
            c26621 = new C26621(continuation);
        }
        Object obj = c26621.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26621.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowCollector<T> flowCollector = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                @Nullable
                public Object emit(T t, @NotNull Continuation<? super Unit> continuation2) {
                    objectRef2.element = t;
                    throw new AbortFlowException(this);
                }
            };
            try {
                c26621.L$0 = objectRef2;
                c26621.L$1 = flowCollector;
                c26621.label = 1;
                if (flow.collect(flowCollector, c26621) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 = flowCollector;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 = c26621.L$1;
            objectRef = c26621.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1);
            }
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static final <T, R> Object fold(@NotNull Flow<? extends T> flow, R r, @NotNull Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3, @NotNull Continuation<? super R> continuation) {
        C26641 c26641;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C26641) {
            c26641 = (C26641) continuation;
            int i = c26641.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26641.label = i - Integer.MIN_VALUE;
            } else {
                c26641 = new C26641(continuation);
            }
        } else {
            c26641 = new C26641(continuation);
        }
        Object obj = c26641.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26641.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = r;
            FlowCollector<? super Object> c26652 = new C26652<>(objectRef2, function3);
            c26641.L$0 = objectRef2;
            c26641.label = 1;
            if (flow.collect(c26652, c26641) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c26641.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return objectRef.element;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T, R> Object fold$$forInline(Flow<? extends T> flow, R r, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super R> continuation) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = r;
        C26652 c26652 = new C26652(objectRef, function3);
        InlineMarker.mark(0);
        flow.collect(c26652, continuation);
        InlineMarker.mark(1);
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object last(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) {
        C26661 c26661;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C26661) {
            c26661 = (C26661) continuation;
            int i = c26661.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26661.label = i - Integer.MIN_VALUE;
            } else {
                c26661 = new C26661(continuation);
            }
        } else {
            c26661 = new C26661(continuation);
        }
        Object obj = c26661.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26661.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<? super Object> c26672 = new C26672<>(objectRef2);
            c26661.L$0 = objectRef2;
            c26661.label = 1;
            if (flow.collect(c26672, c26661) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c26661.L$0;
            ResultKt.throwOnFailure(obj);
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Expected at least one element");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object lastOrNull(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) {
        C26681 c26681;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C26681) {
            c26681 = (C26681) continuation;
            int i = c26681.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26681.label = i - Integer.MIN_VALUE;
            } else {
                c26681 = new C26681(continuation);
            }
        } else {
            c26681 = new C26681(continuation);
        }
        Object obj = c26681.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26681.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowCollector<? super Object> c26692 = new C26692<>(objectRef2);
            c26681.L$0 = objectRef2;
            c26681.label = 1;
            if (flow.collect(c26692, c26681) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c26681.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return objectRef.element;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    @Nullable
    public static final <S, T extends S> Object reduce(@NotNull Flow<? extends T> flow, @NotNull Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> function3, @NotNull Continuation<? super S> continuation) {
        C26701 c26701;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C26701) {
            c26701 = (C26701) continuation;
            int i = c26701.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26701.label = i - Integer.MIN_VALUE;
            } else {
                c26701 = new C26701(continuation);
            }
        } else {
            c26701 = new C26701(continuation);
        }
        Object obj = c26701.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26701.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = NullSurrogateKt.NULL;
            FlowCollector<? super Object> c26712 = new C26712<>(objectRef2, function3);
            c26701.L$0 = objectRef2;
            c26701.label = 1;
            if (flow.collect(c26712, c26701) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c26701.L$0;
            ResultKt.throwOnFailure(obj);
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Empty flow can't be reduced");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object single(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) {
        C26721 c26721;
        Ref.ObjectRef objectRef;
        if (continuation instanceof C26721) {
            c26721 = (C26721) continuation;
            int i = c26721.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26721.label = i - Integer.MIN_VALUE;
            } else {
                c26721 = new C26721(continuation);
            }
        } else {
            c26721 = new C26721(continuation);
        }
        Object obj = c26721.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26721.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<? super Object> c26732 = new C26732<>(objectRef2);
            c26721.L$0 = objectRef2;
            c26721.label = 1;
            if (flow.collect(c26732, c26721) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = c26721.L$0;
            ResultKt.throwOnFailure(obj);
        }
        T t = objectRef.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Flow is empty");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object singleOrNull(@NotNull Flow<? extends T> flow, @NotNull Continuation<? super T> continuation) {
        C26741 c26741;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1;
        if (continuation instanceof C26741) {
            c26741 = (C26741) continuation;
            int i = c26741.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26741.label = i - Integer.MIN_VALUE;
            } else {
                c26741 = new C26741(continuation);
            }
        } else {
            c26741 = new C26741(continuation);
        }
        Object obj = c26741.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26741.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = (T) NullSurrogateKt.NULL;
            FlowCollector<T> flowCollector = new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                @Nullable
                public Object emit(T t, @NotNull Continuation<? super Unit> continuation2) {
                    boolean z;
                    Ref.ObjectRef objectRef3 = objectRef2;
                    T t2 = objectRef3.element;
                    T t3 = (T) NullSurrogateKt.NULL;
                    if (t2 == t3) {
                        objectRef3.element = t;
                        z = true;
                    } else {
                        objectRef3.element = t3;
                        z = false;
                    }
                    if (z) {
                        return Unit.INSTANCE;
                    }
                    throw new AbortFlowException(this);
                }
            };
            try {
                c26741.L$0 = objectRef2;
                c26741.L$1 = flowCollector;
                c26741.label = 1;
                if (flow.collect(flowCollector, c26741) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 = flowCollector;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1 = c26741.L$1;
            objectRef = c26741.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$singleOrNull$$inlined$collectWhile$1);
            }
        }
        T t = objectRef.element;
        if (t == NullSurrogateKt.NULL) {
            return null;
        }
        return t;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x006b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:29:0x006c  */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object first(@NotNull Flow<? extends T> flow, @NotNull Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        C26603 c26603;
        Ref.ObjectRef objectRef;
        AbortFlowException abortFlowException;
        FlowKt__ReduceKt$first$$inlined$collectWhile$2 flowKt__ReduceKt$first$$inlined$collectWhile$2;
        Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function3;
        Ref.ObjectRef objectRef2;
        T t;
        if (continuation instanceof C26603) {
            c26603 = (C26603) continuation;
            int i = c26603.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26603.label = i - Integer.MIN_VALUE;
            } else {
                c26603 = new C26603(continuation);
            }
        } else {
            c26603 = new C26603(continuation);
        }
        Object obj = c26603.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26603.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$first$$inlined$collectWhile$2 = c26603.L$2;
            objectRef2 = c26603.L$1;
            function3 = c26603.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e) {
                objectRef = objectRef2;
                function2 = function3;
                abortFlowException = e;
                FlowExceptions_commonKt.checkOwnership(abortFlowException, flowKt__ReduceKt$first$$inlined$collectWhile$2);
                function3 = function2;
                objectRef2 = objectRef;
            }
            t = objectRef2.element;
            if (t != NullSurrogateKt.NULL) {
                return t;
            }
            throw new NoSuchElementException(Intrinsics.stringPlus("Expected at least one element matching the predicate ", function3));
        }
        ResultKt.throwOnFailure(obj);
        objectRef = new Ref.ObjectRef();
        objectRef.element = (T) NullSurrogateKt.NULL;
        FlowKt__ReduceKt$first$$inlined$collectWhile$2 flowKt__ReduceKt$first$$inlined$collectWhile$3 = new FlowKt__ReduceKt$first$$inlined$collectWhile$2(function2, objectRef);
        try {
            c26603.L$0 = function2;
            c26603.L$1 = objectRef;
            c26603.L$2 = flowKt__ReduceKt$first$$inlined$collectWhile$3;
            c26603.label = 1;
            if (flow.collect(flowKt__ReduceKt$first$$inlined$collectWhile$3, c26603) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } catch (AbortFlowException e2) {
            abortFlowException = e2;
            flowKt__ReduceKt$first$$inlined$collectWhile$2 = flowKt__ReduceKt$first$$inlined$collectWhile$3;
            FlowExceptions_commonKt.checkOwnership(abortFlowException, flowKt__ReduceKt$first$$inlined$collectWhile$2);
        }
        function3 = function2;
        objectRef2 = objectRef;
        t = objectRef2.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException(Intrinsics.stringPlus("Expected at least one element matching the predicate ", function3));
        FlowExceptions_commonKt.checkOwnership(abortFlowException, flowKt__ReduceKt$first$$inlined$collectWhile$2);
        function3 = function2;
        objectRef2 = objectRef;
        t = objectRef2.element;
        if (t != NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException(Intrinsics.stringPlus("Expected at least one element matching the predicate ", function3));
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Nullable
    public static final <T> Object firstOrNull(@NotNull Flow<? extends T> flow, @NotNull Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        C26633 c26633;
        Ref.ObjectRef objectRef;
        AbortFlowException e;
        FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2;
        if (continuation instanceof C26633) {
            c26633 = (C26633) continuation;
            int i = c26633.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c26633.label = i - Integer.MIN_VALUE;
            } else {
                c26633 = new C26633(continuation);
            }
        } else {
            c26633 = new C26633(continuation);
        }
        Object obj = c26633.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c26633.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3 = new FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2(function2, objectRef2);
            try {
                c26633.L$0 = objectRef2;
                c26633.L$1 = flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3;
                c26633.label = 1;
                if (flow.collect(flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3, c26633) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
            } catch (AbortFlowException e2) {
                objectRef = objectRef2;
                e = e2;
                flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 = flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2 = c26633.L$1;
            objectRef = c26633.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__ReduceKt$firstOrNull$$inlined$collectWhile$2);
            }
        }
        return objectRef.element;
    }
}
