package com.alpharooms.testscripts;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alpharooms.testbase.TestBase;

public class HandlingCalendar extends TestBase {

	@BeforeClass
	public void setUp() throws IOException {
		init();
	}
@Test
public void getNights() throws InterruptedException{
	Thread.sleep(2000);
	//Select the number of nights
 setNumberOfNights("6 Nights");
	// clcik on rooms and guests
	ClickOnRoomAndGuests();
	//Set Adult Count
	selectAdultS("4");
	
}
	@Test(enabled=false)
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

		// click Right arrow link
		WebElement rightArrow = driver
				.findElement(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/thead/tr[1]/th[3]/i"));

		// click on Month Year link
		monthYear.click();

		if (yearSection.getText().trim().equals(year)) {
			Thread.sleep(600);
			System.out.println("Perfect year Matched");
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
					System.out.println("Get all Years : "+ currentyear.getText());
					System.out.println("Get all Years with attribute:  "+ currentyear.getAttribute("value"));
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

				System.out.println("Length of the all Dates - >"
						+ getAllDates.size());
				for (WebElement allDays : getAllDates) {
					System.out.println("Get all days with attribute:  "+ allDays.getAttribute("value"));
					System.out.println("Get all days with text  "+ allDays.getText());
					if (allDays.getText().trim().equals(dates)) {
						Thread.sleep(600);
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
			System.out.println("Length of the all months - >"
					+ getAllMonths.size());
			for (WebElement allMonths : getAllMonths) {
				System.out.print("All months from Calendar - > " + mon);
				if (allMonths.getText().trim().equals(CurrentMonth)) {
					System.out.println("Get all months with text:  "+ allMonths.getText());
					System.out.println("Get all months with attribute:  "+ allMonths.getAttribute("value"));
					allMonths.click();
					break;
				}
			}

		}
	}

}
