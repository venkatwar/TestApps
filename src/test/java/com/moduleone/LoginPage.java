package com.moduleone;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPage {
	private WebDriver driver;
	private String userName = "//input[@name='UserName']";
	private String password = "//input[@name='Password']";
	private String login = "//input[@name='Login']";
	private String logout = "//a[@href='Login.html']";
	private Xls_Reader reader;

	@BeforeMethod
	public void beforemethod() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://demosite.executeautomation.com/Login.html");
	}

	@Test(dataProvider = "getInputData")
	public void launchBrowser(String usern, String pwd) {
		sendKeysByXpath(userName, usern);
		sendKeysByXpath(password, pwd);
		clickelementByXpath(login);
		clickelementByXpath(logout);
	}

	@DataProvider
	public Iterator<Object[]> getInputData() {
		ArrayList<Object[]> testdata = getDataFromExcel();
		return testdata.iterator();
	}

	public ArrayList<Object[]> getDataFromExcel() {
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		try {
			reader = new Xls_Reader("C:/Users/Naveen/workspace/TestApps/src/test/resources/data/TestData.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int rowNum = 2; rowNum <= reader.getRowCount("Sheet1"); rowNum++) {
			String username = reader.getCellData("Sheet1", "UserName", rowNum);
			String password = reader.getCellData("Sheet1", "Password", rowNum);
			Object ob[] = { username, password };
			myData.add(ob);
		}
		return myData;

	}

	public void sendKeysByXpath(String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.sendKeys(value);
	}

	public void clickelementByXpath(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}

	@AfterMethod
	public void afterMethod() {
		driver.close();
	}
}
