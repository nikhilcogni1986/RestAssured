package LibraryAPITests;

import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddBookTest
{
        @Test(dataProvider = "BooksData")
        public void addBookTest(String isbn, String aisle)
        {
            RestAssured.baseURI = "http://216.10.245.166";
            String addBookResponse = given().
                    header("Content-Type","application/json").
                    body(payload.addBookData(isbn, aisle)).
            when().post("/Library/Addbook.php").
            then().assertThat().statusCode(200).extract().asString();
            JsonPath js = new JsonPath(addBookResponse);
            String bookId = js.get("ID");
            System.out.println(bookId);


            String deleteResposne = given().
                    header("Content-Type","application/json").
                    body(payload.deleteBookData(bookId)).when()
                    .post("/Library/DeleteBook.php").then().
                    assertThat().statusCode(200).extract().asString();
        }

        @DataProvider(name="BooksData")
        public Object[][] getData()
        {
            return new Object[][] {{"nikhil","2274"},{"Prashant","2275"},{"Prasad","2276"}};
        }
}