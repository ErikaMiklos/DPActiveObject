package observers;

import observable.Capteur;

public class Afficheur1 implements Observer {
    @Override
    public void update(Capteur capteur) {
        System.out.println("Afficheur1: Capteur getValue: " + capteur.getValue());
    }
}
