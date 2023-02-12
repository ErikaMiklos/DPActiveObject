package observable;

import observable.Capteur;
import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

public class CapteurImpl implements Capteur {
    private int value;
    private List<Observer> observers = new ArrayList<>();


    @Override
    public void attache(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detache(Observer observer) {
        observers.remove(observer);
    }

    public void update() {
        for(Observer o: observers){
            //Technique pop
            o.update(this);
        }
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void tick() {
        this.value++;
        update();
        System.out.println("CapteurImpl Current value: " + this.value);
    }


}
