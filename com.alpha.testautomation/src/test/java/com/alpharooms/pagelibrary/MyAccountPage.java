package com.alpharooms.pagelibrary;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author rameshk
 *
 */
public class MyAccountPage {
	static Logger log = Logger.getLogger(MyAccountPage.class.getName());
	WebDriver driver;
	By emptyEmailErrormessage = By.xpath("//*[@id='signinpage']/div/div/div/form/div[1]/div/ul/li");
	By searchButton = By.xpath("//*[@id='signinpage']/div/div/div/form/div[7]/button");
	public MyAccountPage(WebDriver driver) {
	
		this.driver = driver;
		// TODO Auto-generated constructor stub
	}
	
	public boolean verifyEmptySigninErrorMessage(){
	   log.debug("Capture the error message when email empty in myaccount page");
		driver.findElement(emptyEmailErrormessage).isDisplayed();
		return true;
	}
	public void clickSearch(){
		log.debug("Click for Search button");
		driver.findElement(searchButton).click();
	}
	
	

}
