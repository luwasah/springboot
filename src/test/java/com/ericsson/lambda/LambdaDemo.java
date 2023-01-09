package com.ericsson.lambda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * lambda函数式编程
 * 函数式编程思想概述:函数式思想则尽量忽略面向对象的复杂语法：“强调做什么，而不是以什么形式去做”;Lambda表达式就是函数式思想的体现
 *  1.Lambda表达式的使用前提:
 *      有一个接口;
 *      接口中有且仅有一个抽象方法
 *  2. Lambda表达式的三要素
 *      形式参数(形式参数肯定是针对方法的，所以此处的形势参数就是传递给接口中方法的)，箭头，代码块
 *  3. Lambda表达式的注意事项
 *      使用Lambda必须要有接口，并且要求接口中有且仅有一个抽象方法
 *      必须有上下文环境，才能推导出Lambda对应的接口
 *          根据局部变量的赋值得知Lambda对应的接口：Runnable r = () -> System.out.println("Lambda表达式");
 *          根据调用方法的参数得知Lambda对应的接口：new Thread(() -> System.out.println("Lambda表达式")).start();
 *  4. Lambda表达式和匿名内部类的区别：
 *      所需类型不同
 *          匿名内部类：可以是接口，也可以是抽象类，还可以是具体类
 *          Lambda表达式：只能是接口
 *      使用限制不同
 *          如果接口中有且仅有一个抽象方法，可以使用Lambda表达式，也可以使用匿名内部类
 *          如果接口中多于一个抽象方法，只能使用匿名内部类，而不能使用Lambda表达式
 *      实现原理不同
 *          匿名内部类：编译之后，产生一个单独的.class字节码文件
 *          Lambda表达式：编译之后，没有一个单独的.class字节码文件。对应的字节码会在运行的时候动态生成
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaDemo {

    /**
     * 无参、无返回值
     */
    @Test
    public void testLambda1(){
        new Thread(() -> {
            System.out.println("runnable task is running....");
        }).start();

    }

    /**
     * 有参、无返回值
     */
    @Test
    public void testLambda2(){
        new MyThread((String animalName) -> {//(String s)表示接口中fly的形式参数列表
            System.out.println(animalName + "正在欢快的飞....");//{}方法体表示对fly的重写和实现
        }).start("拉布拉多");
    }

    /**
     * 有参、有返回值
     */
    @Test
    public void testLambda3(){
        int result = new MyThread2((String animalName) -> {
            System.out.println(animalName + " 在欢快的跑......");
            int distance = 100;//假装跑了这么远
            return distance;
        }).start("小伯恩山");
        System.out.println("跑了"+ result + "m");
    }

    /**
     * Lambda表达式的省略模式,省略的规则:
     *  参数类型可以省略。但是有多个参数的情况下，不能只省略一个, 即所有参数都要省略参数类型
     *  如果参数有且仅有一个，那么小括号可以省略
     *  如果代码块的语句只有一条，可以省略大括号和分号，和return关键字
     */
    @Test
    public void testLambda4(){
        //只有一个参数时，参数类型可以省略
        new MyThread(
                (animalName) -> {
                    System.out.println(animalName + " is running...");
                }
        ).start("拉布拉多");

        //多个参数的情况下，不能只省略一个;即所有参数都要省略参数类型
        //主意：多个参数时，是参数类型可以省略，括号是不能省略的！参数只有一个时才能省略掉括号
        new MyThread3(
                (animalName, height) -> {
                    System.out.println(animalName + " is flying on " + height );
                }
        ).start("雄鹰", 10000);

        //如果参数有且仅有一个，那么小括号可以省略
        new MyThread(
                animalName -> {
                    System.out.println(animalName + " is running...");
                }
        ).start("大金毛");

        //如果代码块的语句只有一条，可以省略大括号和分号，和return关键字
        int result = new MyThread4(
                (a, b) -> a + b
        ).start(10, 20);
        System.out.println("result=" + result);
    }

}
