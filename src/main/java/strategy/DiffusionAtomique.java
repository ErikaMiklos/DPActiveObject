package strategy;

import observable.CapteurImpl;
import proxy.Canal;
import java.util.List;

/**
 * Il s'agit de l'agorithme de diffusion atomique qui permet
 * l'envoi de données identiques aux afficheurs
 * à chaque changement de valeur du capteur.
 * Pour ce faire, il utilise un système de semaphore bloquant l'ecriture
 * tant que les afficheurs n'ont pas tous lus la donnée.
 */
public class DiffusionAtomique implements AlgoDiffusion {

    int nbDeCanaux =0;
    private List<Canal> canals;
    private CapteurImpl capteur;
    @Override
    public void configure(CapteurImpl capteur) {
        this.capteur = capteur;
        this.canals = capteur.getCanals();
    }

    /**
     * Méthode d'execution principale blaquant l'écriture sur le capteur et faisant appel
     * à la méthode update de tous les canaux qui sont comptés.
     */
    @Override
    public void execute(){
        this.capteur.lock();
        for(Canal c: canals) {
            c.update(capteur);
            this.nbDeCanaux++;
        }


        /*
        if(canals.stream().map(Canal::getValue).allMatch(Future::isDone)) {
            this.capteur.unLock();
        }
        */
    }

    /**
     * Méthode de décrémentation du nombre de canaux devant lire les valeurs du capteur.
     * Quand tous les canaux ont récupérés les données,
     * le nbDeCanaux tombe à zéro et le capteur est dévérouillé.
     */
    public void lectureFaite(){
        this.nbDeCanaux--;
        if (nbDeCanaux ==0){
            this.capteur.unLock();
        }
    }
}
