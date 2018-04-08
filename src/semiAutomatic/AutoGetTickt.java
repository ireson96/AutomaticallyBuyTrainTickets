package semiAutomatic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class AutoGetTickt {
	private String loginUrl = "https://kyfw.12306.cn/otn/login/init";
	private String checkUrl = "https://kyfw.12306.cn/otn/leftTicket/init";
	private String correctUrl = "https://kyfw.12306.cn/otn/index/initMy12306";
	private WebDriver driver;
	public AutoGetTickt() throws InterruptedException {
		// TODO Auto-generated constructor stub
		System.out.println("hello github");
		System.setProperty("webdriver.gecko.driver", ".\\tool\\geckodriver.exe");  
		//System.setProperty("webdriver.firefox.marionette", ".\\Tools\\geckodriver.exe");
		System.setProperty("webdriver.firefox.bin","D:\\Program Files\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		
		if(dologin()) {
			return;
		}
		
		/*
		WebElement fromStationText = driver.findElement(By.id("fromStationText"));
		fromStationText.clear();
		fromStationText.click();
		fromStationText.sendKeys("烟台");
		driver.findElement(By.id("citem_0")).click();
		WebElement toStationText = driver.findElement(By.id("fromStationText"));
		toStationText.clear();
		toStationText.click();
		toStationText.sendKeys("济南");
		driver.findElement(By.id("citem_0")).click();
		
		*/
		Thread.sleep(10000);
		//driver.close();
	}
	private boolean dologin() {
		try {
			driver.get(loginUrl);
			WebElement username = driver.findElement(By.id("username"));
			WebElement password = driver.findElement(By.id("password"));
			username.sendKeys("***");
			password.sendKeys("***");
			
		} catch (Exception e) {
			// TODO: handle exception
			driver.close();
			return false;
		} finally {
			//driver.close();
		}
		
		return true;
	}
	static public void main(String args[]) throws InterruptedException {
		new AutoGetTickt();
	}
}
