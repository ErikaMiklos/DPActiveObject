package observable;

import observers.Observer;

public interface Capteur {
    void attache(Observer observer);
    void detache(Observer observer);
    int getValue();
    void tick();
}
