package work5;

import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class CallExecutor {

    private CallExecutor() {}
    @Nullable
    public static <T> T executeCall(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
