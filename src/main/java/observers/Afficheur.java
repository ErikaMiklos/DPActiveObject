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
    private int updatedValue;

    public List<Integer> getAfficheListe() {
        return afficheListe;
    }

    @Override
    public void update(@NotNull Canal canal) throws ExecutionException, InterruptedException {
        Future<Integer> futureValue = canal.getValue();
        updatedValue = futureValue.get();
        //System.out.println("afficheurid " + this.hashCode() + " a reçu la valeur: "+ updatedValue);
        if(updatedValue<6 && (afficheListe.isEmpty() || afficheListe.get(afficheListe.size()-1)<updatedValue)) {
            System.out.println("afficheurid " + this.hashCode() + " a ajouté sur son liste: "+ updatedValue);
            afficheListe.add(updatedValue);
        }

        if(updatedValue==5){
            //Logger.getGlobal().info("Réussi à récupérer la valeur actuelle");
            System.out.println("Afficheur id " + this.hashCode() + " : Liste des valeurs récupérées: " + afficheListe);
        }
    }
}

