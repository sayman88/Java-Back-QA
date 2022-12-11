package work6;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));

        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            Products product = productsMapper.selectByPrimaryKey(444L);
            System.out.println("Select: " + product);

            ProductsExample example = new ProductsExample();
            example.createCriteria()
                    .andTitleLike("Banana")
                    .andPriceGreaterThan(10);

            List<Products> products = productsMapper.selectByExample(example);
            System.out.println("Select by example 1: " + products);

            example.clear();
            example.createCriteria()
                    .andCategoryIdEqualTo(2L)
                    .andPriceGreaterThan(22900);
            products = productsMapper.selectByExample(example);
            System.out.println("Select by example 2: " + products);

            productsMapper.deleteByPrimaryKey(444L);
            example.clear();

            example.createCriteria()
                    .andTitleLike("Banana")
                    .andPriceGreaterThan(10);

            products = productsMapper.selectByExample(example);
            System.out.println("Select by example 3: " + products);

        }
    }
}