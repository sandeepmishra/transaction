package com.sapient.feecalculator.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionUtils {

	public static Double parseMarketValue(String marketValue) {
		try{// TODO Auto-generated method stub
			return Double.parseDouble(marketValue);
		}catch(Exception ex){
			return (double) 0;
		}
	}

	public static Boolean getPriority(String priority) {
		if(priority!= null){
			priority = priority.trim();
			if(priority.equals("Y")||priority.equals("y")){
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}

	public static Date parseDate(String date) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date convertedCurrentDate = sdf.parse(date);
			return convertedCurrentDate;
		}catch(Exception  ex){
			return null;
		}
	}

}
