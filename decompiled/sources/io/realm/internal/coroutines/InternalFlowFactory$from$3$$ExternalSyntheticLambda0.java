package io.realm.internal.coroutines;

import io.realm.DynamicRealmObject;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class InternalFlowFactory$from$3$$ExternalSyntheticLambda0 implements RealmChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProducerScope f$0;
    public final /* synthetic */ InternalFlowFactory f$1;

    public /* synthetic */ InternalFlowFactory$from$3$$ExternalSyntheticLambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, int i) {
        this.$r8$classId = i;
        this.f$0 = producerScope;
        this.f$1 = internalFlowFactory;
    }

    @Override // io.realm.RealmChangeListener
    public final void onChange(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                InternalFlowFactory.C23313.m653invokeSuspend$lambda0(this.f$0, this.f$1, (RealmResults) obj);
                break;
            case 1:
                InternalFlowFactory.C23324.m655invokeSuspend$lambda0(this.f$0, this.f$1, (RealmResults) obj);
                break;
            case 2:
                InternalFlowFactory.C23335.m656invokeSuspend$lambda0(this.f$0, this.f$1, (RealmList) obj);
                break;
            case 3:
                InternalFlowFactory.C23346.m657invokeSuspend$lambda0(this.f$0, this.f$1, (RealmList) obj);
                break;
            case 4:
                InternalFlowFactory.C23357.m658invokeSuspend$lambda0(this.f$0, this.f$1, (RealmModel) obj);
                break;
            default:
                InternalFlowFactory.C23368.m659invokeSuspend$lambda0(this.f$0, this.f$1, (DynamicRealmObject) obj);
                break;
        }
    }
}
