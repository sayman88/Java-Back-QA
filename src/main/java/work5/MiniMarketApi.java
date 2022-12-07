package work5;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface MiniMarketApi {

    @GET("categories/{id}")
    Call<MiniMarketCategoryResult> getCategory(@Path("id") Integer id);

    @GET("products/")
    Call<List<MiniMarketProductsResult>> getProducts();

    @GET("products/{id}")
    Call<MiniMarketProductsResult> getProductsId(@Path("id") Long id);

    @POST("products/")
    Call<MiniMarketProductsResult> createProducts(@Body MiniMarketProductsResult body);

    @PUT("products/")
    Call<MiniMarketProductsResult> updateProducts(@Body MiniMarketProductsResult body);

    @DELETE("products/{id}")
    Call<Void> deleteProductsId(@Path("id") Long id);
}
