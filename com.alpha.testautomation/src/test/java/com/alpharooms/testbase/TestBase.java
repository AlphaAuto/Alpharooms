package com.alpharooms.testbase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javafx.util.StringConverter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.WebElementHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	public static Properties Repository = new Properties();
	public File f;
	public FileInputStream FI;
	public static WebDriver driver;
	public String startTime;
	public static int indexSI = 1;
	public static ExtentReports extent;
	public static ExtentTest test;
	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir")
				+ "/src/test/java/com/alpharooms/testReport/test"
				+ formater.format(calendar.getTime()) + ".html", false);
	}

	public void init() throws IOException {
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		loadPropertiesFile();
		selectBrowser(Repository.getProperty("browser"));
		driver.get(Repository.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void loadPropertiesFile() throws IOException {
		f = new File(
				System.getProperty("user.dir")
				+ "//src//test//java//com//alpharooms//config//config.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);
	}

	@Parameters("browser")
	public void selectBrowser(String browserName) throws MalformedURLException {
		if (System.getProperty("os.name").contains("Window")) {
			if (browserName.equalsIgnoreCase("firefox")) {
				// https://github.com/mozilla/geckodriver/releases
				System.out.println(System.getProperty("user.dir"));
				String usedProxy = "52.16.27.130:3128";
				Proxy proxy = new org.openqa.selenium.Proxy();
				proxy.setHttpProxy(usedProxy).setFtpProxy(usedProxy)
				.setSslProxy(usedProxy);
				DesiredCapabilities cap =new DesiredCapabilities();
				cap.setCapability("proxy", proxy);
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver(cap);
			} else if (browserName.equalsIgnoreCase("chrome")) 
			{
				String usedProxy = "52.16.27.130:3128";
				Proxy proxy = new org.openqa.selenium.Proxy();
				proxy.setHttpProxy(usedProxy).setFtpProxy(usedProxy)
				.setSslProxy(usedProxy);
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir")
						+ "/drivers/chromedriver.exe");
				DesiredCapabilities cap =new DesiredCapabilities();
				cap.setCapability("proxy", proxy);
				driver = new ChromeDriver(cap);
			}else if (browserName.equalsIgnoreCase("IE")) 
			{
				// https://chromedriver.storage.googleapis.com/index.html
				// DesiredCapabilities capabilities =
				// DesiredCapabilities.chrome();
				// Add the WebDriver proxy capability.
			
							System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir")
						+ "/drivers/IEDriverServer.exe");
				String usedProxy = "52.16.27.130:3128";

				Proxy proxy = new org.openqa.selenium.Proxy();
				proxy.setHttpProxy(usedProxy).setFtpProxy(usedProxy)
				.setSslProxy(usedProxy);
				DesiredCapabilities cap =new DesiredCapabilities();
				cap.setCapability("proxy", proxy);
				driver = new InternetExplorerDriver(cap);
			}
		} else if (System.getProperty("os.name").contains("Mac")) {
			System.out.println(System.getProperty("os.name"));
			if (browserName.equalsIgnoreCase("firefox")) {
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.firefox.marionette",
						System.getProperty("user.dir") + "/drivers/geckodriver");
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir")
						+ "/drivers/chromedriver");
				driver = new ChromeDriver();
			}
		}
	}

	public void expliciteWait(WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForPageToLoad(long timeOutInSeconds) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		try {
			System.out.println("Waiting for page to load...");
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out
			.println("Timeout waiting for Page Load Request to complete after "
					+ timeOutInSeconds + " seconds");
			Assert.assertFalse(true,
					"Timeout waiting for Page Load Request to complete.");

		}
	}

	public String getAllHotels(String selectHotel){
		String hotelNames =null;
	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			//WebElement allDivs = driver.findElement(By.xpath(".//*[@id='accommodationresults']/div[12]/div/div[2]/div[3]/div[7]/div[3]/div/div"));	
			for (int i = 0; i<= 20; i++) {
				List<WebElement> options = driver.findElements(By.xpath(".//*[@id='accommodationresults']/div[12]/div/div[2]/div[3]/div[7]/div[3]/div/div["+i+"]/div[1]/div[2]/div[1]/h3/a"));
				for(WebElement allItems : options){
					hotelNames   = allItems.getText();
				    System.out.println(hotelNames);
				
						if (allItems.getText().trim().equals(selectHotel)) {
							
						allItems.click();
						break;
						}
				}
						//return hotelNames;
					}
					//return hotelNames;
			//return options;
			return hotelNames;
		
			
		
	}
	// select the Number of Guests and Rooms

	public void ClickOnRoomAndGuests() {

		WebElement element = driver.findElement(By
				.xpath(".//*[@id='rooms-dropdownMenu']/button"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

	}

	public void addRooms() {
		WebElement addRoom = driver
				.findElement(By
						.xpath(".//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[1]/div[2]/div[1]/i"));
		addRoom.click();
	}

	public void selectChildren(String numofChild) {
		WebElement element = driver
				.findElement(By
						.xpath(".//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[1]/div[1]/div/div/div/div/div[3]/div/div[2]/select"));
		List<WebElement> elements = element.findElements(By.tagName("option"));
		for (WebElement options : elements) {
			if (options.getText().trim().equals(numofChild)) {
				options.click();
			}
		}


	}
	public void getAllChildDivs(){

	}
	/*public void selectSingleChildAge(String childCount, String childAge){
		for (int j = 1; j < 4; j++) {

		List<WebElement> childBox = driver.findElements(By.xpath(".//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[1]/div[1]/div/div/div/div/div[4]/div/div[2]/div/div["+j+"]/select"));
		int sizeOfDivs = childBox.size();
		String conver = String.valueOf(sizeOfDivs);
		//int conBox = Integer.parseInt(sizeOfDivs);
		if (conver ==childCount) {

		}
		List<WebElement> selectChildAge = childBox.findElements(By.tagName("option"));
		for(WebElement options : selectChildAge){
			if (options.getText().trim().equals(childAge)) {
				options.click();
			}
		}*/
	//	}
	//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[1]/div[1]/div/div/div/div/div[4]/div/div[2]/div/div
	public void getAllChildrenSection(String number){
		WebElement getAllChildCount = driver
				.findElement(By
						.xpath(".//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[1]/div[1]/div/div/div[2]/div/div[4]/div/div[2]/div"));

		for (int i = 0; i < 4; i++) {
			List<WebElement> getChildCount = getAllChildCount
					.findElements(By
							.xpath(".//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[1]/div[1]/div/div/div[2]/div/div[4]/div/div[2]/div/div["+i+"]"));           
			for(WebElement options: getChildCount){
				if (options.getText().trim().equals(number)) {
					String ages = options.getText();
					int actualAge = Integer.parseInt(ages);
					int parseNumber = Integer.parseInt(number);
					if (parseNumber >0 && parseNumber <2) {
						getChildCount.get(0).click();
					} else if (parseNumber >1 && parseNumber <3) {

					}
				}	
			}
		}
	}	
	public void getAllDestinations(String destination){

		WebElement popularDest = driver.findElement(By.xpath(".//*[@id='seach-box-wrapper']/div[1]/div[8]"));
		List<WebElement> allDestinations = popularDest.findElements(By.tagName("a"));
		for(WebElement getDestination : allDestinations){
			if (getDestination.getText().trim().equals(destination)) {
				getDestination.click();
			}
		}
	}
	public void selectAdultS(String number) {
		WebElement element = driver
				.findElement(By
						.xpath("//*[@id='seach-box-wrapper']/div[1]/div[10]/div[2]/div[1]/div[1]/div/div/div/div/div[3]/div/div[1]/select"));
		List<WebElement> elements = element.findElements(By.tagName("option"));
		for (WebElement options : elements) {
			if (options.getText().trim().equals(number)) {
				options.click();
			}
		}

	}

	// Get Number of Nights
	public void setNumberOfNights(String nights) throws InterruptedException {
		Thread.sleep(1000);
		WebElement element = driver
				.findElement(By
						.xpath(".//*[@id='mbl-dropdownMenu']/button"));// btn
		// btn-default
		// dropdown-toggle
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		// List<WebElement> getXpathcount =
		// driver.findElements(By.xpath("//*[@id='dropdownMenu1']/button"));
		// System.out.println(getXpathcount.size());
		// getXpathcount.get(0).click();
		// element.click();
		List<WebElement> movetoDropDown = driver.findElements(By
				.xpath(".//*[@id='mbl-dropdownMenu']/div/ul/li/a"));
		System.out.println(movetoDropDown.size());
		for (WebElement options : movetoDropDown) {
			System.out.println(options.getText());

			if (options.getText().trim().equals(nights)) {
				options.click();
			}
		}
	}

	public WebElement modelPopup() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement element = wait.until(ExpectedConditions
				.elementToBeClickable(By.id("channelModal")));
		return element;
		// return driver.findElement(By.id("channelModal"));
	}

	public void setChannelPopUp(String channelLink) throws InterruptedException {
		String parent_win = driver.getWindowHandle();
		for (String child_win : driver.getWindowHandles()) {
			driver.switchTo().window(child_win);
			Thread.sleep(1000);
			System.out.println(driver.getTitle());
			WebElement channellink = driver.findElement(By
					.linkText(channelLink));
			System.out.println(driver.getTitle());
			channellink.click();
		}
		driver.switchTo().window(parent_win);

	}

	public void clickWhenReady(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();

	}

	public WebElement fluentWait(final WebElement webElement, int timeinsec) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeinsec, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return webElement;
			}
		});

		return element;
	};

	public WebElement getWhenVisible(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(locator));
		return element;

	}

	public void waitFor(int sec) throws InterruptedException {
		Thread.sleep(sec * 1000);
	}

	public void selectTab(String tabName) {

		WebElement searchform = driver.findElement(By.id("searchform"));
		for (int i = 1; i < 3; i++) {
			// List<WebElement> tabs =
			// searchform.findElements(By.xpath("//*[@class='search-box-tabs incFlightOnly']/span["+i+"]"));
			List<WebElement> tabs = searchform.findElements(By
					.xpath("//*[@id='searchform']/div[1]/span[" + i
							+ "]/label/span"));

			for (WebElement options : tabs) {
				System.out.println("This is with text : " + options.getText());
				System.out.println("This is with getAttribute : "
						+ options.getAttribute("value"));
				System.out.println("This is with InnerText : "
						+ options.getAttribute("innerText"));
				if (options.getText().contains(tabName)) {
					if (options.isEnabled()) {
						options.click();
					} else {

					}
				}
			}
		}

	}

	public void getScreenShot(String fileName) throws IOException {
		File outputFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(outputFile, new File(System.getProperty("user.dir")
				+ "//src//test//java//com//alpharooms//screenshots//"
				+ fileName + ".jpg"));
	}

	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fm = new SimpleDateFormat();
		return fm.format(cal.getTime());
	}

	public void dateSelection(String day, String month, String year)
			throws InterruptedException {
		Thread.sleep(1000);
		// String dateTime = "12/Sep/2018";
		// button to open calendar
		WebElement selectDate = driver.findElement(By.id("fromDate"));
		selectDate.click();
		// Click on Month Year section
		WebElement monthYear = driver
				.findElement(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/thead/tr[1]/th[2]"));
		// Click on Year Section
		WebElement yearSection = driver
				.findElement(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[2]/table/thead/tr/th[2]"));
		// button to move previous month in calendar

		/*	// click Right arrow link
		WebElement rightArrow = driver
				.findElement(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/thead/tr[1]/th[3]/i"));
		 */
		// click on Month Year link
		monthYear.click();

		if (yearSection.getText().trim().equals(year)) {
			Thread.sleep(600);
			//System.out.println("Perfect year Matched");
			Thread.sleep(600);
			setMonth(month);
			Thread.sleep(600);
			setDate(day);
		} else

			if (!yearSection.getText().trim().equals(year)) {
				yearSection.click();
				Thread.sleep(600);
				setYear(year);
				Thread.sleep(600);
				setMonth(month);
				Thread.sleep(600);
				setDate(day);
			}
	}
	public static void setYear(String year) throws InterruptedException {
		for (int j = 0; j <= 12; j++) {

			// get all Years from Year menu
			List<WebElement> getYears = driver
					.findElements(By
							.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[3]/table/tbody/tr/td/span["
									+ j + "]"));
			for (WebElement currentyear : getYears) {
				if (currentyear.getText().trim().equals(year)) {
					/*System.out.println("Get all Years : "+ currentyear.getText());
					System.out.println("Get all Years with attribute:  "+ currentyear.getAttribute("value"));*/
					Thread.sleep(600);
					currentyear.click();
					break;
				}
			}
		}
	}

	public static void setDate(String dates) throws InterruptedException {
		for (int day = 1; day <= 5; day++) {
			for (int i = 1; i <=7; i++) {
				List<WebElement> getAllDates = driver
						.findElements(By
								.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/tbody/tr["+day+"]/td["+i+"]"));

				/*System.out.println("Length of the all Dates - >"
						+ getAllDates.size());*/
				for (WebElement allDays : getAllDates) {
				/*	System.out.println("Get all days with attribute:  "+ allDays.getAttribute("value"));
					System.out.println("Get all days with text  "+ allDays.getText());*/
					if (allDays.getText().trim().equals(dates)) {
						Thread.sleep(200);
						allDays.click();
						break;
					}
				}
			}
		}
	}

	public static void setMonth(String CurrentMonth) {

		for (int mon = 1; mon <= 12; mon++) {
			// get all months
			List<WebElement> getAllMonths = driver
					.findElements(By
							.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[2]/table/tbody/tr/td/span["
									+ mon + "]"));
		/*	System.out.println("Length of the all months - >"
					+ getAllMonths.size());*/
			for (WebElement allMonths : getAllMonths) {
				System.out.print("All months from Calendar - > " + mon);
				if (allMonths.getText().trim().equals(CurrentMonth)) {
				/*	System.out.println("Get all months with text:  "+ allMonths.getText());
					System.out.println("Get all months with attribute:  "+ allMonths.getAttribute("value"));*/
					allMonths.click();
					break;
				}
			}

		}
	}
	public static void updateResult(int indexSI, String testCaseName,
			String testCaseStatus, String scriptName) throws IOException {
		String startDateTime = new SimpleDateFormat("MM-dd-yyyy_HH-ss")
		.format(new GregorianCalendar().getTime());

		String userDirector = System.getProperty("user.dir");

		String resultFile = userDirector
				+ "//src//test//java//com//alpharooms//report//TestHtmlReport.html";

		File file = new File(resultFile);
		System.out.println(file.exists());

		if (!file.exists()) {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<html>" + "\n");
			bw.write("<head><title>" + "Test execution report" + "</title>"
					+ "\n");
			bw.write("</head>" + "\n");
			bw.write("<body>");
			bw.write("<font face='Tahoma'size='2'>" + "\n");
			bw.write("<u><h1 align='center'>" + "Test execution report"
					+ "</h1></u>" + "\n");
			bw.flush();
			bw.close();
		}
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(file, true));
		if (indexSI == 1) {
			bw1.write("<table align='center' border='0' width='70%' height='10'>");
			bw1.write("<tr><td width='70%' </td></tr>");
			bw1.write("<table align='center' border='1' width='70%' height='47'>");
			bw1.write("<tr>");
			bw1.write("<td colspan='2' align='center'><b><font color='#000000' face='Tahoma' size='2'>ScriptName :&nbsp;&nbsp;&nbsp;</font><font color='#0000FF'' face='Tahoma' size='2'>"
					+ scriptName + " </font></b></td>");
			bw1.write("<td colspan='1' align='left'><b><font color='#000000' face='Tahoma' size='1'>Start Time :&nbsp;</font></b><font color='#0000FF'' face='Tahoma' size='1'>"
					+ startDateTime + " </font></td>");
			bw1.write("</tr>");
			bw1.write("</tr>");
			bw1.write("<td  bgcolor='#CCCCFF' align='center'><b><font color='#000000' face='Tahoma' size='2'>S.No</font></b></td>");
			bw1.write("<td  bgcolor='#CCCCFF' align='left'><b><font color='#000000' face='Tahoma' size='2'>Test case ID : Test case Description </font></b></td>");

			bw1.write("<td  bgcolor='#CCCCFF' align='center'><b><font color='#000000' face='Tahoma' size='2'>Result </font></b></td>");
			bw1.write("</tr>");
		}
		bw1.write("<tr>" + "\n");
		bw1.write("<td bgcolor='#FFFFDC'align='Center'><font color='#000000' face='Tahoma' size='2'>"
				+ indexSI + "</font></td>" + "\n");
		bw1.write("<td  bgcolor='#FFFFDC' valign='middle' align='left'><b><font color='#000000' face='Tahoma' size='2'>"
				+ testCaseName + "</font></b></td>" + "\n");
		if (testCaseStatus == "Pass") {
			bw1.write("<td  bgcolor='#FFFFDC' valign='middle' align='center'><b><font color='Green' face='Tahoma' size='2'>"
					+ testCaseStatus + "</font></b></td>" + "\n");
		} else {
			bw1.write("<td  bgcolor='#FFFFDC' valign='middle' align='center'><b><font color='red' face='Tahoma' size='2'>"
					+ testCaseStatus + "</font></b></td>" + "\n");
		}
		bw1.write("</tr>" + "\n");
		bw1.write("</body>" + "\n");
		bw1.write("</html>");
		bw1.flush();
		bw1.close();

	}

	public void getresult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP,
					result.getName() + " test is skipped and skip reason is:-"
							+ result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed"
					+ result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}

	@AfterMethod()
	public void afterMethod(ITestResult result) {
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}

	public void closeBrowser() {
		extent.endTest(test);
		extent.flush();
	}

	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir"))
			.getAbsolutePath()
			+ "/src/main/java/com/test/automation/uiAutomation/screenshot/";
			destFile = new File((String) reportDirectory + fileName + "_"
					+ formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath()
					+ "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
}
