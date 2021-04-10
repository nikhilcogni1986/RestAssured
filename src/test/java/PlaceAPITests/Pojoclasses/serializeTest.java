package PlaceAPITests.Pojoclasses;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class serializeTest {
  public static void main(String[] args) {
    RestAssured.baseURI = "https://rahulshettyacademy.com";
    AddPlace AP = new AddPlace();

    AP.setAccuracy(50);
    AP.setAddress("29, side layout, cohen 09");
    AP.setLanguage("French-IN");
    AP.setName("Frontline house");
    AP.setPhoneNumber("(+91) 983 893 3937");

    // set types
    ArrayList<String> myList = new ArrayList<>();
    myList.add("Shoe Park");
    myList.add("shop");
    AP.setTypes(myList);

    location l1 = new location();
    l1.setLat(-38.383494);
    l1.setLng(33.427362);
    AP.setLocation(l1);

    Response getLocationResponse =
        given()
            .log()
            .all()
            .queryParam("key", "qaclick123")
            .body(AP)
            .when()
            .post("/maps/api/place/add/json")
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response();

    String responseString = getLocationResponse.asString();
    System.out.println(responseString);
  }
}