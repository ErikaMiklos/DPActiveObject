package observable;

import observers.Observer;
import strategy.DiffusionAtomique;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

public class CapteurImpl extends Thread implements Capteur {
    private int value;
    private List<Observer> observers = new ArrayList<>();

    private DiffusionAtomique diffusionAtomique = new DiffusionAtomique();


    @Override
    public void attache(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detache(Observer observer) {
        observers.remove(observer);
    }

    public void update() {
        this.value = diffusionAtomique.getValue();
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
        diffusionAtomique.execute();
        //this.value++;
        update();
        System.out.println("CapteurImpl Current value: " + this.value);
    }
    @Override
    public void run() {
        try {
            for (int i=0; i<5; i++) {
                tick();
                Thread.sleep(300);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
