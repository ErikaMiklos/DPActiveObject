import observable.Capteur;
import observable.CapteurImpl;
import observers.Afficheur1;
import observers.Observer;

public class TestMain {
    public static void main(String[] args) {
        CapteurImpl capteur = new CapteurImpl();
        Observer observer1 = new Afficheur1();
        capteur.attache(observer1);
        capteur.run();
    }
}
