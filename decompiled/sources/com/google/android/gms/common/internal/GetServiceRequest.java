package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes2.dex */
@KeepForSdk
@SafeParcelable.Class(creator = "GetServiceRequestCreator")
@SafeParcelable.Reserved({9})
public class GetServiceRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzm();

    @SafeParcelable.VersionField(m91id = 1)
    public final int zza;

    @SafeParcelable.Field(m89id = 2)
    public final int zzb;

    @SafeParcelable.Field(m89id = 3)
    public int zzc;

    @SafeParcelable.Field(m89id = 4)
    public String zzd;

    @SafeParcelable.Field(m89id = 5)
    public IBinder zze;

    @SafeParcelable.Field(m89id = 6)
    public Scope[] zzf;

    @SafeParcelable.Field(m89id = 7)
    public Bundle zzg;

    @Nullable
    @SafeParcelable.Field(m89id = 8)
    public Account zzh;

    @SafeParcelable.Field(m89id = 10)
    public Feature[] zzi;

    @SafeParcelable.Field(m89id = 11)
    public Feature[] zzj;

    @SafeParcelable.Field(m89id = 12)
    public boolean zzk;

    @SafeParcelable.Field(defaultValue = "0", m89id = 13)
    public int zzl;

    @SafeParcelable.Field(getter = "isRequestingTelemetryConfiguration", m89id = 14)
    public boolean zzm;

    @Nullable
    @SafeParcelable.Field(getter = "getAttributionTag", m89id = 15)
    private String zzn;

    @SafeParcelable.Constructor
    public GetServiceRequest(@SafeParcelable.Param(m90id = 1) int i, @SafeParcelable.Param(m90id = 2) int i2, @SafeParcelable.Param(m90id = 3) int i3, @SafeParcelable.Param(m90id = 4) String str, @SafeParcelable.Param(m90id = 5) IBinder iBinder, @SafeParcelable.Param(m90id = 6) Scope[] scopeArr, @SafeParcelable.Param(m90id = 7) Bundle bundle, @SafeParcelable.Param(m90id = 8) Account account, @SafeParcelable.Param(m90id = 10) Feature[] featureArr, @SafeParcelable.Param(m90id = 11) Feature[] featureArr2, @SafeParcelable.Param(m90id = 12) boolean z, @SafeParcelable.Param(m90id = 13) int i4, @SafeParcelable.Param(m90id = 14) boolean z2, @Nullable @SafeParcelable.Param(m90id = 15) String str2) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
        if ("com.google.android.gms".equals(str)) {
            this.zzd = "com.google.android.gms";
        } else {
            this.zzd = str;
        }
        if (i < 2) {
            this.zzh = iBinder != null ? AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(iBinder)) : null;
        } else {
            this.zze = iBinder;
            this.zzh = account;
        }
        this.zzf = scopeArr;
        this.zzg = bundle;
        this.zzi = featureArr;
        this.zzj = featureArr2;
        this.zzk = z;
        this.zzl = i4;
        this.zzm = z2;
        this.zzn = str2;
    }

    @NonNull
    @KeepForSdk
    public Bundle getExtraArgs() {
        return this.zzg;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }

    @Nullable
    public final String zza() {
        return this.zzn;
    }

    public GetServiceRequest(int i, @Nullable String str) {
        this.zza = 6;
        this.zzc = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzb = i;
        this.zzk = true;
        this.zzn = str;
    }
}
