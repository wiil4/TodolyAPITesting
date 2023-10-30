package rawTests.userMethods;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserCrudTest {
    Response response;
    RequestInfo requestInfo = new RequestInfo();
    Map<String,String> variables = new HashMap<>();
    String token;

    @Test
    @Description("Verify user CRUD")
    public void userCrudTest(){
        //CREATE
        JSONObject body =new JSONObject();
        body.put("Email", Configuration.email);
        body.put("FullName", Configuration.fullName);
        body.put("Password", Configuration.password);
        requestInfo.setUrl(Configuration.host+"/api/user.json").setBody(body.toString());
        response = FactoryRequest.make("post").send(requestInfo);

        //Assertion
        response.then().body("Email", equalTo(Configuration.email));
        int userId =response.then().extract().path("Id");

        //GETTOKEN
        String credentials = encodeBase64(Configuration.email, Configuration.password);

        requestInfo.setUrl(Configuration.host+"/api/authentication/token.json")
                .setHeader("Authorization", "Basic "+credentials);
        response = FactoryRequest.make("get").send(requestInfo);
        token =response.then().extract().path("TokenString");
        requestInfo = new RequestInfo();
        requestInfo.setHeader("Token", token);

        //UPDATE
        body.put("Email",Configuration.updatedEmail);
        requestInfo.setUrl(Configuration.host+"/api/user/0.json").setBody(body.toString());
        response = FactoryRequest.make("put").send(requestInfo);
        //Assertion
        response.then().body("Email", equalTo(Configuration.updatedEmail));
        response.then().body("Id", equalTo(userId));

        //GETTOKEN
        credentials = encodeBase64(Configuration.updatedEmail,Configuration.password);

        requestInfo.setUrl(Configuration.host+"/api/authentication/token.json")
                .setHeader("Authorization", "Basic "+credentials);
        response = FactoryRequest.make("get").send(requestInfo);
        token =response.then().extract().path("TokenString");
        requestInfo = new RequestInfo();
        requestInfo.setHeader("Token", token);

        //DELETE
        requestInfo.setUrl(Configuration.host+"/api/user/0.json").setBody("");
        response = FactoryRequest.make("delete").send(requestInfo);
        //Assertion
        response.then().body("Email",equalTo(Configuration.updatedEmail));
        response.then().body("Id", equalTo(userId));
    }

    private String encodeBase64(String email, String password) {
        return Base64.getEncoder()
                .encodeToString((email + ":" + password).getBytes());
    }

}
