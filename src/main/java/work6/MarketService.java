package work6;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;


import static work5.CallExecutor.executeCall;



public class MarketService {

    private final MarketApi api;

    public MarketService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(System.out::println);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        api = new Retrofit.Builder()
                .baseUrl("https://minimarket1.herokuapp.com/market/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarketApi.class);
    }

    public List<Product> getProducts() {

        return executeCall(api.getProducts());
    }

    public Response<Product> getProductById(Long id) throws IOException {
        Call<Product> call = api.getProductById(id);
        return call.execute();
    }

    public Product createProduct(Product product) {

        return executeCall(api.createProduct(product));
    }

    public Product updateProduct(Product product) {
        return executeCall(api.updateProduct(product));
    }
    public Response<Void> deleteProduct(Long id) throws IOException {
        Call<Void> call = api.deleteProduct(id);
        return call.execute();
    }
}