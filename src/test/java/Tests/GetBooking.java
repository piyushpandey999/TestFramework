package Tests;

import BaseSetup.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class GetBooking extends BaseTest {

    @Test
    public void GetBookings(){
        RestAssured.given().when().get("https://restful-booker.herokuapp.com/booking").then().statusCode(200).extract().response();

    }
}
