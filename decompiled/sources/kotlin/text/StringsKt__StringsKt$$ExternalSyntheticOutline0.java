package kotlin.text;

import android.util.JsonReader;
import com.google.android.play.core.internal.zzag;
import com.google.android.play.core.tasks.zzi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class StringsKt__StringsKt$$ExternalSyntheticOutline0 {
    /* JADX INFO: renamed from: m */
    public static int m336m(CharSequence charSequence, String str, Function1 function1, String str2) {
        Intrinsics.checkNotNullParameter(charSequence, str);
        Intrinsics.checkNotNullParameter(function1, str2);
        return charSequence.length();
    }

    /* JADX INFO: renamed from: m */
    public static int m337m(String str, int i) {
        return String.valueOf(str).length() + i;
    }

    /* JADX INFO: renamed from: m */
    public static int m338m(UInt uInt, int i) {
        return UInt.m759constructorimpl(uInt.getData() + i);
    }

    /* JADX INFO: renamed from: m */
    public static int m339m(IntRange intRange, int i) {
        return intRange.getEndInclusive().intValue() + i;
    }

    /* JADX INFO: renamed from: m */
    public static zzi m340m(zzag zzagVar, String str, Object[] objArr) {
        zzagVar.zzd(str, objArr);
        return new zzi();
    }

    /* JADX INFO: renamed from: m */
    public static IllegalArgumentException m341m(JsonReader jsonReader, String str) throws IOException {
        jsonReader.skipValue();
        return new IllegalArgumentException(str);
    }

    /* JADX INFO: renamed from: m */
    public static Integer m342m(HashMap map, Integer num, String str, int i, String str2) {
        map.put(num, str);
        Integer numValueOf = Integer.valueOf(i);
        map.put(numValueOf, str2);
        return numValueOf;
    }

    /* JADX INFO: renamed from: m */
    public static Object m343m(CharSequence charSequence, int i, Function1 function1) {
        return function1.invoke(Character.valueOf(charSequence.charAt(i)));
    }

    /* JADX INFO: renamed from: m */
    public static Object m344m(byte[] bArr, int i, Function1 function1) {
        return function1.invoke(UByte.m677boximpl(UByteArray.m740getw2LRezQ(bArr, i)));
    }

    /* JADX INFO: renamed from: m */
    public static Object m345m(int[] iArr, int i, Function1 function1) {
        return function1.invoke(UInt.m753boximpl(UIntArray.m818getpVg5ArA(iArr, i)));
    }

    /* JADX INFO: renamed from: m */
    public static Object m346m(long[] jArr, int i, Function1 function1) {
        return function1.invoke(ULong.m831boximpl(ULongArray.m896getsVKNKU(jArr, i)));
    }

    /* JADX INFO: renamed from: m */
    public static Object m347m(short[] sArr, int i, Function1 function1) {
        return function1.invoke(UShort.m937boximpl(UShortArray.m1000getMh2AYeg(sArr, i)));
    }

    /* JADX INFO: renamed from: m */
    public static String m348m(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m349m(int i, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static String m350m(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb.toString();
    }

    /* JADX INFO: renamed from: m */
    public static StringBuilder m351m(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb;
    }

    /* JADX INFO: renamed from: m */
    public static ArrayList m352m(LinkedHashMap linkedHashMap, Object obj) {
        ArrayList arrayList = new ArrayList();
        linkedHashMap.put(obj, arrayList);
        return arrayList;
    }

    /* JADX INFO: renamed from: m */
    public static ArrayList m353m(Map map, Object obj) {
        ArrayList arrayList = new ArrayList();
        map.put(obj, arrayList);
        return arrayList;
    }

    /* JADX INFO: renamed from: m */
    public static Iterator m354m(Iterable iterable, String str, Function1 function1, String str2) {
        Intrinsics.checkNotNullParameter(iterable, str);
        Intrinsics.checkNotNullParameter(function1, str2);
        return iterable.iterator();
    }

    /* JADX INFO: renamed from: m */
    public static Iterator m355m(Sequence sequence, String str, Function1 function1, String str2) {
        Intrinsics.checkNotNullParameter(sequence, str);
        Intrinsics.checkNotNullParameter(function1, str2);
        return sequence.iterator();
    }

    /* JADX INFO: renamed from: m */
    public static KotlinNothingValueException m356m() {
        FlowKt.noImpl();
        return new KotlinNothingValueException();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.collections.IntIterator] */
    /* JADX INFO: renamed from: m */
    public static IntIterator m357m(int i, int i2) {
        return new IntRange(i, i2).iterator();
    }
}
