package Tests;

import BaseSetup.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class GetUsers extends BaseTest {


    @Test
    public void getUser(){
        RestAssured.given().when().get("https://reqres.in/api/users").then().statusCode(200).extract().response();
    }
}
