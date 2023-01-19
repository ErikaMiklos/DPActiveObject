package observable;

import observable.Capteur;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

public class CapteurImpl extends Thread implements Capteur {
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

    public void notifyObservers() {
        for(Observer o:observers){
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
        notifyObservers();
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<5; i++) {
                tick();
                Thread.sleep(300);
                System.out.println("CapteurImpl: value actuel: " + this.value);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
