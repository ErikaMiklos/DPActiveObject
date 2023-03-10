package observers;

import org.jetbrains.annotations.NotNull;
import proxy.Canal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class Afficheur implements Observer {
    private final List<Integer> afficheListe = new ArrayList<>();

    @Override
    public void update(@NotNull Canal canal) throws ExecutionException, InterruptedException {
        Future<Integer> futureValue = canal.getValue();
        Integer updatedValue = futureValue.get();
        System.out.println("afficheurid " + this.hashCode() + " a reçu la valeur: "+ updatedValue);
        afficheListe.add(updatedValue);

        if(updatedValue==5){
            //Logger.getGlobal().info("Réussi à récupérer la valeur actuelle");
            System.out.println("Afficheur id " + this.hashCode() + " : Liste des valeurs récupérées: " + afficheListe);
        }
    }
}

