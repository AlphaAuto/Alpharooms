package com.alpharooms.pagelibrary;

import java.util.Calendar;

import java.util.List;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.Test;

public class DatePicker {
	@Test
	public void testDAtePicker() throws Exception {
		// DAte and Time to be set in textbox
		String dateTime = "12/07/2018";
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("http://demos.telerik.com/kendo-ui/datetimepicker/index");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// button to open calendar
		WebElement selectDate = driver.findElement(By
				.id("fromDate"));
		selectDate.click();
		// button to move next in calendar
		WebElement nextLink = driver
				.findElement(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']"));
		// button to click in center of calendar header
		WebElement midLink = driver
				.findElement(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/thead/tr[1]/th[2]"));
		// button to move previous month in calendar
		WebElement previousLink = driver
				.findElement(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[2]/table/thead/tr/th[1]/i"));
		// Split the date time to get only the date part
		String date_dd_MM_yyyy[] = (dateTime.split(" ")[0]).split("/");
		// get the year difference between current year and year to set in
		// calander
		int yearDiff = Integer.parseInt(date_dd_MM_yyyy[2])
				- Calendar.getInstance().get(Calendar.YEAR);
		midLink.click();
		if (yearDiff != 0) {
			// if you have to move next year
			if (yearDiff > 0) {
				for (int i = 0; i < yearDiff; i++) {
					System.out.println("Year Diff->" + i);

					nextLink.click();
				}
			}
			// if you have to move previous year
			else if (yearDiff < 0) {

				for (int i = 0; i < (yearDiff * (-1)); i++) {

					System.out.println("Year Diff->" + i);

					previousLink.click();

				}

			}

		}

		Thread.sleep(1000);

		// Get all months from calendar to select correct one

		List<WebElement> list_AllMonthToBook = driver
				.findElements(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[2]/table/tbody/tr/td/span"));

		list_AllMonthToBook.get(Integer.parseInt(date_dd_MM_yyyy[1]) - 1)
				.click();

		Thread.sleep(1000);

		// get all dates from calendar to select correct one

		List<WebElement> list_AllDateToBook = driver
				.findElements(By
						.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/tbody/tr/td"));

		list_AllDateToBook.get(Integer.parseInt(date_dd_MM_yyyy[0]) - 1)
				.click();

			}

}