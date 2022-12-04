package work4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;

public class ShoppingListSpecificationTest extends AbstractSpoonaccularTest {

    private static final String API_KEY = "df76289e26bd474e8d8a3c52bc2baf74";

    @BeforeAll
    @Disabled
    static void beforeAll() {

        RestAssured.baseURI = "https://api.spoonacular.com/mealplanner/";
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