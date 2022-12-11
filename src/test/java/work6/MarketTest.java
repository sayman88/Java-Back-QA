package work6;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MarketTest {

    private static Products getProductFromDbById(Long id) throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));

        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            return productsMapper.selectByPrimaryKey(id);
        }
    }

    private static MarketService service;

    private static Products productFromDb;
    @BeforeAll
    static void beforeAll() {
        service = new MarketService();
    }

    private static Long id;

    @Test
    @Order(1)
    @DisplayName("CreateAndCheckProductInDb")
    void testCreateAndCheckProductInDb() throws Exception {
        Product product = new Product();
        product.setTitle("Bred");
        product.setPrice(100);
        product.setCategoryTitle("Food");
        Product createProduct = service.createProduct(product);
        Assertions.assertNotNull(createProduct);

        id = createProduct.getId();

        productFromDb = getProductFromDbById(id);

        Assertions.assertEquals(createProduct.getId(), productFromDb.getId());
        Assertions.assertEquals(createProduct.getTitle(), productFromDb.getTitle());
        Assertions.assertEquals(createProduct.getPrice(), productFromDb.getPrice());
        System.out.println("Создан новый продукт :  [ " + "ID : " + createProduct.getId() + " Title : " + createProduct.getTitle() + "]" + "\n"
                + "Подтверждение из БД  :  [ " + "ID : " + productFromDb.getId() + " Title : " + productFromDb.getTitle() + "]");

    }

    @Test
    @Order(2)
    @DisplayName("UpdateAndCheckProductInDb")
    void testUpdateAndCheckProductInDb() throws IOException {
        Product product = new Product();
        product.setId(id);
        product.setTitle("BredIsBeer");
        product.setPrice(111);
        product.setCategoryTitle("Food");
        Product updateProduct = service.updateProduct(product);

        productFromDb = getProductFromDbById(id);

        Assertions.assertNotNull(updateProduct);
        Assertions.assertEquals(updateProduct.getId(), productFromDb.getId());
        Assertions.assertEquals(updateProduct.getTitle(), productFromDb.getTitle());
        Assertions.assertEquals(updateProduct.getPrice(), productFromDb.getPrice());
        System.out.println("Внесены  изменения  в  продукт :  [ " + "ID : " + updateProduct.getId() + " Title : " + updateProduct.getTitle() + " Price : " + updateProduct.getPrice() + "]" + "\n"
                + "Подтверждение изменений из БД  :  [ " + "ID : " + productFromDb.getId() + " Title : " + productFromDb.getTitle() + " Price : " + productFromDb.getPrice() + "]");


    }

    @Test
    @Order(3)
    @DisplayName("DeleteAndCheckProductInDb")
    void testDeleteAndCheckProductInDb() throws IOException {
        Response<Void> deleteProduct = service.deleteProduct(id);

        Assertions.assertEquals(200, deleteProduct.code());
        System.out.println("Удален продукт с ID : " + id);
    }

    @Test
    @Order(4)
    @DisplayName("GetProductByIdAndCheckAfterDeletedProductFromDb")
    void testGetProductByIdAndCheckAfterDeletedProductFromDb() throws IOException {
        Response<Product> response = service.getProductById(id);
        Assertions.assertEquals(404, response.code());
        System.out.println("Не найден продукт с ID : " + id);

    }
}