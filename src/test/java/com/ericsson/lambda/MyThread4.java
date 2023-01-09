package com.ericsson.lambda;

public class MyThread4 implements Addable{
    private Addable target;

    public MyThread4(Addable addable){//构造函数
        this.target = addable;
    }

    public int start(int a, int b){
        return this.add(a, b);
    }

    @Override
    public int add(int a, int b) {
        int result = 0;
        if (target != null) {
            result = target.add(a, b);
        }
        return result;
    }

}
