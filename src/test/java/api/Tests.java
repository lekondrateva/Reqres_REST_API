package api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class Tests {
    private final static String URL = "https://reqres.in/";

    @Test
    @DisplayName("POST - login using correct email, password")
    public void postSuccessLogin() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec200());
        String token = "QpwL5tke4Pnpja7X4";
        Login user = new Login("eve.holt@reqres.in", "cityslicka");
        SuccessLogin successLogin = given()
                .body(user)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().as(SuccessLogin.class);
        Assert.assertNotNull(successLogin.getToken());
        Assert.assertEquals(token, successLogin.getToken());
    }

    @Test
    @DisplayName("POST - login using incorrect email, empty password")
    public void postNotSuccessLogin() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec400());
        Login user = new Login("peter@klaven", "");
        UnsuccessLogin unsuccessLogin = given()
                .body(user)
                .post("api/login")
                .then().log().all()
                .extract().as(UnsuccessLogin.class);
        Assert.assertEquals("Missing password", unsuccessLogin.getError());
    }
}
