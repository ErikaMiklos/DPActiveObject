package async;

import java.util.concurrent.Future;

public interface CapteurAsync extends Future {
    Future<Integer> getValue();
}
