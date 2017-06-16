package test.src;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class testAutomation {
	
	WebDriver driver;
	frameworkClass framework;
	String url;
	
	@BeforeClass
	@Parameters("browser")
	public void setUp(String browser){
		framework = new frameworkClass();
		driver = framework.getDriver(browser);
		url = "file:///" + System.getProperty("user.dir") + "/src/gol.html";
		driver.get(url);
	}
	
	@AfterClass
	public void tearDown(){
		driver.quit();
	}
	
	@BeforeMethod
	public void reset(){
		framework.panelReset(driver, url);
	}
	
	@Test
	public void test1_block(){
		int[][] initialState = {{0,0,0,0,0,0}, {0,1,1,0,0,0}, {0,1,1,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};
		int[][] iState = framework.setInitialState(driver, initialState);
		driver.findElement(By.id("step-button")).click();
		int[][] fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
		iState = fState;
		driver.findElement(By.id("step-button")).click();
		fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
	}
	
	
	@Test
	public void test2_beehive(){
		int[][] initialState = {{0,0,0,0,0,0}, {0,0,1,1,0,0}, {0,1,0,0,1,0}, {0,0,1,1,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};
		int[][] iState = framework.setInitialState(driver, initialState);
		driver.findElement(By.id("step-button")).click();
		int[][] fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
		iState = fState;
		driver.findElement(By.id("step-button")).click();
		fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
	}
	
	
	@Test
	public void test3_blinker(){
		int[][] initialState = {{0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,1,1,1,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};
		int[][] iState = framework.setInitialState(driver, initialState);
		driver.findElement(By.id("step-button")).click();
		int[][] fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
		iState = fState;
		driver.findElement(By.id("step-button")).click();
		fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
	}
	
	
	@Test
	public void test4_toad(){
		int[][] initialState = {{0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,1,1,1,0}, {0,1,1,1,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};
		int[][] iState = framework.setInitialState(driver, initialState);
		driver.findElement(By.id("step-button")).click();
		int[][] fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
		iState = fState;
		driver.findElement(By.id("step-button")).click();
		fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
	}
	
	
	@Test
	public void test5_beacon(){
		int[][] initialState = {{0,0,0,0,0,0}, {0,1,1,0,0,0}, {0,1,0,0,0,0}, {0,0,0,0,1,0}, {0,0,0,1,1,0}, {0,0,0,0,0,0}};
		int[][] iState = framework.setInitialState(driver, initialState);
		driver.findElement(By.id("step-button")).click();
		int[][] fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
		iState = fState;
		driver.findElement(By.id("step-button")).click();
		fState = framework.getPanelState(driver);
		Assert.assertTrue(framework.verifyState(iState, fState));
	}
	
}