package saucedemoPage;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Purchase_item {
	@Test
	public void testNGAsserts() throws Exception{
		// Source file (spreadsheet)
		FileInputStream fs = new FileInputStream("./src/test/resources/E2ETest.xlsx");
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		DataFormatter df = new DataFormatter();
		String user = df.formatCellValue(sheet.getRow(1).getCell(0));
		String password = df.formatCellValue(sheet.getRow(1).getCell(1));
		String first_name = df.formatCellValue(sheet.getRow(1).getCell(2));
		String last_name = df.formatCellValue(sheet.getRow(1).getCell(3));
		String postal_code = df.formatCellValue(sheet.getRow(1).getCell(4));
		
		// Chrome and Firefox to make it work for multiple browsers
		System.setProperty("webdriver.chrome.driver","./driver/chromedriver");
		WebDriver driver = new ChromeDriver();	
		System.setProperty("webdriver.gecko.driver","./driver/geckodriver");
		WebDriver driver2 = new FirefoxDriver();
		
		// ExtentReports
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("Purchase_item.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		ExtentTest logStep = extent.createTest("Purchase item", "Validate that items can be purchased");
		
	logStep.info("Step 1: Open website (Firefox and Chrome)");
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Swag Labs");
		driver2.get("https://www.saucedemo.com/");
		driver2.manage().window().maximize();
		System.out.println(title);
		Assert.assertEquals(title, "Swag Labs");
        System.out.println("AssertEquals Test Passed, initial URL is correct");
		Thread.sleep(1000);
		
	logStep.info("Step 2: Enter the credentials and click login button");		
		driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(user);
		driver2.findElement(By.xpath("//*[@id='user-name']")).sendKeys(user);
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);
		driver2.findElement(By.xpath("//*[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id='login-button']")).click();
		driver2.findElement(By.xpath("//*[@id='login-button']")).click();
		Thread.sleep(1000);
		
	logStep.info("Step 3: Asserting if user is logged in");		
        String expText = "PRODUCTS";
        String expText2 = "PRODUCTS";
		String productPage = driver.findElement(By.xpath("//*[@id='header_container']/div[2]/span")).getText();
		String productPage2 = driver2.findElement(By.xpath("//*[@id='header_container']/div[2]/span")).getText();
        Assert.assertEquals(productPage, expText);
        Assert.assertEquals(productPage2, expText2);
        System.out.println(productPage);
        System.out.println(productPage2);
        logStep.pass("Login success"); 
        
    logStep.info("Step 3: Adding a specific product to the shopping cart");
		driver.findElement(By.xpath("//*[@id='add-to-cart-sauce-labs-onesie']")).click();
		driver2.findElement(By.xpath("//*[@id='add-to-cart-sauce-labs-onesie']")).click();
		Thread.sleep(1000);
        logStep.pass("Adding item"); 
		
    logStep.info("Step 4: Asserting that product is added to the shopping cart");		
        String removeText = "REMOVE";
        String removeText1 = "REMOVE";
		String remove = driver.findElement(By.xpath("//button[text()='Remove']")).getText();
		String remove1 = driver2.findElement(By.xpath("//button[text()='Remove']")).getText();
        Assert.assertEquals(remove, removeText);
        Assert.assertEquals(remove1, removeText1);
        System.out.println("Onesie product is added to the shopping cart");
        System.out.println("Onesie product is added to the shopping cart");
        logStep.pass("Onesie product is added to the shopping cart"); 
    
     logStep.info("Step 5: Completing a purchase of a product");	
		driver.findElement(By.xpath("//*[@id='shopping_cart_container']")).click();
		driver2.findElement(By.xpath("//*[@id='shopping_cart_container']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='checkout']")).click();
		driver2.findElement(By.xpath("//*[@id='checkout']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='first-name']")).sendKeys(first_name);
		driver2.findElement(By.xpath("//*[@id='first-name']")).sendKeys(first_name);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='last-name']")).sendKeys(last_name);
		driver2.findElement(By.xpath("//*[@id='last-name']")).sendKeys(last_name);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='postal-code']")).sendKeys(postal_code);
		driver2.findElement(By.xpath("//*[@id='postal-code']")).sendKeys(postal_code);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='continue']")).click();
		driver2.findElement(By.xpath("//*[@id='continue']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='finish']")).click();
		driver2.findElement(By.xpath("//*[@id='finish']")).click();
		Thread.sleep(1000);
		
	    logStep.info("Step 5: Asserting that purchase was completed");		
        String backText = "BACK HOME";
        String backText1 = "BACK HOME";
		String back = driver.findElement(By.xpath("//*[@id='back-to-products']")).getText();
		String back1 = driver2.findElement(By.xpath("//*[@id='back-to-products']")).getText();
        Assert.assertEquals(back, backText);
        Assert.assertEquals(back1, backText1);
        System.out.println("Purchase is completed");
        System.out.println("Purchase is completed");
        logStep.pass("Purchase is completed"); 
	
		driver.close();
		driver2.close();
	logStep.info("End of the test");
	extent.flush();
	

	}
}