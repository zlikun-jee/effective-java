package com.zlikun.jee.j006;

import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 反射API测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 10:11
 */
public class ReflectiveTest {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({TYPE})
    @interface MyAnnotation {
        String value() default "Hello !";
    }

    @MyAnnotation("我是一个内部类!")
    static class MyType {

        private String name;

        static {
            System.out.println("静态代码块");
        }

        {
            System.out.println("非静态代码块");
        }

        public MyType() {
            System.out.println("无参构造方法");
        }
    }

    @Test
    public void clazz() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class<MyType> clazz = MyType.class;

        // 使用 Class.forName() 加载类的时候，只会执行静态代码块（不会构造类实例）
        // 实际上使用 Class.forName() 加载类返回类实例等价于直接使用类.class返回类实例，区别在于后者不会执行静态代码块
        assertEquals(clazz, Class.forName(clazz.getName()));

        // 返回类全名(在使用内部类、匿名类时，中间使用$分隔，如果是外部类则是.，与getCanonicalName方法一致)
        assertEquals("com.zlikun.jee.j006.ReflectiveTest$MyType", clazz.getName());
        // 返回类标准名称
        assertEquals("com.zlikun.jee.j006.ReflectiveTest.MyType", clazz.getCanonicalName());
        // 返回类名（简称，只包含类名，不包含包名）
        assertEquals("MyType", clazz.getSimpleName());

        // 使用无参构造方法构造一个实例（如果要使用参数构造实例，需要使用Constructor实例来实现）
        // 构造实例时会执行非静态代码块和无参构造方法
        MyType mt = clazz.newInstance();
        assertNull(mt.name);

    }

    @Test
    public void annotations() {
        Class<MyType> clazz = MyType.class;

        // 获取类注解列表
        Annotation[] annotations = clazz.getAnnotations();

        // @com.zlikun.jee.j006.ReflectiveTest$MyAnnotation(value=我是一个内部类!)
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        // 获取指定类注解
        MyAnnotation annotation = clazz.getAnnotation(MyAnnotation.class);
        // @com.zlikun.jee.j006.ReflectiveTest$MyAnnotation(value=我是一个内部类!)
        System.out.println(annotation);

        // interface com.zlikun.jee.j006.ReflectiveTest$MyAnnotation
        System.out.println(annotation.annotationType());
        // @com.zlikun.jee.j006.ReflectiveTest$MyAnnotation(value=我是一个内部类!)
        System.out.println(annotation.toString());
        // class com.zlikun.jee.j006.$Proxy4
        System.out.println(annotation.getClass());

        // 获取该注解属性值
        // 我是一个内部类!
        System.out.println(annotation.value());

    }

    @Test
    public void classLoader() {
        Class<MyType> clazz = MyType.class;

        // 返回加该类的ClassLoader
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(clazz.getClassLoader());

        // 其与当前测试类使用了同一个ClassLoader
        assertEquals(this.getClass().getClassLoader(), clazz.getClassLoader());
    }

    @Test
    public void package0() {
        Class<MyType> clazz = MyType.class;
        Package package0 = clazz.getPackage();
        // package com.zlikun.jee.j006
        System.out.println(package0);
    }

    @Test
    public void constructors() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Employee> clazz = Employee.class;

        System.out.println("--getConstructors--");
        // 返回构造方法列表，只返回当前类的public构造方法，不包含私有构造方法和其父类的构造方法
        Constructor[] constructors = clazz.getConstructors();
        // public com.zlikun.jee.j006.Employee(java.lang.String,java.lang.Long)
        for (Constructor<Employee> constructor : constructors) {
            System.out.println(constructor);
        }

        System.out.println("--getDeclaredConstructors--");
        // 返回当前类定义的全部构造方法（包含private和protected及包级访问权限的），不包含其父类的构造方法
        constructors = clazz.getDeclaredConstructors();
        // public com.zlikun.jee.j006.Employee(java.lang.String,java.lang.Long)
        // protected com.zlikun.jee.j006.Employee()
        // private com.zlikun.jee.j006.Employee(java.lang.Long)
        for (Constructor<Employee> constructor : constructors) {
            System.out.println(constructor);
        }

        // 如果要获取父类构造方法，需要先获取父类的类对象
        // class com.zlikun.jee.j006.Person
        Class<? super Employee> superClazz = clazz.getSuperclass();
        System.out.println(superClazz);
        // 获取父类的构造方法
        System.out.println("--superClazz.getDeclaredConstructors--");
        // public com.zlikun.jee.j006.Person()
        // public com.zlikun.jee.j006.Person(java.lang.String)
        for (Constructor<?> constructor : superClazz.getDeclaredConstructors()) {
            System.out.println(constructor);
        }
        System.out.println("--end--");

        // 根据参数类型列表返回构造方法，无参构造方法不用传入类型参数
        Constructor<Employee> constructor = clazz.getConstructor(String.class, Long.class);
        // public com.zlikun.jee.j006.Employee(java.lang.String,java.lang.Long)
        System.out.println(constructor);

        // 构造一个实例
        Employee employee = constructor.newInstance("zlikun", 350000L);
        // zlikun salary is 350000 fen
        System.out.printf("%s salary is %d fen%n", employee.getName(), employee.getSalary());

        // 能否使用private构造方法构造一个类实例
        // 首先使用getDeclaredConstructor方法才能得到这个实例
        constructor = clazz.getDeclaredConstructor(Long.class);
        // 其次修改其访问权限为true，才能使用该构造方法对象构造实例，这一点对属性、方法也适用
        constructor.setAccessible(true);
        employee = constructor.newInstance(240000L);
        assertEquals(Long.valueOf(240000L), employee.getSalary());
    }

    @Test
    public void fields() throws NoSuchFieldException, IllegalAccessException {
        Employee employee = new Employee("Ashe", 180000L);
        Class<? extends Employee> clazz = employee.getClass();

        // 同构造方法类似，只能取得仅有属性，且不包含父类属性
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        // 取得所有属性，但不包含父类属性
        fields = clazz.getDeclaredFields();
        // private java.lang.Long com.zlikun.jee.j006.Employee.salary
        for (Field field : fields) {
            System.out.println(field);
        }

        // 获取单个属性
        Field field = clazz.getDeclaredField("salary");
        assertEquals("salary", field.getName());
        assertEquals("private java.lang.Long com.zlikun.jee.j006.Employee.salary", field.toString());
        assertEquals("private java.lang.Long com.zlikun.jee.j006.Employee.salary", field.toGenericString());

        // 获取字段值(字段是私有的，所以需要设置访问权限)
        field.setAccessible(true);
        assertEquals(Long.valueOf(180000L), field.get(employee));

    }

    @Test
    public void methods() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Employee employee = new Employee("Ashe", 180000L);
        Class<? extends Employee> clazz = employee.getClass();

        // 与构造方法、属性不同，getMethods返回了所有public方法，包含从父类继承的（当然也包含Object类的）
        /* ----------------------------------------------------------------------------------------------
            public java.lang.Long com.zlikun.jee.j006.Employee.raise(long)
            public void com.zlikun.jee.j006.Employee.setSalary(java.lang.Long)
            public java.lang.Long com.zlikun.jee.j006.Employee.getSalary()
            public java.lang.String com.zlikun.jee.j006.Person.getName()
            public void com.zlikun.jee.j006.Person.setName(java.lang.String)
            public final void java.lang.Object.wait() throws java.lang.InterruptedException
            public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
            public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
            public boolean java.lang.Object.equals(java.lang.Object)
            public java.lang.String java.lang.Object.toString()
            public native int java.lang.Object.hashCode()
            public final native java.lang.Class java.lang.Object.getClass()
            public final native void java.lang.Object.notify()
            public final native void java.lang.Object.notifyAll()
        ---------------------------------------------------------------------------------------------- */
        System.out.println("--getMethods--");
        for (Method method : clazz.getMethods()) {
            System.out.println(method);
        }

        // getDeclaredMethods则仍只获取了当前类的方法（与访问范围无关）
        /* ----------------------------------------------------------------------------------------------
            public java.lang.Long com.zlikun.jee.j006.Employee.raise(long)
            public java.lang.Long com.zlikun.jee.j006.Employee.getSalary()
            public void com.zlikun.jee.j006.Employee.setSalary(java.lang.Long)
            private void com.zlikun.jee.j006.Employee.tip(long)
        ---------------------------------------------------------------------------------------------- */
        System.out.println("--getDeclaredMethods--");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method);
        }

        System.out.println("--end--");

        // 获取父类里的单个方法
        Method method = clazz.getMethod("setName", String.class);
        // 执行这个方法
        method.invoke(employee, "Peter");
        // 查找执行效果
        assertEquals("Peter", employee.getName());

        // 获取当前类里的私有方法
        method = clazz.getDeclaredMethod("tip", long.class);
        method.setAccessible(true);
        // Peter偷偷跟客户要了小费（反正我是私有的，老板看不到^_^）。
        Object r = method.invoke(employee, 1200L);
        // 1200
        System.out.println(r);

    }

}
