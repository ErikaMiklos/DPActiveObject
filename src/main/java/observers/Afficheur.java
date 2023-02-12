package observers;

import observable.Capteur;

public class Afficheur implements Observer {

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void update(Capteur capteur) {
        System.out.println("Afficheur" + id + ": Capteur getValue: " + capteur.getValue());
    }
}

