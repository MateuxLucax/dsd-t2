package domain.model;

public interface Lockable {
    public void release();
    public void acquire();
    public boolean tryAcquire(long timeout);
}
