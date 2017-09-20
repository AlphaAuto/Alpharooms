package com.alpharooms.testscripts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.alpharooms.testbase.TestBase;

import freemarker.template.SimpleDate;

public class CurrentDate extends TestBase{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 String selectDate = "10/12/2017";
		 
		 Date d = new Date(selectDate);
		 SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		 String date = dt.format(d);
		 System.out.println(date);
		 
		 String[] split = date.split("/");
		 System.out.println();
		 String month = split[1]+" "+split[2];
		 System.out.println(month);
		 
		 WebElement selectDate1 = driver.findElement(By
					.id("fromDate"));
			selectDate1.click();
			WebElement calendar = driver.findElement(By.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']"));
			while(true){
				try {
					driver.findElement(By.xpath("*[contains(text(),' "+month+" ')]")).isDisplayed();
					driver.findElement(By.xpath("")).click();
					for (int row = 1; row < 8; row++) {
						
						for (int j = 1; j < 8; j++) {
							if(calendar.findElement(By.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/tbody/tr["+row+"]/td["+j+"]")).isEnabled()){
								
							}
							
						}
					}

                     break;
				} catch (Exception e) {
					// TODO: handle exception
					WebElement nextLink = driver
							.findElement(By
									.xpath("//*[@class='datepicker datepicker-dropdown dropdown-menu']/div[1]/table/thead/tr[1]/th[3]/i"));
				}
			}
	}

}
