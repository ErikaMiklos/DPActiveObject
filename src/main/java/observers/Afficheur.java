package observers;

import observable.Capteur;

import java.util.ArrayList;
import java.util.List;

public class Afficheur implements Observer {

    private int id;
    private List<Integer> afficheListe = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void update(Capteur capteur) {
        afficheListe.add(capteur.getValue());

        if(capteur.getValue()==5){
            System.out.println("Afficheur" + id + ": Liste des valeurs récupérées: " + afficheListe);
        }
    }
}

