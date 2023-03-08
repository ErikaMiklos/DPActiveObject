package async;

import observable.Capteur;

import java.util.concurrent.ExecutionException;

public interface ObserverAsync {
    void update(Capteur capteur);
}
