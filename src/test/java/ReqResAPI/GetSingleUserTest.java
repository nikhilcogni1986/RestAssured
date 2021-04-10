package ReqResAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetSingleUserTest {
  @Test
  public void getSingleUser() {
    RestAssured.baseURI = "https://reqres.in";
    String getResponse =
        given()
            .when()
            .get("api/users/8")
            .then()
            .log()
            .all()
            .assertThat()
            .statusCode(200)
            .extract()
            .asString();

    System.out.println(getResponse);
    JsonPath js = new JsonPath(getResponse);

    System.out.println(js.get("data.id"));
    System.out.println(js.get("data.email"));
    System.out.println(js.get("data.first_name"));
    System.out.println(js.get("data.last_name"));
  }

  @Test
  public void getSingleUserNotFound() {
    RestAssured.baseURI = "https://reqres.in";
            given()
            .when()
            .get("api/users/23")
            .then()
            .assertThat()
            .statusCode(404);
  }
}