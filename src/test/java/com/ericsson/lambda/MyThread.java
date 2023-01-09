package com.ericsson.lambda;
/**
 * 定义MyThread类模拟Thread类，区别：接口中方法有参数；原Runnable中的run方法无参数
 */
public class MyThread implements Flyable{
    private Flyable target;

    public MyThread(Flyable flyable){
        this.target = flyable;
    }

    public void start(String animalName){
        this.fly(animalName);
    }

    @Override
    public void fly(String animalName) {
        if (target != null) {
            target.fly(animalName);
        }
    }

}
