package org.lsposed.hiddenapibypass;

import android.util.Log;
import dalvik.system.VMRuntime;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes3.dex */
public final class HiddenApiBypass {

    /* JADX INFO: renamed from: a */
    private static final Unsafe f368a;

    /* JADX INFO: renamed from: b */
    private static final long f369b;

    /* JADX INFO: renamed from: c */
    private static final long f370c;

    /* JADX INFO: renamed from: d */
    private static final long f371d;

    /* JADX INFO: renamed from: e */
    private static final long f372e;

    /* JADX INFO: renamed from: f */
    private static final long f373f;

    /* JADX INFO: renamed from: g */
    private static final long f374g;

    /* JADX INFO: renamed from: h */
    private static final long f375h;

    /* JADX INFO: renamed from: i */
    private static final long f376i;

    /* JADX INFO: renamed from: j */
    private static final long f377j;

    /* JADX INFO: renamed from: k */
    private static final long f378k;

    /* JADX INFO: renamed from: l */
    private static final long f379l;

    /* JADX INFO: renamed from: m */
    private static final long f380m;

    /* JADX INFO: renamed from: n */
    private static final Set f381n = new HashSet();

    static {
        try {
            Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe", new Class[0]).invoke(null, new Object[0]);
            f368a = unsafe;
            f369b = unsafe.objectFieldOffset(Helper$Executable.class.getDeclaredField("artMethod"));
            f370c = unsafe.objectFieldOffset(Helper$Executable.class.getDeclaredField("declaringClass"));
            long jObjectFieldOffset = unsafe.objectFieldOffset(Helper$MethodHandle.class.getDeclaredField("artFieldOrMethod"));
            f371d = jObjectFieldOffset;
            f372e = unsafe.objectFieldOffset(Helper$MethodHandleImpl.class.getDeclaredField("info"));
            long jObjectFieldOffset2 = unsafe.objectFieldOffset(Helper$Class.class.getDeclaredField("methods"));
            f373f = jObjectFieldOffset2;
            long jObjectFieldOffset3 = unsafe.objectFieldOffset(Helper$Class.class.getDeclaredField("iFields"));
            f374g = jObjectFieldOffset3;
            f375h = unsafe.objectFieldOffset(Helper$Class.class.getDeclaredField("sFields"));
            f376i = unsafe.objectFieldOffset(Helper$HandleInfo.class.getDeclaredField("member"));
            Method declaredMethod = Helper$NeverCall.class.getDeclaredMethod("a", new Class[0]);
            Method declaredMethod2 = Helper$NeverCall.class.getDeclaredMethod("b", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod2.setAccessible(true);
            MethodHandle methodHandleUnreflect = MethodHandles.lookup().unreflect(declaredMethod);
            MethodHandle methodHandleUnreflect2 = MethodHandles.lookup().unreflect(declaredMethod2);
            long j = unsafe.getLong(methodHandleUnreflect, jObjectFieldOffset);
            long j2 = unsafe.getLong(methodHandleUnreflect2, jObjectFieldOffset);
            long j3 = unsafe.getLong(Helper$NeverCall.class, jObjectFieldOffset2);
            long j4 = j2 - j;
            f377j = j4;
            f378k = (j - j3) - j4;
            Field declaredField = Helper$NeverCall.class.getDeclaredField("i");
            Field declaredField2 = Helper$NeverCall.class.getDeclaredField("j");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            MethodHandle methodHandleUnreflectGetter2 = MethodHandles.lookup().unreflectGetter(declaredField2);
            long j5 = unsafe.getLong(methodHandleUnreflectGetter, jObjectFieldOffset);
            long j6 = unsafe.getLong(methodHandleUnreflectGetter2, jObjectFieldOffset);
            long j7 = unsafe.getLong(Helper$NeverCall.class, jObjectFieldOffset3);
            f379l = j6 - j5;
            f380m = j5 - j7;
        } catch (ReflectiveOperationException e) {
            Log.e("HiddenApiBypass", "Initialize error", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /* JADX INFO: renamed from: a */
    static boolean m378a(Class[] clsArr, Object[] objArr) {
        if (clsArr.length != objArr.length) {
            return false;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (!clsArr[i].isPrimitive()) {
                Object obj = objArr[i];
                if (obj != null && !clsArr[i].isInstance(obj)) {
                    return false;
                }
            } else {
                if (clsArr[i] == Integer.TYPE && !(objArr[i] instanceof Integer)) {
                    return false;
                }
                if (clsArr[i] == Byte.TYPE && !(objArr[i] instanceof Byte)) {
                    return false;
                }
                if (clsArr[i] == Character.TYPE && !(objArr[i] instanceof Character)) {
                    return false;
                }
                if (clsArr[i] == Boolean.TYPE && !(objArr[i] instanceof Boolean)) {
                    return false;
                }
                if (clsArr[i] == Double.TYPE && !(objArr[i] instanceof Double)) {
                    return false;
                }
                if (clsArr[i] == Float.TYPE && !(objArr[i] instanceof Float)) {
                    return false;
                }
                if (clsArr[i] == Long.TYPE && !(objArr[i] instanceof Long)) {
                    return false;
                }
                if (clsArr[i] == Short.TYPE && !(objArr[i] instanceof Short)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean addHiddenApiExemptions(String... strArr) {
        Set set = f381n;
        set.addAll(Arrays.asList(strArr));
        String[] strArr2 = new String[((HashSet) set).size()];
        set.toArray(strArr2);
        return setHiddenApiExemptions(strArr2);
    }

    public static boolean clearHiddenApiExemptions() {
        ((HashSet) f381n).clear();
        return setHiddenApiExemptions(new String[0]);
    }

    public static Constructor getDeclaredConstructor(Class cls, Class... clsArr) throws NoSuchMethodException {
        for (Executable executable : getDeclaredMethods(cls)) {
            if (executable instanceof Constructor) {
                Class<?>[] parameterTypes = executable.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < clsArr.length; i++) {
                        if (clsArr[i] == parameterTypes[i]) {
                        }
                    }
                    return (Constructor) executable;
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching constructor");
    }

    public static Method getDeclaredMethod(Class cls, String str, Class... clsArr) throws NoSuchMethodException {
        for (Executable executable : getDeclaredMethods(cls)) {
            if (executable.getName().equals(str) && (executable instanceof Method)) {
                Class<?>[] parameterTypes = executable.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < clsArr.length; i++) {
                        if (clsArr[i] == parameterTypes[i]) {
                        }
                    }
                    return (Method) executable;
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    public static List getDeclaredMethods(Class cls) {
        ArrayList arrayList = new ArrayList();
        if (!cls.isPrimitive() && !cls.isArray()) {
            try {
                Method declaredMethod = Helper$NeverCall.class.getDeclaredMethod("a", new Class[0]);
                declaredMethod.setAccessible(true);
                MethodHandle methodHandleUnreflect = MethodHandles.lookup().unreflect(declaredMethod);
                Unsafe unsafe = f368a;
                long j = unsafe.getLong(cls, f373f);
                if (j == 0) {
                    return arrayList;
                }
                int i = unsafe.getInt(j);
                for (int i2 = 0; i2 < i; i2++) {
                    long j2 = (((long) i2) * f377j) + j + f378k;
                    Unsafe unsafe2 = f368a;
                    unsafe2.putLong(methodHandleUnreflect, f371d, j2);
                    unsafe2.putObject(methodHandleUnreflect, f372e, (Object) null);
                    try {
                        MethodHandles.lookup().revealDirect(methodHandleUnreflect);
                    } catch (Throwable unused) {
                    }
                    Unsafe unsafe3 = f368a;
                    arrayList.add((Executable) unsafe3.getObject((MethodHandleInfo) unsafe3.getObject(methodHandleUnreflect, f372e), f376i));
                }
            } catch (IllegalAccessException | NoSuchMethodException unused2) {
            }
        }
        return arrayList;
    }

    public static List getInstanceFields(Class cls) {
        ArrayList arrayList = new ArrayList();
        if (!cls.isPrimitive() && !cls.isArray()) {
            try {
                Field declaredField = Helper$NeverCall.class.getDeclaredField("i");
                declaredField.setAccessible(true);
                MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
                Unsafe unsafe = f368a;
                long j = unsafe.getLong(cls, f374g);
                if (j == 0) {
                    return arrayList;
                }
                int i = unsafe.getInt(j);
                for (int i2 = 0; i2 < i; i2++) {
                    long j2 = (((long) i2) * f379l) + j + f380m;
                    Unsafe unsafe2 = f368a;
                    unsafe2.putLong(methodHandleUnreflectGetter, f371d, j2);
                    unsafe2.putObject(methodHandleUnreflectGetter, f372e, (Object) null);
                    try {
                        MethodHandles.lookup().revealDirect(methodHandleUnreflectGetter);
                    } catch (Throwable unused) {
                    }
                    Unsafe unsafe3 = f368a;
                    arrayList.add((Field) unsafe3.getObject((MethodHandleInfo) unsafe3.getObject(methodHandleUnreflectGetter, f372e), f376i));
                }
            } catch (IllegalAccessException | NoSuchFieldException unused2) {
            }
        }
        return arrayList;
    }

    public static List getStaticFields(Class cls) {
        ArrayList arrayList = new ArrayList();
        if (!cls.isPrimitive() && !cls.isArray()) {
            try {
                Field declaredField = Helper$NeverCall.class.getDeclaredField("s");
                declaredField.setAccessible(true);
                MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
                Unsafe unsafe = f368a;
                long j = unsafe.getLong(cls, f375h);
                if (j == 0) {
                    return arrayList;
                }
                int i = unsafe.getInt(j);
                for (int i2 = 0; i2 < i; i2++) {
                    long j2 = (((long) i2) * f379l) + j + f380m;
                    Unsafe unsafe2 = f368a;
                    unsafe2.putLong(methodHandleUnreflectGetter, f371d, j2);
                    unsafe2.putObject(methodHandleUnreflectGetter, f372e, (Object) null);
                    try {
                        MethodHandles.lookup().revealDirect(methodHandleUnreflectGetter);
                    } catch (Throwable unused) {
                    }
                    Unsafe unsafe3 = f368a;
                    arrayList.add((Field) unsafe3.getObject((MethodHandleInfo) unsafe3.getObject(methodHandleUnreflectGetter, f372e), f376i));
                }
            } catch (IllegalAccessException | NoSuchFieldException unused2) {
            }
        }
        return arrayList;
    }

    public static Object invoke(Class cls, Object obj, String str, Object... objArr) throws NoSuchMethodException {
        if (obj != null && !cls.isInstance(obj)) {
            throw new IllegalArgumentException("this object is not an instance of the given class");
        }
        Method declaredMethod = Helper$InvokeStub.class.getDeclaredMethod("invoke", Object[].class);
        declaredMethod.setAccessible(true);
        Unsafe unsafe = f368a;
        long j = unsafe.getLong(cls, f373f);
        if (j == 0) {
            throw new NoSuchMethodException("Cannot find matching method");
        }
        int i = unsafe.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            f368a.putLong(declaredMethod, f369b, (((long) i2) * f377j) + j + f378k);
            if (str.equals(declaredMethod.getName()) && m378a(declaredMethod.getParameterTypes(), objArr)) {
                return declaredMethod.invoke(obj, objArr);
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    public static Object newInstance(Class cls, Object... objArr) throws NoSuchMethodException {
        Method declaredMethod = Helper$InvokeStub.class.getDeclaredMethod("invoke", Object[].class);
        Constructor declaredConstructor = Helper$InvokeStub.class.getDeclaredConstructor(Object[].class);
        declaredConstructor.setAccessible(true);
        Unsafe unsafe = f368a;
        long j = unsafe.getLong(cls, f373f);
        if (j == 0) {
            throw new NoSuchMethodException("Cannot find matching constructor");
        }
        int i = unsafe.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            long j2 = (((long) i2) * f377j) + j + f378k;
            Unsafe unsafe2 = f368a;
            long j3 = f369b;
            unsafe2.putLong(declaredMethod, j3, j2);
            if ("<init>".equals(declaredMethod.getName())) {
                unsafe2.putLong(declaredConstructor, j3, j2);
                unsafe2.putObject(declaredConstructor, f370c, cls);
                if (m378a(declaredConstructor.getParameterTypes(), objArr)) {
                    return declaredConstructor.newInstance(objArr);
                }
            }
        }
        throw new NoSuchMethodException("Cannot find matching constructor");
    }

    public static boolean setHiddenApiExemptions(String... strArr) {
        try {
            invoke(VMRuntime.class, invoke(VMRuntime.class, null, "getRuntime", new Object[0]), "setHiddenApiExemptions", strArr);
            return true;
        } catch (Throwable th) {
            Log.w("HiddenApiBypass", "setHiddenApiExemptions", th);
            return false;
        }
    }
}
