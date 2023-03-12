package observers;

import org.jetbrains.annotations.NotNull;
import proxy.Canal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Il s'agit de la classe Observer de Capteur. Récupère le canal mis à jour,
 * celui qui transfère la valeur actuel du Capteur via la méthode getValue().
 * La valeur arrive en tant que Future et en appellant le get() méthode
 * elle mets la Future en statut complété (isDone=true).
 */
public class Afficheur implements Observer {
    /**
     * attribut
     */
    private final List<Integer> afficheListe = new ArrayList<>();

    /**
     * Retour la liste des valeurs récuperés
     * @return
     */
    public List<Integer> getAfficheListe() {
        return afficheListe;
    }

    /**
     * Méthode de récupération des valeurs lu par le Capteur
     * @param canal: canal associé en êtat mise à jour
     */
    @Override
    public void update(@NotNull Canal canal) throws ExecutionException, InterruptedException {
        Future<Integer> futureValue = canal.getValue();
        Integer updatedValue = futureValue.get();

        if(afficheListe.isEmpty() || afficheListe.get(afficheListe.size()-1) < updatedValue) {
            afficheListe.add(updatedValue);
        }
    }
}

