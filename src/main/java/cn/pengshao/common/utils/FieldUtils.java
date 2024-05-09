package cn.pengshao.common.utils;

import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/5/9 22:29
 */
public class FieldUtils {

    public static List<Field> findAnnotatedField(Class<?> aClass, Class<? extends Annotation> annotationClass) {
        return findField(aClass, (f) -> f.isAnnotationPresent(annotationClass));
    }

    public static List<Field> findField(Class<?> aClass, Function<Field, Boolean> function) {
        ArrayList<Field> result;
        for (result = new ArrayList<>(); aClass != null; aClass = aClass.getSuperclass()) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field f : fields) {
                if (function.apply(f)) {
                    result.add(f);
                }
            }
        }
        return result;
    }

    public static void setFinalField(Object obj, String fieldName, Object value) throws NoSuchFieldException {
        try {
            setFinalField(obj, obj.getClass().getField(fieldName), value);
        } catch (Throwable ex) {
            throw ex;
        }
    }

    public static void setFinalField(Object obj, Field field, Object value) {
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        long fieldOffset = unsafe.objectFieldOffset(field);
        unsafe.putObject(obj, fieldOffset, value);
    }

    public static Object getField(Object obj, String fieldName) throws NoSuchFieldException {
        try {
            return getField(obj, obj.getClass().getField(fieldName));
        } catch (Throwable ex) {
            throw ex;
        }
    }

    public static Object getField(Object obj, Field field) {
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        long fieldOffset = unsafe.objectFieldOffset(field);
        return unsafe.getObject(obj, fieldOffset);
    }

    static void main(String[] args) {
        try {
            A a = new A();
            String finalFieldName = "finalFieldName";
            setFinalField(a, finalFieldName, "ps");
            System.out.println(getField(a, finalFieldName));
            System.out.println(a.getClass().getField(finalFieldName).get(a));
            PrintStream printStream = System.out;
            Objects.requireNonNull(a);
            printStream.println("finalFieldName");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static class A {
        public final String finalFieldName = "finalFieldName";

        public A() {
        }
    }

}
