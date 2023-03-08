package async;

import observable.Capteur;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface ObserverAsync {
    Future<?> update(Capteur capteur);
}
