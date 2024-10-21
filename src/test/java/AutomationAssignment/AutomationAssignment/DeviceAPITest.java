package AutomationAssignment.AutomationAssignment;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import static io.restassured.RestAssured.given;

public class DeviceAPITest {

	@Test
	public void addNewDeviceTest() {
		RestAssured.baseURI = "https://api.restful-api.dev/objects";
		String requestBody = "{\n" + 
			" \"name\": \"Apple Max Pro 1TB\",\n" +
			" \"data\": {\n" +
			"  \"year\": 2023, \n" +
			" \"price\": 7999.99, \n" +
			" \"CPU model\": \"Apple ARM A7\", \n" +
			"\"Hard disk size\": \"1 TB\"\n" +
			" }\n" +
			"}";
		Response response = given()
				.header("Content-Type", "application/json").body(requestBody).when().post();
		
		Assert.assertEquals(response.getStatusCode(), 200, "Status Code should be 200");
		
		String responseBody = response.getBody().asString();
		System.out.println("Reponse Body: " + responseBody);
		
		Assert.assertTrue(responseBody.contains("Apple Max Pro 1TB"),"Device name should Match");
		Assert.assertTrue(responseBody.contains("2023"),"Year should Match");
		Assert.assertTrue(responseBody.contains("7999.99"),"Price should Match");
		Assert.assertTrue(responseBody.contains("Apple ARM A7"),"CPU name should match");
		Assert.assertTrue(responseBody.contains("1 TB"),"Size shold Match");
		Assert.assertNotNull(response.jsonPath().getString("id"), "ID should not be null");
		Assert.assertNotNull(response.jsonPath().getString("createdAt"), "createdAt should not be null");		
	}
	
}
