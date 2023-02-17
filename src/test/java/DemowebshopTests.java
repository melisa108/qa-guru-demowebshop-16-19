import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemowebshopTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }

    @Test
    void addToCartTest() {
        String cookieValue = "C3B23684816523C3E10934D04BB7269BD98869D9B42CE39E42379171707" +
                "1721A102B8200F60C64202EFF08A8BB31019B281869DCC0A013C1BDDB8A2C040B083CB7EE18743398AB5F22BC4F575" +
                "96FA47732C6ED3EF4505FAC4C44E1324BC72D5D6C434630381EAAD9EF1A5CC8316CAA105B3A34D3709FBA22C343FD" +
                "CB8F4546262AC16884DD69B73F0ECE3F16DFCBAE2A;",
                body = "product_attribute_72_5_18=65" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&product_attribute_72_8_30=94" +
                        "&addtocart_72.EnteredQuantity=3";

        given()
//                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", cookieValue) // todo move to file
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void addToCartAnonymTest() {
        String body = "product_attribute_72_5_18=65" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=3";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(3)"));
    }

    @Test
    @DisplayName("Authorized customer adds the item in wish list ")
    void addToWishListTest() {
        String cookieValue = "CEE9638B0D901722979027B105EC69CAB66F3BBC6002FC4D4DA2F96508DF91B6FECC053" +
                "7FC37841822B34469E1E8262C3FB257252F9C280265228D598D3A36A1FDAFE64CF87D91B23C7176AF48A" +
                "73D93F533961EAAC1B23DDB24348A6E3BFAC957DE754A0DBA97467DB761867B59BBCB564D6217929F2E25" +
                "774319313743360D204726E8B76839E80A5278812B8AC6B19A7998A8D75E6D6A7D7F9DE974BBF3BEEFF5" +
                "B1BF35FD6FE7558F5B1ADBF316AE",
                bodyValue = "giftcard_4.RecipientName=Tester&giftcard_4.SenderName=King+Author&" +
                        "giftcard_4.Message=&addtocart_4.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", cookieValue)
                .body(bodyValue)
                .when()
                .post("/addproducttocart/details/4/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"));

    }
}
