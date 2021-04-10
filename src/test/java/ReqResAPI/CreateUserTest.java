package ReqResAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateUserTest
{
        @Test
        public void createUserTest()
        {
            RestAssured.baseURI = "https://reqres.in";
            String response = given().header("Content-Type","application/json").
                    body("{\n" +
                            "    \"name\": \"Prasad\",\n" +
                            "    \"job\": \"leader\"\n" +
                            "}").
                    when().
                    post("/api/users").
                    then().
                    assertThat().
                    statusCode(201).
                    extract().
                    asString();
            System.out.println(response);
            JsonPath js = new JsonPath(response);
            System.out.println("Name of the User created--> "+js.get("name"));
            System.out.println("job of the User created--> "+js.get("job"));
            System.out.println("id of the User created--> "+js.get("id"));
            System.out.println("createdAt--> "+js.get("createdAt"));

        }
}
