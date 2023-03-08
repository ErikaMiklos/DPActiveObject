package observers;

import observable.Capteur;
import proxy.Canal;

import java.util.concurrent.ExecutionException;

public interface Observer {
    void update(Canal canal) throws ExecutionException, InterruptedException ;
}
