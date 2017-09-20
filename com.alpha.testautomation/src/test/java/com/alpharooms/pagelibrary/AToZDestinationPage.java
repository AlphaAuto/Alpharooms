package com.alpharooms.pagelibrary;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AToZDestinationPage {
	static Logger log = Logger.getLogger(AToZDestinationPage.class.getName());
	WebDriver driver;
	By holidayDestHeader = By.xpath("//*[@id='countrylistpage']/div/div/div/div[2]/div[1]/div/h1");
	
	public AToZDestinationPage(WebDriver driver) {
		this.driver =driver;
		// TODO Auto-generated constructor stub
	}

	public String getTitle(){
		log.debug("The title of the current page is - ");
		return driver.getTitle();
	}
	
	public String getText(){
		log.debug("The text of the current page is - ");
		return driver.findElement(holidayDestHeader).getText();
	}
	

}
