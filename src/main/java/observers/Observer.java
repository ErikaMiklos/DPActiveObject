package observers;

import observable.Capteur;

public interface Observer {
    void update(Capteur capteur);
}
