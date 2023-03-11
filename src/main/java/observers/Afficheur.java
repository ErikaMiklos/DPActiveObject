package observers;

import org.jetbrains.annotations.NotNull;
import proxy.Canal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Afficheur implements Observer {
    private final List<Integer> afficheListe = new ArrayList<>();

    public List<Integer> getAfficheListe() {
        return afficheListe;
    }

    @Override
    public void update(@NotNull Canal canal) throws ExecutionException, InterruptedException {
        Future<Integer> futureValue = canal.getValue();
        Integer updatedValue = futureValue.get();

        if(updatedValue<6 && (afficheListe.isEmpty() || afficheListe.get(afficheListe.size()-1)<updatedValue)) {
            afficheListe.add(updatedValue);
        }
    }
}

