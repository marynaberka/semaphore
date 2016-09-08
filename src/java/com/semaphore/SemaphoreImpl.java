package com.semaphore;

//При помощи методов wait(), notify(), notifyAll() реализовать семафор.

public class SemaphoreImpl implements Semaphore {

    private volatile int availablePermits = 0;

    public SemaphoreImpl(int availablePermits) {
        if (availablePermits < 0) {
            throw new IllegalArgumentException("Available permits can't be < 0");
        }
        this.availablePermits = availablePermits;
    }

    // Запрашивает разрешение. Если есть свободное, захватывает его.
    // Если нет - приостанавливает поток до тех пор, пока не появится свободное разрешение.
    @Override
    public synchronized void acquire() {
        /*if (availablePermits > 0) {
            availablePermits--;
        } else {*/
        while (availablePermits <= 0) {
            try {
                this.wait();
                System.out.println("----Thread waiting inside acquire() method.----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        availablePermits--;
    }

    // Запрашивает переданое количество разрешений.
    // Если есть переданое количество свободных разрешений, захватывает их.
    // Если нет - приостанавливает поток до тех пор, пока не появится переданое колтчество
    // свободных разрешений.
    @Override
    public synchronized void acquire(int permits) {
        while (permits > availablePermits || availablePermits <= 0) {
            try {
                this.wait();
                System.out.println("----Thread waiting inside acquire(int permits) method.----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < permits; i++) {
            availablePermits--;
        }
    }

    // Отпускает разрешение, возвращая его семафору.
    @Override
    public synchronized void release() {
        availablePermits++;
        this.notify();
    }

    // Отпускает переданое количество разрешений, возварщая их семафору.
    @Override
    public synchronized void release(int permits) {
        for (int i = 0; i < permits; i++) {
            availablePermits++;
        }
        this.notifyAll();
    }


    // Возвращает количество свободных разрешений на данный момент.
    @Override
    public int getAvailablePermits() {
        return availablePermits;
    }
}
