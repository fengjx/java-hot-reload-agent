package com.fengjx.reload.test;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fengjianxin
 */
public class HotReloadTest {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static volatile boolean running = true;

    public static void main(String[] args) {
        executor.submit(() -> {
            while (running) {
                new Test().t();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("input enter key to exit");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("exit");
        running = false;
        executor.shutdown();
    }

}
