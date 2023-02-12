package observers;

import observable.Capteur;

public class Afficheur implements Observer {
    @Override
    public void update(Capteur capteur) {
        System.out.println("Afficheur: Capteur getValue: " + capteur.getValue());
    }
}
