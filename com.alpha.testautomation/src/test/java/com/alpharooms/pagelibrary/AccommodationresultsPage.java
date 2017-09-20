package com.alpharooms.pagelibrary;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.alpharooms.testbase.TestBase;

public class AccommodationresultsPage {

	WebDriver driver;
	static Logger log = Logger.getLogger(AccommodationresultsPage.class.getName());
	By propertiestxt = By.xpath(".//*[@id='accommodationresults']/div[12]/div/div[2]/div[3]/div[5]/div[1]/h2[1]");
   TestBase 	tpage= new TestBase();
	//*[@id='accommodationresults']/div[12]/div/div[2]/div[3]/div[5]/div[1]/h2[1]
	public AccommodationresultsPage(WebDriver driver){
		this.driver = driver;
	}
	public String getTitle() {
		log.debug("The title of the current page is - ");
		return driver.getTitle();
	}
	// Wait for certain element to be loaded on the web page
	public void waitFor(ExpectedCondition<WebElement> condition,
			Integer timtOutinSeconds) {
		timtOutinSeconds = timtOutinSeconds != null ? timtOutinSeconds : 30;
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(condition);
	}
	public void waitForElementPresent(){
		tpage.getWhenVisible(propertiestxt, 20)	;
	}
	public HolidayExtrasPage getAllHotelResults(String selectHotel){
		tpage.getAllHotels(selectHotel);
		return new HolidayExtrasPage(driver);
	}
//	public boolean get
	public String getHotelProperties( )
	{

		WebElement element = driver.findElement(propertiestxt);
		String options = element.getText();
		String[] values = options.split(" ");
		System.out.println(values[0]);
        //return options;		
		return options;
 
	}
	
}
