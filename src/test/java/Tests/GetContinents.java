package Tests;

import BaseSetup.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class GetContinents extends BaseTest {


    @Test
    public void getContinents() {
        RestAssured.given().when().get("https://dummy-json.mock.beeceptor.com/continents").then().statusCode(200).extract().response();

    }
}
