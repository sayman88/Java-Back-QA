package work5;

import org.junit.jupiter.api.*;
import retrofit2.Response;
import java.io.IOException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MiniMarketApiTest {

    private static MiniMarketApiService service;

    @BeforeAll
    static void beforeAll() {
        service = new MiniMarketApiService();
    }

    private static Long id;

    @Test
    @Order(1)
    void testGetCategory() throws IOException {
        MiniMarketCategoryResult category = service.getCategory(2);
//         Перед проведением сравнения необходимо обновить .json файл
//        assertJson(getResource("category.json"), category);
        System.out.println("Get categories");
    }
    @Test
    @Order(2)
    void testGetProducts()  {
        List<MiniMarketProductsResult> products = service.getProducts();
//          Перед проведением теста на сравнения необходимо обновить .json файл
//        assertJson(getResource("products.json"), products);
        System.out.println("Get products");
    }
    @Test
    @Order(3)
    void testCreateProducts() throws IOException {
        MiniMarketProductsResult product = new MiniMarketProductsResult();
        product.setId(null);
        product.setTitle("Bred");
        product.setPrice(100);
        product.setCategoryTitle("Food");
        Response<MiniMarketProductsResult> response = service.createProducts(product);
        MiniMarketProductsResult created = response.body();
        Assertions.assertNotNull(created);
        Assertions.assertEquals(product.getTitle(), created.getTitle());
        Assertions.assertEquals(product.getPrice(), created.getPrice());
        Assertions.assertEquals(product.getCategoryTitle(), created.getCategoryTitle());
        Assertions.assertEquals(201, response.code());
        id = created.getId();
        System.out.println("Create product ID : " + id);
    }
    @Test
    @Order(4)
    void testGetProductsId() throws IOException {
        MiniMarketProductsResult exProduct = new MiniMarketProductsResult();
        exProduct.setId(id);
        exProduct.setTitle("Bred");
        exProduct.setPrice(100);
        exProduct.setCategoryTitle("Food");
        MiniMarketProductsResult product = service.getProductsId(id);
        Assertions.assertEquals(exProduct.getTitle(), product.getTitle());
        Assertions.assertEquals(exProduct.getPrice(), product.getPrice());
        Assertions.assertEquals(exProduct.getCategoryTitle(), product.getCategoryTitle());
        Assertions.assertEquals(exProduct.getId(), product.getId());
        System.out.println("Get product after created by ID : " + id + " " + product.getTitle() + " " + product.getPrice());
    }
    @Test
    @Order(5)
    void testUpdateProducts() throws IOException {
        MiniMarketProductsResult product = new MiniMarketProductsResult();
        product.setId(id);
        product.setTitle("BredIsBeer");
        product.setPrice(101);
        product.setCategoryTitle("Food");
        Response<MiniMarketProductsResult> response = service.updateProducts(product);
        MiniMarketProductsResult update = response.body();
        Assertions.assertNotNull(update);
        Assertions.assertEquals(product.getTitle(), update.getTitle());
        Assertions.assertEquals(product.getPrice(), update.getPrice());
        Assertions.assertEquals(product.getCategoryTitle(), update.getCategoryTitle());
        Assertions.assertEquals(200, response.code());
        System.out.println("Update product by ID : " + id);

    }
    @Test
    @Order(6)
    void testGetProductsIdAfterUpdate() throws IOException {
        MiniMarketProductsResult product = service.getProductsId(id);
        System.out.println("Get product by Id after update ID : " + id + " " + product.getTitle() + " " + product.getPrice());
    }
    @Test
    @Order(7)
    void testDeleteProducts() throws IOException {
        Response<Void> remove = service.deleteProductsId(id);
        Assertions.assertEquals(200, remove.code());
        System.out.println("Delete product by ID : " + id);

    }
    @Test
    @Order(8)
    void testGetProductsIdAfterDelete() throws IOException {
        MiniMarketProductsResult product = service.getProductsId(id);
        System.out.println("Get product by Id after delete ID : " + id);
    }
}
