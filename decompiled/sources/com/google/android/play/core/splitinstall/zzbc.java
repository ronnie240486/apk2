package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.play.core.internal.zzbz;
import com.google.android.play.core.internal.zzce;
import com.google.android.play.core.internal.zzch;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.text.StringsKt__StringsKt$$ExternalSyntheticOutline0;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbc {
    private static final com.google.android.play.core.internal.zzag zzb = new com.google.android.play.core.internal.zzag("SplitInstallService");
    private static final Intent zzc = new Intent("com.google.android.play.core.splitinstall.BIND_SPLIT_INSTALL_SERVICE").setPackage("com.android.vending");

    @Nullable
    @VisibleForTesting
    public com.google.android.play.core.internal.zzas zza;
    private final String zzd;

    public zzbc(Context context, String str) {
        this.zzd = str;
        if (zzch.zzb(context)) {
            this.zza = new com.google.android.play.core.internal.zzas(zzce.zza(context), zzb, "SplitInstallService", zzc, new com.google.android.play.core.internal.zzan() { // from class: com.google.android.play.core.splitinstall.zzak
                @Override // com.google.android.play.core.internal.zzan
                public final Object zza(IBinder iBinder) {
                    return zzbz.zzb(iBinder);
                }
            }, null);
        }
    }

    public static /* bridge */ /* synthetic */ Bundle zza() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore_version_code", 11003);
        return bundle;
    }

    public static /* bridge */ /* synthetic */ ArrayList zzl(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Bundle bundle = new Bundle();
            bundle.putString("language", str);
            arrayList.add(bundle);
        }
        return arrayList;
    }

    public static /* bridge */ /* synthetic */ ArrayList zzm(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Bundle bundle = new Bundle();
            bundle.putString("module_name", str);
            arrayList.add(bundle);
        }
        return arrayList;
    }

    private static Task zzn() {
        zzb.zzb("onError(%d)", -14);
        return Tasks.zza(new SplitInstallException(-14));
    }

    public final Task zzc(int i) {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "cancelInstall(%d)", new Object[]{Integer.valueOf(i)});
        this.zza.zzq(new zzas(this, zziVarM340m, i, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }

    public final Task zzd(List list) {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "deferredInstall(%s)", new Object[]{list});
        this.zza.zzq(new zzan(this, zziVarM340m, list, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }

    public final Task zze(List list) {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "deferredLanguageInstall(%s)", new Object[]{list});
        this.zza.zzq(new zzao(this, zziVarM340m, list, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }

    public final Task zzf(List list) {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "deferredLanguageUninstall(%s)", new Object[]{list});
        this.zza.zzq(new zzap(this, zziVarM340m, list, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }

    public final Task zzg(List list) {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "deferredUninstall(%s)", new Object[]{list});
        this.zza.zzq(new zzam(this, zziVarM340m, list, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }

    public final Task zzh(int i) {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "getSessionState(%d)", new Object[]{Integer.valueOf(i)});
        this.zza.zzq(new zzaq(this, zziVarM340m, i, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }

    public final Task zzi() {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "getSessionStates", new Object[0]);
        this.zza.zzq(new zzar(this, zziVarM340m, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }

    public final Task zzj(Collection collection, Collection collection2) {
        if (this.zza == null) {
            return zzn();
        }
        com.google.android.play.core.tasks.zzi zziVarM340m = StringsKt__StringsKt$$ExternalSyntheticOutline0.m340m(zzb, "startInstall(%s,%s)", new Object[]{collection, collection2});
        this.zza.zzq(new zzal(this, zziVarM340m, collection, collection2, zziVarM340m), zziVarM340m);
        return zziVarM340m.zza();
    }
}
