package semiAutomatic;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;



public class AutoGetTickt {
	private String loginUrl = "https://kyfw.12306.cn/otn/login/init";
	private String checkUrl = "https://kyfw.12306.cn/otn/leftTicket/init";
	private WebDriver driver;
	private static String[] sit = {"1A","1B","1C","1D","1E"};
	public AutoGetTickt() throws InterruptedException {
		System.out.println("hello github\nhello Java");
		System.setProperty("webdriver.gecko.driver", ".\\tool\\geckodriver.exe");  
		//System.setProperty("webdriver.firefox.marionette", ".\\Tools\\geckodriver.exe");
		System.setProperty("webdriver.firefox.bin","D:\\Program Files\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		
		if(dologin()) {
			String ticketId = findTicketId();
			trackTicket(ticketId);
			if(buyTicket()) {
				System.out.println("ok");
			}else {
				System.out.println(":(");
			}
		}
		Thread.sleep(3000000);
		System.out.println("Good Bye!");
	}
	
	private boolean buyTicket() {
		try {
			for(String name : tempConfig.passengers) {
				WebElement input = driver.findElement(By.id("quickQueryPassenger_id"));
				input.clear();
				input.click();
				input.sendKeys(name);
				driver.findElement(By.id("submit_quickQueryPassenger")).click();
				driver.findElement(By.id("normal_passenger_id")).findElement(By.className("check")).click();
			}
			driver.findElement(By.id("submitOrder_id")).click();
			Thread.sleep(500);
			try {
				WebElement sitSelector = driver.findElement(By.id("erdeng1"));
				for(int i=0;i<tempConfig.passengers.length;i++) {
					System.out.println(i+"   "+sit[i]);
					sitSelector.findElement(By.id(sit[i])).click();
					
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			driver.findElement(By.id("qr_submit_id")).click();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}
	
	
	private String findTicketId() {
		try {
			driver.get(checkUrl);
			Scanner input = new Scanner(System.in);
			String fromStation = tempConfig.fromStation;
			String toStation = tempConfig.toStation;
			driver.findElement(By.id("date_icon_1")).click();
			System.out.print("请手动选择日期：\n选择完毕？");
			input.nextLine();
			WebElement fromStationText = driver.findElement(By.id("fromStationText"));
			fromStationText.clear();
			fromStationText.click();
			fromStationText.sendKeys(fromStation);
			driver.findElement(By.id("citem_0")).click();
			WebElement toStationText = driver.findElement(By.id("toStationText"));
			toStationText.clear();
			toStationText.click();
			toStationText.sendKeys(toStation);
			driver.findElement(By.id("citem_0")).click();
			driver.findElement(By.id("query_ticket")).click();
			WebElement allticket = driver.findElement(By.id("queryLeftTable"));
			System.out.println("请输入您要购买的车次位于当前页面的第几列：");
			int line = input.nextInt();
			WebElement needTicket = allticket.findElements(By.tagName("tr")).get((line-1)*2);
			System.out.println(needTicket.getText());
			System.out.println(":)");
			input.close();
			return needTicket.getAttribute("id");
		} catch (Exception e) {
			driver.close();
			return null;
		}
		
	}
	
	
	
	private boolean trackTicket(String ticketId) {
		boolean flag = true;
		String currentUrl = driver.getCurrentUrl();
		while(flag) {
			try {
				driver.findElement(By.id("query_ticket")).click();
				driver.findElement(By.id(ticketId)).findElement(By.className("no-br")).click();
				currentUrl = driver.getCurrentUrl();
				if(!currentUrl.equals(checkUrl)) {
					System.out.println("可以购买");				
					flag = false;
				}else {
					System.out.println(driver.findElement(By.id(ticketId)).findElement(By.className("no-br")).getText());
				}
			} catch (Exception e) {
				if(!currentUrl.equals(checkUrl)) {
					System.out.println("可以购买");				
					flag = false;
				}else {
					System.out.println(e);
				}
				
			}
		}
		return true;
	}
	
	
	
	private boolean dologin() {
		try {
			driver.get(loginUrl);
			WebElement username = driver.findElement(By.id("username"));
			WebElement password = driver.findElement(By.id("password"));
			username.clear();
			username.click();
			username.sendKeys(tempConfig.username);
			password.clear();
			password.click();
			password.sendKeys(tempConfig.password);
			String currentUrl = driver.getCurrentUrl();
			while(currentUrl.equals(loginUrl)||currentUrl.equals(loginUrl+"#")) {
				System.out.println("等待选择正确的验证码...");
				Thread.sleep(5000);
				currentUrl = driver.getCurrentUrl();
			}
			Thread.sleep(2000);
			return true;
			
		} catch (Exception e) {
			driver.close();
			return false;
		} finally {
			//driver.close();
		}
	}
	
	
	static public void main(String args[]) throws InterruptedException {
		new AutoGetTickt();
		/*for(int i=0;i<tempConfig.passengers.length;i++) {
			System.out.println(i+"   "+sit[i]);
			//sitSelector.findElement(By.id(sit[i])).click();
		}*/
	}
}
