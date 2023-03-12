package strategy;

import observable.CapteurImpl;
import proxy.Canal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * La classe DiffusionSequence joue le rôle d'Êcriture
 * elle produise une valeur incréménté via une BlockingQueue vers
 * la Lecture celle la classe CapteurImpl.
 */
public class DiffusionSequence implements AlgoDiffusion {
    /**
     * attributs
     */
    private CapteurImpl capteur;
    private BlockingQueue<Integer> copy;
    private Queue<Integer> originList;

    /**
     * Configure l'algo
     * @param queue: BlockingQueue avec une seul Case, elle d
     *             copie les valeurs de la originListe, celle qui est
     *             preremplie au moment de la configuration
     * @param capteur: Lecture des données produit de l'algo
     *
     */
    @Override
    public void configure(BlockingQueue<Integer> queue, CapteurImpl capteur){
        this.capteur = capteur;
        this.copy = queue;
        this.originList = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            originList.add(i);
        }
    }
    /**
     * Méthode d'execution principale bloquant [méthode put()]
     * l'écriture sur le capteur et faisant appel à la méthode
     * update de tous les canaux.
     */
    @Override
    public void execute() throws InterruptedException {
        //la valeur <<head>> d'OriginListe est copié dans le BlockingQueue
        //afin de le transferer au CapteurImpl
        if(originList.peek() != null){
            copy.put(originList.peek());
        }
        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
    }
    /**
     * Méthode qui permets d'incrémenter la valeur en la tirant
     * de Queue d'originliste.
     */
    @Override
    public void setValue(){
        if(!originList.isEmpty()) {
            originList.remove();
        }
    }

}
