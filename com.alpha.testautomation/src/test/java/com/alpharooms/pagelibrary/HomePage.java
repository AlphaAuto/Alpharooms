package com.alpharooms.pagelibrary;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.alpharooms.testbase.TestBase;
public class HomePage {
	WebDriver driver;
	static Logger log = Logger.getLogger(HomePage.class.getName());
	By destinationtextBox = By.id("DestinationName");
	By holidayExtras = By.linkText("Holiday Extras");
	By aToZDestinations = By.linkText("A-Z Destinations");
	By FAQ = By.linkText("FAQ");
	By groups = By.linkText("Groups");
	By myAccount = By.linkText("My Account");
	By headerHome = By.xpath(".//*[@id='searchFormForm']/div[2]/div[2]/div[2]/h1");
	By doneBtn = By.xpath(".//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[2]/a");
	By searchbtn = By.id("searchFormSubmit");
	TestBase tpage = new TestBase();
	public HomePage(WebDriver driver) {
		this.driver = driver;
		// TODO Auto-generated constructor stub
	}
	// Title of the Current Web page
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
	public boolean verifyAlllinksAvaialbleinAlphaHomePage() {
		log.debug("Capture the error message when email empty in myaccount page");
		//driver.findElement(holidayExtras).isDisplayed();
		driver.findElement(aToZDestinations).isDisplayed();
		driver.findElement(FAQ).isDisplayed();
		driver.findElement(groups).isDisplayed();
		//driver.findElement(myAccount).isDisplayed();
		return true;
	}
public String getTextofHeader(){
	return driver.findElement(headerHome).getText();
}
	// destination textbox
public void clickDestination(){
	driver.findElement(destinationtextBox).click();
}
public void selectDestinationfrmPopUp(String destination){
	 
	 tpage.getAllDestinations(destination);
}
	public void destination(String value){
		driver.findElement(destinationtextBox).sendKeys(value);
	}
	public HolidayExtrasPage NavigatetoHolidayExtras() {
		log.debug("Click for Holiday Extras link");
		driver.findElement(holidayExtras).click();
		return new HolidayExtrasPage(driver);
	}

	public AToZDestinationPage NavigateToAToZDestinations() {
		log.debug("Click for A toZ destination  link");
		driver.findElement(aToZDestinations).click();
		return new AToZDestinationPage(driver);
	}

	public FAQPage NavigateToFAQ() {
		log.debug("Click for FAQ link");
		driver.findElement(FAQ).click();
		return new FAQPage(driver);
	}

	public GroupsPage NavigateToGroups() {
		log.debug("Click for Groups link");
		driver.findElement(groups).click();
		return new GroupsPage(driver);
	}

	public MyAccountPage NavigateToMyAccount() {
		log.debug("Click for MyAccount link");
		driver.findElement(myAccount).click();
		return new MyAccountPage(driver);
	}
	
	public void selectFromDate(String day, String month, String year) throws InterruptedException {
		// TODO Auto-generated method stub
		tpage.dateSelection(day, month, year);
	}
	public void selectNumberOfNights(String nights){
		try {
			tpage.setNumberOfNights(nights);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setAdultCount(String Adult){
		tpage.selectAdultS(Adult);
	}
	
	public void clickDoneButton(){
		driver.findElement(doneBtn).click();
	}
	public AccommodationresultsPage clickSearchButton(){
		driver.findElement(searchbtn).click();
		return new AccommodationresultsPage(driver);
	}
	public void clickOnRoomsAndGuest(){
		try {
			Thread.sleep(1000);
			tpage.ClickOnRoomAndGuests();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
