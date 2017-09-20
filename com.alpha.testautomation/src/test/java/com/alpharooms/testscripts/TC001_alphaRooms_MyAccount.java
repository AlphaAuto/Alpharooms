package com.alpharooms.testscripts;

import java.io.IOException;

import org.openqa.selenium.Proxy;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.alpharooms.pagelibrary.AToZDestinationPage;
import com.alpharooms.pagelibrary.AccommodationresultsPage;
import com.alpharooms.pagelibrary.FAQPage;
import com.alpharooms.pagelibrary.HolidayExtrasPage;
import com.alpharooms.pagelibrary.HomePage;
import com.alpharooms.testbase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author rameshk
 */
public class TC001_alphaRooms_MyAccount extends TestBase {
	HomePage homePage;

	@Test(enabled = false)
	public void selectTabsFromAlpha() throws InterruptedException {
		test.log(LogStatus.PASS,
				"alpha home Page will be open in Firefox Browser");
		// Create an instance for homePage
		String channelLink = "United Kingdom (£)";

		if ((modelPopup().isDisplayed())) {
			setChannelPopUp(channelLink);
		}
		Thread.sleep(2000);
		selectTab("FLIGHTS");
	}

	@BeforeClass
	public void setUP() throws IOException {

		// 52.16.27.130 3128
		init();
		// starting test
		test = extent.startTest("Alpharooms Test",
				"This test will verify alpharoomsHomePage");
		test.log(LogStatus.PASS, "Basic set up for test is done");
	}

	@Test(priority = 0)
	public void alpharoomsHomePage() throws InterruptedException, IOException {
		try {
		//	String homePagetitle = "Hotel Deals & Discount Holidays 2017 . Amazing four star hotel offers &amp; all inclusive holidays.";
			String hotelSearchPageTitle = "Cheap hotels and apartments, low cost flights, and cheap holidays worldwide with alpharooms.com";
		//	String title = "Cheap holidays worldwide - Low cost flights, discount hotels & great holiday deals";
		//	String header = "Book your perfect holiday";
			String HotelName ="Club Hotel Aguamarina";
			String destination = "Mallorca (Majorca), Spain";
			String channelLink = "United Kingdom (£)";
			String day = "12";
			String mon = "Nov";
			String year = "2017";
			String nights = "4 Nights";
			String adults = "3";
			String propCount="";
			String holidayExtrasPageTitle="";
		/*	if ((modelPopup().isDisplayed())) {
				setChannelPopUp(channelLink);
			} else{*/
			test.log(LogStatus.PASS,
					"alpha home Page will be open in Firefox Browser");
			// Create an instance for homePage
			homePage = new HomePage(driver);

			test.log(LogStatus.PASS, "Title of Alpha room home page   - > "
					+ driver.getTitle());
		
			test.log(LogStatus.PASS,
					"verify all the links of Alpharooms homepage");
		
			test.log(LogStatus.PASS, "Enter the destination as : "
					+ destination);
			homePage.clickDestination();
			homePage.selectDestinationfrmPopUp("Menorca");
			// homePage.destination(destination);
			test.log(LogStatus.PASS, "select the start date as : " + day + " "
					+ mon + " " + year);
			homePage.selectFromDate("12", "Nov", "2017");
			// Choose Number of nIghts
			test.log(LogStatus.PASS, "select the NUmberof nights : " + nights);
			homePage.selectNumberOfNights("4 Nights");
			test.log(LogStatus.PASS, "click on Room and Guest search ");
			homePage.clickOnRoomsAndGuest();
			// Choose Number of Rooms or Guests

			test.log(LogStatus.PASS, "Select the Adults" + adults);
			homePage.setAdultCount("3");
			test.log(LogStatus.PASS, "Click on Done button ");
			homePage.clickDoneButton();
			// Click on Search button
			test.log(LogStatus.PASS, "Click Search button");
			AccommodationresultsPage accomPage = homePage.clickSearchButton();
			test.log(LogStatus.PASS,
					"I am afraid i have to wait for some time as next page is loading !, Thanks you so much");
			waitForPageToLoad(60);
			test.log(LogStatus.PASS,
					"Hey ! I am the Hotel search Page Title , here it is my title   : "
							+ driver.getTitle());
	     // accomPage.waitForElementPresent();		
			Assert.assertEquals(accomPage.getTitle(), hotelSearchPageTitle);
			System.out.println(accomPage.getHotelProperties());
	    // test.log(LogStatus.PASS, );
			
				HolidayExtrasPage hPage = accomPage.getAllHotelResults(HotelName);
			test.log(LogStatus.PASS, hPage.getTitle());
			
					
			Thread.sleep(3000);
		} catch (Exception e) {
		}
		extent.endTest(test);
		extent.flush();
		driver.close();
	}

	@AfterClass
	public void quitBrowser() {
				
		driver.quit();
	}

}
