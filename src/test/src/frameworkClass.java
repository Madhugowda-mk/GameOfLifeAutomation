package test.src;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class frameworkClass {
	
	public static WebDriver driver = null;
	
	public WebDriver getDriver(String browser){
		if(driver!=null){
			return driver;
		}
		else if(browser.contains("firefox"))
			driver =  new FirefoxDriver();
		else if(browser.contains("chrome")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver");
			driver = new ChromeDriver();
		}
		else{
			System.out.println("ERROR: Wrong Browser selection, returning firefox driver");
			driver =  new FirefoxDriver();
			return driver;
		}
		return driver;	
	}

	public int getPanelSize(WebDriver driver){
		WebElement slider = driver.findElement(By.id("matrix-size"));
		return Integer.parseInt(slider.getAttribute("value"));	
	}
	
	
	public void panelReset(WebDriver driver, String url){
		driver.get(url);
	}
	
	
	public int[][] getPanelState(WebDriver driver){
		int size = getPanelSize(driver);
		int[][] state = new int[size][size];
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				int xIndex = i+1;
				int yIndex = j+1;
				 WebElement elm = driver.findElement(By.xpath("//*[@id='gol']/div/div[" + xIndex + "]/div[" + yIndex + "]"));
				 if (elm.getAttribute("class").contains("alive")) {
					 state[i][j] = 1;
				 }
				 else {
					state[i][j] = 0;
				 }  
			}
		}
		return state;
	}
	
	
	public int getCellState(int[][] panelState, int xIndex, int yIndex){
		int panelSize = panelState[0].length;
		if(xIndex>=0 && xIndex<panelSize && yIndex>=0 && yIndex<panelSize){
			return panelState[xIndex][yIndex];
		}
		else{
			return -1;
		}
	}
	
	
	public int[] getNeighboursState(int[][] panelState, int xIndex, int yIndex){
		int[] neighboursState = new int[9];
		
		int nxIndex = xIndex-1;
		int nyIndex = yIndex-1;
		neighboursState[0] = getCellState(panelState, nxIndex, nyIndex);
		
		nxIndex = xIndex;
		nyIndex = yIndex-1;
		neighboursState[1] = getCellState(panelState, nxIndex, nyIndex);
		
		nxIndex = xIndex+1;
		nyIndex = yIndex-1;
		neighboursState[2] = getCellState(panelState, nxIndex, nyIndex);
		
		nxIndex = xIndex-1;
		nyIndex = yIndex;
		neighboursState[3] = getCellState(panelState, nxIndex, nyIndex);
		
		neighboursState[4] = -1;
		
		nxIndex = xIndex+1;
		nyIndex = yIndex;
		neighboursState[5] = getCellState(panelState, nxIndex, nyIndex);
		
		nxIndex = xIndex-1;
		nyIndex = yIndex+1;
		neighboursState[6] = getCellState(panelState, nxIndex, nyIndex);
		
		nxIndex = xIndex;
		nyIndex = yIndex+1;
		neighboursState[7] = getCellState(panelState, nxIndex, nyIndex);
		
		nxIndex = xIndex+1;
		nyIndex = yIndex+1;
		neighboursState[8] = getCellState(panelState, nxIndex, nyIndex);
		
		return neighboursState;
	}
	
	
	public int[][] setInitialState(WebDriver driver, int[][] initialState){
		int size = getPanelSize(driver);
		if(initialState[0].length==size && initialState.length==size){
			for(int i=0; i<size; i++){
				for(int j=0; j<size; j++){
					if(initialState[i][j] == 1){
						int xIndex = i+1;
						int yIndex = j+1;
						WebElement elm = driver.findElement(By.xpath("//*[@id='gol']/div/div[" + xIndex + "]/div[" + yIndex + "]"));
						elm.click();
					}
				}
			}
		}
		else{
			System.out.println("ERROR: Initail State Array size: [" + initialState[0].length + "][" + initialState.length + "] with Panel Size: [" + size + "][" + size + "] does not match");
			int[][] tmp = {{-1}, {-1}};
			return tmp;
		}
		return getPanelState(driver);
	}
	
	
	public boolean verifyState(int[][] initialState, int[][] currentState){
		Boolean check = false;
		if(initialState[0].length == currentState[0].length && initialState.length == currentState.length){
			for(int i=0; i<initialState.length; i++){
				for(int j=0; j<initialState[0].length; j++){
					if(initialState[i][j] == 1){
						int[] initialNeighboursState = getNeighboursState(initialState, i, j);
						int cellState = getCellState(initialState, i, j);
						int nextGenerationCellState = getCellState(currentState, i, j);
						check = verifyRules(initialNeighboursState, cellState, nextGenerationCellState);	
					}
					if(currentState[i][j] == 1){
						int[] initialNeighboursState = getNeighboursState(initialState, i, j);
						int cellState = getCellState(initialState, i, j);
						int nextGenerationCellState = getCellState(currentState, i, j);
						check = verifyRules(initialNeighboursState, cellState, nextGenerationCellState);
					}
				}
			}
		}
		else{
			System.out.println("ERROR: 2 states size are equal");
		}
		return check;
	}
	
	
	public boolean verifyRules(int[] neighboursState, int cellState, int nextGenerationCellState) {
		Boolean check = false;
		int aliveCellCount = getAliveCellsCount(neighboursState);
		
//		Verify First rule.
		if(aliveCellCount<2  && nextGenerationCellState==0){
			check = true;
		}
		
//		Verify Second rule.
		if((aliveCellCount==2 || aliveCellCount==3) && cellState==1 && nextGenerationCellState==1){
			check = true;
		}
		
//		Verify Third rule.
		if(aliveCellCount>3 && nextGenerationCellState==0){
			check = true;
		}
		
//		Verify Forth rule.
		if(aliveCellCount==3 && cellState==0 && nextGenerationCellState==1){
			check = true;
		}
		
		return check;
	}
	
	
	public int getAliveCellsCount(int[] neighboursState){
		int count = 0;
		for(int i=0; i<neighboursState.length; i++){
			if(neighboursState[i] == 1){
				count = count + 1;
			}
		}
		return count;
	}
}
