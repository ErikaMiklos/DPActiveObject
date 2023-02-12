package observable;

import observers.Observer;
import proxy.Canal;

public interface Capteur {
    void attache(Observer observer);
    void detache(Observer observer);
    int getValue();
    void tick();
}
