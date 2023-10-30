# Testing User API Methods Todo.ly
This automated test was made with Java(Gradle and Groovy) and JUnit. Some of the dependencies taken from Maven repository are related to:
* JsonPath
* RestAssured
* Cucumber JVM
## Test Procedure
### Postman
A collection created in Postman was used to fully detect the steps before the automated implementation with RestAssured. The UserCrud test was made following the available methods for User in [Todo.ly API](http://todo.ly/apiwiki/?user). The test case implemented follows the described steps based on json:
  1. Creates a user (email, fullname, password).
  2. Get the token for that user.
  3. Update the user email.
  4. Get the new token.
  5. Delete the user.
 
### Raw Test
[UserCrudRawTest](src/test/java/rawTests/userMethods/UserCrudTest.java) was implemented before creating the feature with Gherkin.
### Cucumber
The implementation for User can be seen at [CRUDUser](src/test/resources/CRUDUser.feature), and it's corresponding steps can be seen at [CrudUserSteps](src/test/java/runner/CrudUserStepDefs.java).
### Configuration Values
There has been implemented a [Configuration](src/test/java/config/Configuration.java) file containing data that can be modified for the test, which is described below:

````java
public class Configuration {
    public static String host = "https://todo.ly";
    public static String email = "user@testing.com";
    public static String fullName = "userTesting";
    public static String password = "12345";

    public static String updatedEmail = "userqatest@testing.com";
}
````

