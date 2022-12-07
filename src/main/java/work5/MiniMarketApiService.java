package work5;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

import static work5.CallExecutor.executeCall;

public class MiniMarketApiService {

    private final MiniMarketApi api;
    public MiniMarketApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(System.out::println);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        api = new Retrofit.Builder()
                .baseUrl("https://minimarket1.herokuapp.com/market/api/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MiniMarketApi.class);
    }

    public MiniMarketCategoryResult getCategory(Integer id) {
        return executeCall(api.getCategory(id));
    }

    public List<MiniMarketProductsResult> getProducts() {
        Call<List<MiniMarketProductsResult>> call = api.getProducts();
        return executeCall(call);
    }

    public Response<MiniMarketProductsResult> createProducts(MiniMarketProductsResult id) throws IOException {
        Call <MiniMarketProductsResult> call = api.createProducts(id);
        return call.execute();
    }

    public Response<MiniMarketProductsResult> updateProducts(MiniMarketProductsResult body) throws IOException {
        Call <MiniMarketProductsResult> call = api.updateProducts(body);
        return call.execute();
    }

    public MiniMarketProductsResult getProductsId(Long id) {
        Call<MiniMarketProductsResult> call = api.getProductsId(id);
        return executeCall(call);
    }

    public Response<Void> deleteProductsId(Long id) throws IOException {
        Call<Void> call = api.deleteProductsId(id);
        return call.execute();
    }
}

