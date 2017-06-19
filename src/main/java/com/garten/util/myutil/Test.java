package com.garten.util.myutil;

import java.sql.Timestamp;
import java.text.DecimalFormat;

public  class Test {
	public static String getDecimal(Double number){
		DecimalFormat abc=new DecimalFormat("0.00");
				return abc.format(number);
	}
	
	
	
	
	

	
}
