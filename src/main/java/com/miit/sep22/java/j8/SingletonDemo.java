package com.miit.sep22.java.j8;

public class SingletonDemo {

    public static SingletonDemo singletonDemo;

    SingletonDemo() {

    }

    public static SingletonDemo getSingletonDemo() {
        if(singletonDemo == null) {
            synchronized (SingletonDemo.class) {
                if(singletonDemo == null) {
                    singletonDemo = new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }
}

@FunctionalInterface
interface TestInterface1
{


    void show();

    // default method
    default void show1()
    {
        System.out.println("Default TestInterface1");
    }

    default void show2()
    {
        System.out.println("Default TestInterface1");
    }

    static void show3()
    {
        System.out.println("Default TestInterface1");
    }

    static void show4()
    {
        System.out.println("Default TestInterface1");
    }
}

// if class is serializable then
