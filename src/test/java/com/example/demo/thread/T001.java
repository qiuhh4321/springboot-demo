package com.example.demo.thread;

public class T001 implements Runnable {
    private int count = 10;

    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "count=" + count);
    }

    public static void main(String[] args) {
        T001 t = new T001();
        for (int i = 0; i < 5; i++) {
            new Thread(t, "Thread" + i).start();
        }
    }
}
