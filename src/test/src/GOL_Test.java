package test.src;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GOL_Test extends frameworkClass{

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver");
		WebDriver driver = new ChromeDriver();
		int[][] initialState = {{0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,1,1,1,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};
		String url = System.getProperty("user.dir") + "/src/gol.html";
		driver.get("file:///" + url);
		frameworkClass framework = new frameworkClass();
		int[][] iState = framework.setInitialState(driver, initialState);
		driver.findElement(By.id("step-button")).click();
		int[][] fState = framework.getPanelState(driver);	
		System.out.println(framework.verifyState(iState, fState));
		driver.findElement(By.id("step-button")).click();
		int[][] fState1 = framework.getPanelState(driver);
		System.out.println(framework.verifyState(fState, fState1));
		
		driver.quit();
		
	}
}
