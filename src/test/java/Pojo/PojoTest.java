package Pojo;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import java.util.List;

import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.given;

public class PojoTest
{
      public static void main(String[] args)
      {
          RestAssured.baseURI = "https://reqres.in/";
    Root R1 =
        given()
            .queryParam("Page", "2")
            .expect()
            .defaultParser(Parser.JSON)
            .when()
            .get("api/users")
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .as(Root.class);

          System.out.println("Value of Page received from response is: "+R1.getPage());
          System.out.println("Total Pages received from response are: "+R1.getTotal_pages());
          System.out.println("Value of URL received from response is: "+R1.getSupport().getUrl());
          System.out.println("Value of text received from response is: "+R1.getSupport().getText());
          List<Data> data = R1.getData();
          System.out.println(data.size());
      }
}
