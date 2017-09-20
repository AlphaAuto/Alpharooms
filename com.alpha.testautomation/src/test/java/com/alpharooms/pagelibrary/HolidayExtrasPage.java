package com.alpharooms.pagelibrary;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.alpharooms.testbase.TestBase;

/**
 * @author rameshk
 *
 */
public class HolidayExtrasPage {
	TestBase tpage = new TestBase();
	WebDriver driver;
	static Logger log = Logger.getLogger(HolidayExtrasPage.class.getName());
	public HolidayExtrasPage(WebDriver driver){
		this.driver = driver;
	}
	public String getTitle() {
		log.debug("The title of the current page is - ");
		return driver.getTitle();
	}
	public String clickOnHotelName(String HotelName) {
		
		return tpage.getAllHotels(HotelName);
		
	}
	
	
	

}
