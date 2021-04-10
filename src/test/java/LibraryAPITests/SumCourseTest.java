package LibraryAPITests;

import Files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumCourseTest {
  @Test
  public void calculateSumCourseTest() {
    JsonPath js = new JsonPath(payload.complexJsonData());
    int count = js.getInt("courses.size");
    int totalCourseAmount = 0;
    for (int i = 0; i < count; i++) {
      // get the proce for each course
      int coursePrice = js.get("courses[" + i + "].price");
      int numberOfCopies = js.get("courses[" + i + "].copies");
      int amount = coursePrice * numberOfCopies;
      totalCourseAmount = totalCourseAmount + amount;
      System.out.println(amount);
    }
    System.out.println("Total amount for all courses is: " + totalCourseAmount);
    int actualPurchaseAmount = js.get("dashboard.purchaseAmount");
    Assert.assertEquals(actualPurchaseAmount, totalCourseAmount);
  }
}
