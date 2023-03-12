package observable;

import observers.Afficheur;
import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;
import strategy.DiffusionSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *  La classe CapteurImpl joue le rôle de Lecture
 *  elle consumme une valeur incréménté transferé via une BlockingQueue
 *  d'une classe d'AlgoDiffusion.
 */
public class CapteurImpl implements Capteur {
    /**
     * attributs
     */
    private int value = 0;
    private final List<Observer> observers;
    private final AlgoDiffusion algo;
    private final BlockingQueue<Integer> queue;
    private final List<Canal> canals;
    private int compteur = 0;

    /**
     * Constructeur
     * Création la connection avec l'implémentation d'algoDiffusion
     * via une BlockingQueue
     * Crèation de 4 Canaux, qu'ils représentes des 4 proxy des observers (Afficheurs)
     * @param queue: BlockingQueue, côté récepteur
     * @param algo: implémentation d'AlgoDiffusion:
     *            DiffusionAtomique, DiffusionEpoque, DiffusionSequence
     */
    public CapteurImpl(BlockingQueue<Integer> queue, AlgoDiffusion algo)  {
        this.algo = algo;
        observers = new ArrayList<>();
        canals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Canal canal = new Canal(this);
            canals.add(canal);
        }
        this.queue = queue;
    }

    /**
     * Méthode pour récupérer des Canaux
     * @return : Liste des Canaux
     */
    public List<Canal> getCanals() {
        return canals;
    }

    /**
     * Methode pour attacher un afficheur
     * @param afficheur: observer de Capteur
     */
    @Override
    public void attache(Afficheur afficheur) {
        observers.add(afficheur);
    }

    /**
     * Methode pour détacher un afficheur
     * @param afficheur: observeur de Capteur
     */
    @Override
    public void detache(Afficheur afficheur) {
        observers.remove(afficheur);
    }
    /**
     * Fonction de récupération de la value actuelle du capteur.
     * Concernant la diffusion atomique et diffusion séquentielle la valeur
     * est lu de la BlockingQueue, qui est blocké et déblocké périodiquement.
     * Concernant la diffusion époque, elle récupère la valeur actuelle
     * de la capteur, ici il n'y a pas de blockage.
     * @return value
     */
    @Override
    public int getValue() throws InterruptedException {

        if(algo.getClass().equals(DiffusionAtomique.class) || algo.getClass().equals(DiffusionSequence.class)){
            compteur++;
            if (compteur == 4) {
                compteur = 0;
                // relâche le blockage
                return queue.take();
            } else {
                // non blockant
                return queue.peek();
            }
        } else {
            return value;
        }
    }
    /**
     * Il s'agit de la méthode d'incrémentation de value dans le capteur,
     * elle fonctionne si les valeurs sont inférieur à 6.
     * Ce méthode est blocké pendant que chaque afficheurs récupérent
     * la valeur actuelle.
     */
    @Override
    public void tick() throws InterruptedException, ExecutionException {
        if(value < 5) {
            ++value;
            algo.execute();
        }
    }
    /**
     * Il s'agit de la méthode d'incrémentation de value dans
     * l'implémentation d'algoDiffusion.
     */
    public void setValue() throws InterruptedException {
        algo.setValue();
    }


}
