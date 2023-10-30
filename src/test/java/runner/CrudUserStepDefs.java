package runner;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class CrudUserStepDefs {
    Response response;
    RequestInfo requestInfo = new RequestInfo();
    Map<String, String> variables = new HashMap<>();
    private String email = Configuration.email;

    @Given("The account is not registered")
    public void theAccountIsNotRegistered() {
        response = getToken(email, Configuration.password);
        response.then().body("ErrorCode", equalTo(105));
    }

    @When("I send a {} request {string} with body")
    public void iSendARequestWithBody(String method, String url, String body) {
        requestInfo.setUrl(Configuration.host + this.replaceValues(url)).setBody(body);
        response = FactoryRequest.make(method).send(requestInfo);
    }

    @Then("The response code should be {int}")
    public void theResponseCodeShouldBe(int expectedCode) {
        response.then().statusCode(expectedCode);
    }

    @And("The attribute {string} must be equal to {string}")
    public void theAttributeMustBeEqualTo(String attribute, String expectedValue) {
        response.then().body(attribute, equalTo(expectedValue));
        email = response.then().extract().path("Email");
        System.out.println("**************************************************"+email);
    }

    @And("I get the token for the user")
    public void iGetTheTokenForTheUser() {
        response  = getToken(email, Configuration.password);
        String token =response.then().extract().path("TokenString");
        requestInfo = new RequestInfo();
        requestInfo.setHeader("Token", token);
    }

    private Response getToken(String email, String password) {
        String credentials = Base64.getEncoder()
                .encodeToString((email + ":" + password).getBytes());;
        requestInfo.setUrl(Configuration.host+"/api/authentication/token.json")
                .setHeader("Authorization", "Basic "+credentials);
        return FactoryRequest.make("get").send(requestInfo);
    }

    private String replaceValues(String value){
        for (String key:variables.keySet() ) {
            value=value.replace(key,variables.get(key));
        }
        return value;
    }
}
