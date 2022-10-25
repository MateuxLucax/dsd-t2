package domain.model.parallel;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CustomSemaphone extends Semaphore implements Lockable {
    public CustomSemaphone() {
        super(1);
    }

    @Override
    public boolean tryAcquire(long timeout) {
        try {
            return super.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            return false;
        }
    }

    @Override
    public void acquire() {
        try {
            super.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Something went wrong!");
        }
    }

    @Override
    public void release() {
        super.release();
    }
}
