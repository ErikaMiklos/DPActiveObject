package observable;

import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * La classe CapteurImpl represente un capteur qui receptionne des valeurs (ici une valeur incrémenté par un tick().
 */
public class CapteurImpl implements Capteur {
    private int value = 0;
    private List<Integer> values;
    private boolean isLocked = false;
    private final List<Observer> observers;
    private final AlgoDiffusion algo;
    private List<Canal> canals;

    public CapteurImpl(AlgoDiffusion algo)  {
        this.algo = algo;
        this.observers = new ArrayList<>();
        this.values = new ArrayList<>();
        canals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Canal canal = new Canal(this);
            canals.add(canal);
        }
    }

    public List<Canal> getCanals() {
        return canals;
    }

    /**
     * Methode pour attacher un afficheur
     * @param observer
     */
    @Override
    public void attache(Observer observer) {
        observers.add(observer);
    }

    /**
     * Methode pour détacher un afficheur
     * @param observer
     */
    @Override
    public void detache(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Fonction de récupération de la value qui fait appel
     * à la méthode lectureFaite de l'algo pour permettre
     * le dévérouillage de la méthode tick du capteur.
     * @return
     */
    @Override
    public int getValue(){
        algo.lectureFaite();
        return  this.value;
    }

    /**
     * Méthode de vérouillage de la méthode tick()
     */
    public void lock() {
        this.isLocked = true;
        //System.out.println("isLocked");
    }

    /**
     * Méthode de dévérouillage de la méthode tick()
     */
    public void unLock() {
        this.isLocked = false;
        //System.out.println("UnLocked");
    }

    /**
     * Il s'agit de la méthode d'incrémentation de value dans le capteur, elle fonctionne si les valeurs sont inférieur à 5.
     * Pendant la diffusion atomique, elle fonctionne si le lock n'est pas activé.
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public void tick() throws InterruptedException, ExecutionException {
        if (value==5){
            lock();
        }
        if (!isLocked ){
            this.value++;
            this.values.add(value);
            System.out.println("valeur capteurImpl: " + this.value);
            algo.execute();
        }
        else {
            System.out.println("tick is locked!!!");
        }
    }


}
