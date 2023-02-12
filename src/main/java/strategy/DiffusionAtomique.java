package strategy;

import observable.Capteur;
import observable.CapteurImpl;

public class DiffusionAtomique extends Thread implements AlgoDiffusion {
    private Capteur capteur;

    public DiffusionAtomique(Capteur capteur) {
        this.capteur = capteur;
    }

    @Override
    public void configure() {

    }

    @Override
    public void execute() {
        capteur.tick();
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<5; i++) {
                execute();
                Thread.sleep(300);
                System.out.println("execute");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
