package androidx.lifecycle;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public interface LifecycleRegistryOwner extends LifecycleOwner {

    /* JADX INFO: renamed from: androidx.lifecycle.LifecycleRegistryOwner$-CC, reason: invalid class name */
    /* JADX INFO: loaded from: classes2.dex */
    public final /* synthetic */ class CC {
    }

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    /* bridge */ /* synthetic */ Lifecycle getLifecycle();

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    LifecycleRegistry getLifecycle();
}
