package com.semaphore;

import java.util.concurrent.CountDownLatch;

public class SemaphoreMain {

    public static void main(String[] args) {

        SemaphoreImpl semaphore = new SemaphoreImpl(3);

        System.out.println("Permits: " + semaphore.getAvailablePermits());

        Thread thread1 = new Thread(() -> {
            System.out.println("Invocation of thread1.");

            semaphore.acquire();
            System.out.println("Thread1 acquires 1(one) permit. Permits: " + semaphore.getAvailablePermits());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
            System.out.println("Thread1 releases 1(one) permit. Permits: " + semaphore.getAvailablePermits());
            System.out.println("End of thread1.");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Invocation of thread2.");

            System.out.println("Thread2 TRIES TO acquire 2(two) permits. Permits: " + semaphore.getAvailablePermits());
            semaphore.acquire(2);
            System.out.println("Thread2 acquires 2(two) permits. Permits: " + semaphore.getAvailablePermits());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release(1);
            System.out.println("Thread2 releases 1-st permit. Permits: " + semaphore.getAvailablePermits());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release(1);
            System.out.println("Thread2 releases 2-nd permit. Permits: " + semaphore.getAvailablePermits());
            System.out.println("End of thread2.");
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("Invocation of thread3.");

            semaphore.acquire();
            System.out.println("Thread3 acquires 1(one) permit. Permits: " + semaphore.getAvailablePermits());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
            System.out.println("Thread3 releases 1(one) permit. Permits: " + semaphore.getAvailablePermits());
            System.out.println("End of thread3.");

        });

        thread1.start();
        thread2.start();
        thread3.start();

    }
}

