package PlaceAPITests;

import io.restassured.path.json.JsonPath;
import Files.payload;

public class ComplexJsonParser
{
      public static void main(String[] args)
      {
          JsonPath js = new JsonPath(payload.complexJsonData());
          int numberOfCourses = js.getInt("courses.size");
          System.out.println("Number of courses in the response are: "+numberOfCourses);
          System.out.println("Total prices for all courses is: "+js.get("dashboard.purchaseAmount"));
          System.out.println("Title of the first course is: " + js.get("courses[0].title"));

          //Print All course titles and their respective Prices
          for(int i=0 ; i<numberOfCourses ; i++)
          {
              System.out.println(js.get("courses["+i+"].title"));
              System.out.println("Price for "+js.get("courses["+i+"].title")+" is-->"+js.get("courses["+i+"].price"));

              //Print no of copies sold by RPA Course
              if (js.get("courses["+i+"].title").toString().equalsIgnoreCase("RPA"))
              {
                  System.out.println("Number of copies sold by RPA is:" +js.get("courses["+i+"].copies"));
                  break;
              }
          }

          //validate sum validation for all the courses




      }
}