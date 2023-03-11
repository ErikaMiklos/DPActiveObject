package observable;

import observers.Afficheur;
import observers.Observer;

import java.util.concurrent.ExecutionException;

public interface Capteur {
    void attache(Afficheur afficheur);

    void detache(Afficheur afficheur);

    int getValue() throws ExecutionException, InterruptedException;

    void tick() throws InterruptedException, ExecutionException;
}
