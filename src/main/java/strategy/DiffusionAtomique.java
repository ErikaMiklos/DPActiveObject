package strategy;

import observable.CapteurImpl;
import proxy.Canal;
import java.util.List;
import java.util.concurrent.Future;

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
        /*if(canals.stream().map(Canal::getValue).allMatch(Future::isDone)) {
            this.capteur.unLock();
        }*/
        this.capteur.unLock();

    }
}
