package com.laxian.asmdemo.ui.main;

public class Java8 {
    public Java8() {
    }

    public static void main(String[] args) {
        new Thread(() -> System.out.println("test")).start();
    }
}