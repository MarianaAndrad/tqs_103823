import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AppTest {

    private static final String URL = "https://jsonplaceholder.typicode.com/todos";

    @Test
    void testListAllToDos() {
        given()
                .when()
                .get(URL)
                .then()
                .statusCode(200);

    }

    @Test
    void when_getSpecificQuery_thenCheckTitle() {
        given()
                .when()
                .get(URL + "/4")
                .then()
                .statusCode(200)
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    void when_listAll_getSpecificID_thenCheck198() {
        given()
                .when()
                .get(URL)
                .then()
                .statusCode(200)
                .body("id", hasItems(198, 199));
    }


    @Test
    void when_listAll_getTime_Test() {
        given()
                .when()
                .get(URL)
                .then()
                .statusCode(200)
                .time(lessThan(2000L));
    }

}
