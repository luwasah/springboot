package com.ericsson.lambda;

public class MyThread3 implements Flyable3{
    private Flyable3 target;

    public MyThread3(Flyable3 flyable){
        this.target = flyable;
    }

    public void start(String animalName, int height){
        this.fly(animalName, height);
    }

    @Override
    public void fly(String animalName, int height) {
        if (target != null) {
            target.fly(animalName, height);
        }
    }

}
