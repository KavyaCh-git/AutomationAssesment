package AutomationAssignment.AutomationAssignment;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class AmazonTest {

	WebDriver driver;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
	}

	@Test
	public void testAmazonToSearch() throws InterruptedException {
		// Navigate to Amazon
		driver.get("https://www.amazon.com");

		if(driver.findElement(By.xpath("//h4[contains(text(),'Type the characters you see in this image:')]")).isDisplayed()) {
		Thread.sleep(10000);
		// Need to enter the captcha manually
		}

		// Search for "toys"
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("toys");
		searchBox.submit();

		Thread.sleep(3000);

		// Select 2 products from the search results
		List<WebElement> products = driver.findElements(By.cssSelector(".s-result-item .a-price-whole"));
		String firstProductPrice = products.get(0).getText();
		products.get(0).click();
		Thread.sleep(2000);
		String productDetailsPagePrice1 = driver
				.findElement(By.xpath("//div[@class='a-section a-spacing-micro']//span[@class='a-price-whole']"))
				.getText();
		driver.navigate().back();
		Thread.sleep(3000);
		List<WebElement> products1 = driver.findElements(By.cssSelector(".s-result-item .a-price-whole"));
		String secondProductPrice = products1.get(1).getText();
		products1.get(1).click();
		Thread.sleep(2000);
		String productDetailsPagePrice2 = driver
				.findElement(By.xpath("//div[@class='a-section a-spacing-micro']//span[@class='a-price-whole']"))
				.getText();

		if (driver.findElement(By.id("add-to-cart-button")).isEnabled()) {
			// Add both to Cart
			WebElement addToCart = driver.findElement(By.id("add-to-cart-button"));
			addToCart.click();
			driver.navigate().back();

			Thread.sleep(3000);
			List<WebElement> products2 = driver.findElements(By.cssSelector(".s-result-item .a-price-whole"));
			products2.get(1).click();
			addToCart.click();
		} else {
			driver.navigate().back();
			List<WebElement> products2 = driver.findElements(By.cssSelector(".s-result-item .a-price-whole"));
			products2.get(2).click();
			WebElement addToCart = driver.findElement(By.id("add-to-cart-button"));
			addToCart.click();
		}

		// Go to Cart and validate prices
		driver.findElement(By.xpath("//*[@id='nav-cart']")).click();
		String cartTotalPrice = driver.findElement(By.cssSelector(".sc-price")).getText();

		// Assertions for price Validations
		Assert.assertEquals(firstProductPrice, productDetailsPagePrice1, "Price mismatch on Product 1");
		Assert.assertEquals(secondProductPrice, productDetailsPagePrice2, "Price mismatch on Product 1");
		Assert.assertEquals(firstProductPrice.toString() + secondProductPrice.toString(), cartTotalPrice.toString(),
				"Cart Total price is not matched");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
