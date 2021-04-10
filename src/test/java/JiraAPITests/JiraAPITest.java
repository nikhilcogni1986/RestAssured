package JiraAPITests;

import Files.payload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraAPITest {
  public static void main(String[] args) {
    RestAssured.baseURI = "http://localhost:8080";

    // Login scenario - Provide the credentials o as to get the unique
    // session ID which has to be used in further API calls

    // create a session filter instance which will store session ID and
    // can be used in further requests

    SessionFilter session = new SessionFilter();
    String loginResponse =
        given()
            .header("Content-Type", "application/json")
            .log()
            .all()
            .body(payload.loginCredentialsToJira())
            .log()
            .all()
            .filter(session)
            .when()
            .post("/rest/auth/1/session")
            .then()
            .log()
            .all()
            .extract()
            .asString();
    String message = "Hi How are you?";

    given()
        .pathParam("ID", "10102")
        .log()
        .all()
        .header("Content-Type", "application/json")
        .body(payload.addCommentToAnIssue(message))
        .filter(session)
        .when()
        .post("/rest/api/2/issue/{ID}/comment")
        .then()
        .log()
        .all()
        .assertThat()
        .statusCode(201);

    // add attachment
    // endpoint given looks like below
    // curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt"
    // http://myhost/rest/api/2/issue/TEST-123/attachments

    // X - followed by type of request
    // -H is header
    String addCommentResponse =
        given()
            .header("X-Atlassian-Token", "no-check")
            .header("Content-Type", "multipart/form-data")
            .filter(session)
            .pathParam("key", "10102")
            .multiPart("file", new File("Jira.txt"))
            .when()
            .post("/rest/api/2/issue/{key}/attachments")
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response().asString();

    JsonPath js1 = new JsonPath(addCommentResponse);
    String commentID = js1.get("id").toString();

    // get the issue and its details created above
    String issueDetails =
        given()
            .filter(session)
            .pathParam("Key", "10102")
            .queryParam("fields", "comment")
            .when()
            .get("/rest/api/2/issue/{Key}")
            .then()
            .log()
            .all()
            .extract().response()
            .asString();
    System.out.println(issueDetails);
    JsonPath js = new JsonPath(issueDetails);
    int numberOfComments = js.getInt("fields.comment.comments.size()");

    // loop through to get the ID for each comment
    for (int i = 0; i < numberOfComments; i++) {
      String commentIDIssue = js.get("fields.comment.comments[" + i + "].id").toString();
      System.out.println(commentIDIssue);
      if (commentIDIssue.equalsIgnoreCase(commentID)) {
        String bodyOfComment = js.get("fields.comment.comments[" + i + "].body").toString();
        System.out.println("Body Content"+bodyOfComment);
        Assert.assertEquals(bodyOfComment, message);
      }
    }
  }
}
