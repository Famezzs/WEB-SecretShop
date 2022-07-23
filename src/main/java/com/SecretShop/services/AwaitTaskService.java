package com.SecretShop.services;

public class AwaitTaskService {
    public void execute(Thread thread) {
        thread.start();

        while (thread.isAlive()) {

        }
    }
}
