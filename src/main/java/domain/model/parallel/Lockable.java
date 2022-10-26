package domain.model.parallel;

public interface Lockable {
    void release();
    void acquire();
    boolean tryAcquire(long timeout);
    boolean isLocked();
}
