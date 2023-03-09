package observable;

import observers.Observer;

import java.util.concurrent.ExecutionException;

public interface Capteur {
    void attache(Observer observer);

    void detache(Observer observer);

    int getValue() throws ExecutionException, InterruptedException;

    void tick() throws InterruptedException, ExecutionException;

    void lock();

    void unLock();

    boolean isLocked();
}
