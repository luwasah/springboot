package com.ericsson.lambda;

/**
 * 定义MyThread2类模拟Thread类，区别：接口中方法有参数、又返回值；原Runnable中的run方法无参数、无返回值
 */
public class MyThread2 implements Runnable{
    private Runnable target;

    public MyThread2(Runnable runnable){
        this.target = runnable;
    }

    public int start(String animalName){
        return this.run(animalName);
    };

    @Override
    public int run(String animalName) {
        if(target != null){
            return target.run(animalName);
        }
        return 0;
    }
}
