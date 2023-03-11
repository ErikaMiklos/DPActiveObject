package strategy;

import observable.CapteurImpl;
import proxy.Canal;
import java.util.concurrent.BlockingQueue;

/**
 * La classe DiffusionAtomique joue le rôle d'Êcriture
 * elle produise une valeur incréménté via une BlockingQueue vers
 * la Lecture celle la classe CapteurImpl.
 */
public class DiffusionAtomique implements AlgoDiffusion {
    /**
     * attributs
     */
    private CapteurImpl capteur;
    private BlockingQueue<Integer> queue;
    private int value = 0;

    /**
     * Configure l'algo
     * @param queue: BlockingQueue avec une seul Case
     * @param capteur: Lecture des données produit de l'algo
     */
    @Override
    public void configure(BlockingQueue<Integer> queue, CapteurImpl capteur) {
        this.capteur = capteur;
        this.queue = queue;
    }
    /**
     * Méthode d'execution principale bloquant l'écriture sur le capteur et faisant appel
     * à la méthode update de tous les canaux.
     */
    @Override
    public void execute() throws InterruptedException {

        queue.put(value);
        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
    }

    /**
     * Méthode pour incrémenter la valeur de maniére non-blockant.
     */
    @Override
    public void setValue(){
         ++value;
    }
}
