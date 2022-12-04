package work4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;

public class RecipesSpecificationTest extends AbstractSpoonaccularTest {

    private static final String API_KEY = "8f065c41f2994b83a4951ea13cd8f9f3";

    @BeforeAll
    @Disabled
    static void beforeAll() {

        RestAssured.baseURI = "https://api.spoonacular.com/recipes/";
        //Выводит response log - без фильтра будет только request log
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }
}