package observers;

import observable.Capteur;

import java.util.concurrent.ExecutionException;

public interface Observer {
    void update(Capteur capteur) throws ExecutionException, InterruptedException;
}
