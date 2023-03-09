package strategy;

import observable.CapteurImpl;
import proxy.Canal;
import java.util.List;

public class DiffusionAtomique implements AlgoDiffusion {

    private List<Canal> canals;
    private CapteurImpl capteur;
    @Override
    public void configure(List<Canal> canals, CapteurImpl capteur) {
        this.capteur = capteur;
        this.canals = canals;
    }

    @Override
    public void execute(){
        this.capteur.lock();
        for(Canal c: canals) {
            c.update(capteur);
        }
        this.capteur.unLock();
    }
}
