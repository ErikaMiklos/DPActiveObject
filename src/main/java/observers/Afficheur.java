package observers;

import observable.Capteur;
import proxy.Canal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Afficheur implements Observer {
    private List<Integer> afficheListe = new ArrayList<>();

    @Override
    public void update(Canal canal) throws ExecutionException, InterruptedException {
        afficheListe.add(canal.getValue().get());

        if(canal.getValue().get()==5){
            System.out.println("Afficheur id " + this.hashCode() + " : Liste des valeurs récupérées: " + afficheListe);
        }
    }
}

