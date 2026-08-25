package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.text.StringsKt__StringsKt$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class AbstractIdleService implements Service {
    private final Service delegate;
    private final Supplier<String> threadNameSupplier;

    /* JADX INFO: renamed from: com.google.common.util.concurrent.AbstractIdleService$1 */
    /* JADX INFO: loaded from: classes.dex */
    class ExecutorC19121 implements Executor {
        public ExecutorC19121() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            MoreExecutors.newThread((String) AbstractIdleService.this.threadNameSupplier.get(), runnable).start();
        }
    }

    /* JADX INFO: loaded from: classes.dex */
    public final class DelegateService extends AbstractService {
        private DelegateService() {
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStart() {
            AbstractIdleService abstractIdleService = AbstractIdleService.this;
            Objects.requireNonNull(abstractIdleService);
            MoreExecutors.renamingDecorator(abstractIdleService.new ExecutorC19121(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractIdleService.DelegateService.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AbstractIdleService.this.startUp();
                        DelegateService.this.notifyStarted();
                    } catch (Throwable th) {
                        DelegateService.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStop() {
            AbstractIdleService abstractIdleService = AbstractIdleService.this;
            Objects.requireNonNull(abstractIdleService);
            MoreExecutors.renamingDecorator(abstractIdleService.new ExecutorC19121(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractIdleService.DelegateService.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AbstractIdleService.this.shutDown();
                        DelegateService.this.notifyStopped();
                    } catch (Throwable th) {
                        DelegateService.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractIdleService.this.toString();
        }

        public /* synthetic */ DelegateService(AbstractIdleService abstractIdleService, ExecutorC19121 executorC19121) {
            this();
        }
    }

    public final class ThreadNameSupplier implements Supplier<String> {
        private ThreadNameSupplier() {
        }

        public /* synthetic */ ThreadNameSupplier(AbstractIdleService abstractIdleService, ExecutorC19121 executorC19121) {
            this();
        }

        @Override // com.google.common.base.Supplier
        public String get() {
            String simpleName = AbstractIdleService.this.getClass().getSimpleName();
            String strValueOf = String.valueOf(AbstractIdleService.this.state());
            return StringsKt__StringsKt$$ExternalSyntheticOutline0.m349m(strValueOf.length() + simpleName.length() + 1, simpleName, " ", strValueOf);
        }
    }

    public AbstractIdleService() {
        ExecutorC19121 executorC19121 = null;
        this.threadNameSupplier = new ThreadNameSupplier(this, executorC19121);
        this.delegate = new DelegateService(this, executorC19121);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    public abstract void shutDown() throws Exception;

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    public abstract void startUp() throws Exception;

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public String toString() {
        String simpleName = getClass().getSimpleName();
        String strValueOf = String.valueOf(state());
        return StringsKt__StringsKt$$ExternalSyntheticOutline0.m350m(strValueOf.length() + simpleName.length() + 3, simpleName, " [", strValueOf, "]");
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(j, timeUnit);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(j, timeUnit);
    }
}
