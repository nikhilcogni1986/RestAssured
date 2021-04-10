package PlaceAPITests;

import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EndToEndAPITest {
  @Test
  public void getWeatherDetailsWithLogs() throws IOException {
    RestAssured.baseURI = "https://rahulshettyacademy.com";
    given()
        .log()
        .all()
        .queryParam("key", "qaclick123")
        .header("Content-Type", "Application/json")
        .body(
            new String(
                Files.readAllBytes(Paths.get("F:\\RestAssuredAutomation\\PostLocationData.txt"))))
        .when()
        .post("maps/api/place/add/json")
        .then()
        .log()
        .all()
        .assertThat()
        .statusCode(200)
        .body("scope", equalTo("APP"))
        .header("Server", "Apache/2.4.18 (Ubuntu)");
  }

  @Test
  public void getWeatherDetailsWithNoLogs() {
    RestAssured.baseURI = "https://rahulshettyacademy.com";
    String response =
        given()
            .queryParam("key", "qaclick123")
            .header("Content-Type", "application/json")
            .body(payload.addPlaceData())
            .when()
            .post("maps/api/place/add/json")
            .then()
            .assertThat()
            .statusCode(200)
            .body("scope", equalTo("APP"))
            .header("Server", "Apache/2.4.18 (Ubuntu)")
            .extract()
            .asString();

    System.out.println("Post Response: " + response);
    System.out.println(
        "============================================================================");
    JsonPath js = new JsonPath(response);
    String place_id = js.getString("place_id");
    System.out.println(response);
    System.out.println("Place ID extracted is " + place_id);

    String newAddress = "Summer Walk, Africa";

    // now we go for put request to update the place
    String updateResponse =
        given()
            .queryParam("key", "qaclick123")
            .header("Content-Type", "application/json")
            .body(
                "{\n"
                    + "\"place_id\":\""
                    + place_id
                    + "\",\n"
                    + "\"address\":\""
                    + newAddress
                    + "\",\n"
                    + "\"key\":\"qaclick123\"\n"
                    + "}\n")
            .when()
            .put("maps/api/place/update/json")
            .then()
            .assertThat()
            .log()
            .all()
            .statusCode(200)
            .body("msg", equalTo("Address successfully updated"))
            .extract()
            .asString();
    System.out.println("Updated Response: " + updateResponse);

    // now get the details of the place - place id comes from Post request
    String getPlaceResponse =
        given()
            .queryParam("key", "qaclick123")
            .queryParam("place_id", place_id)
            .when()
            .get("/maps/api/place/get/json")
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .asString();
    System.out.println("Get Response: " + getPlaceResponse);

    JsonPath js1 = new JsonPath(getPlaceResponse);
    String actualAddress = js1.getString("address");
    System.out.println("Actual Address: " + actualAddress);
  }
}
