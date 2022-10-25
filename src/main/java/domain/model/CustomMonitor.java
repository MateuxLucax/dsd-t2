package domain.model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CustomMonitor extends ReentrantLock implements Lockable {
    @Override
    public void release() {
        if (super.isHeldByCurrentThread()) {
            super.unlock();
        }
    }

    @Override
    public void acquire() {
        if (isHeldByCurrentThread())
            super.lock();
    }

    @Override
    public boolean tryAcquire(long timeout) {
        try {
            return super.tryLock(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            return false;
        }
    }
}
