package strategy;

import observable.CapteurImpl;
import proxy.Canal;

import java.util.List;

/**
 * Il s'agit de l'agorithme de diffusion époque qui permet
 * l'envoi de données en controlant uniquement le fait que la donnée la plus récente est retenu par les afficheurs.
 */
public class DiffusionEpoque implements AlgoDiffusion {

    int nbDeCanaux =0;
    private List<Canal> canals;
    private CapteurImpl capteur;
    @Override
    public void configure(CapteurImpl capteur) {
        this.capteur = capteur;
        this.canals = capteur.getCanals();
    }

    /**
     * Méthode d'execution principale bloquant l'écriture sur le capteur et faisant appel
     * à la méthode update de tous les canaux qui sont comptés.
     */
    @Override
    public void execute(){
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
     * Quand tous les canaux ont récupéré les données,
     * le nbDeCanaux tombe à zéro et le capteur est dévérouiller.
     */
    public int lectureRealisee(){
        this.nbDeCanaux--;
        if (nbDeCanaux ==0){

        }
        return capteur.getValue();
    }
}
