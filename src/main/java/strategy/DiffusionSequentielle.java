package strategy;

import observable.CapteurImpl;
import proxy.Canal;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Il s'agit de l'agorithme de diffusion séquentielle qui permet
 * l'envoi de données identiques aux afficheurs
 * cependant, il ne récupère par toutes les données du capteur.
 * Pour ce faire, il utilise un système de semaphore bloquant l'écriture
 * tant que les afficheurs n'ont pas tous lus la donnée et utilise un lock pour les lecteurs.
 */
public class DiffusionSequentielle implements AlgoDiffusion {

    int nbDeCanaux =0;
    int tampon;
    boolean verrouLecture;
    private List<Canal> canals;
    private CapteurImpl capteur;

    @Override
    public void configure(CapteurImpl capteur) {
        this.capteur = capteur;
        this.canals = capteur.getCanals();
        this.verrouLecture = false;
    }

    /**
     * Méthode d'execution principale permettant l'écriture sur le capteur
     * et bloquant la lecture des autres valeurs du capteur tant que celle copié dans le tampon,
     * n'a pas été copié par tous les canaux.
     */
    @Override
    public void execute(){

        if(!verrouLecture){
            verrouLecture = true;
            this.tampon = capteur.getValue();
            for(Canal c: canals) {
                c.update(capteur);
                this.nbDeCanaux++;
            }
        }
    }

    /**
     * Méthode de décrémentation du nombre de canaux devant lire les valeurs du capteur.
     * Quand tous les canaux ont récupéré les données,
     * le nbDeCanaux tombe à zéro et la copie tampon peut-être réécrite.
     */
    public int lectureRealisee(){
        this.nbDeCanaux--;
        if (nbDeCanaux ==0){
            this.verrouLecture=false;
        }
        return this.tampon;
    }
}
