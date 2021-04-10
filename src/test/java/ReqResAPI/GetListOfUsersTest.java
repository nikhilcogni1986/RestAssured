package ReqResAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetListOfUsersTest {
  @Test
  public void getUsersList() {
    RestAssured.baseURI = "https://reqres.in/";
    String getListResponse = given()
        .queryParam("Page", "2")
        .when()
        .get("api/users")
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .asString();

      System.out.println(getListResponse);
      JsonPath js = new JsonPath(getListResponse);
      System.out.println("Per Page: "+ js.get("per_page"));
      int numberOfUsers = js.getInt("data.size");
      for(int i=0 ; i<numberOfUsers; i++)
      {
            String emailAddress = js.get("data["+i+"].email");
            String firstName = js.get("data["+i+"].first_name");
            System.out.println("First Name is: "+firstName);
            System.out.println("Email Address: "+emailAddress);
      }
      System.out.println("URL: "+js.get("support.url"));
      System.out.println("Text: "+js.get("support.text"));
  }
}
