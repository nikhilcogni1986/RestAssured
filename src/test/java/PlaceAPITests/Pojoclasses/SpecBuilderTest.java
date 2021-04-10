package PlaceAPITests.Pojoclasses;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
  public static void main(String[] args) {
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

    RequestSpecification request =
        new RequestSpecBuilder()
            .setBaseUri("https://rahulshettyacademy.com")
            .addQueryParam("key", "qaclick123")
            .setContentType(ContentType.JSON)
            .build();

    ResponseSpecification resspec =
        new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

    RequestSpecification complete_request = given().spec(request).body(AP);
    Response response =
        complete_request
            .when()
            .post("/maps/api/place/add/json")
            .then()
            .spec(resspec)
            .extract()
            .response();

    String responseString = response.asString();
    System.out.println(responseString);
  }
}