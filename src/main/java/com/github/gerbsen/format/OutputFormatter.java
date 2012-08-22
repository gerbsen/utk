package com.github.gerbsen.format;

import java.text.DecimalFormat;


public class OutputFormatter {

	/**
	 * Formats a double by a given format. If the format is null
	 * or format.equals("") == true the the default format #.## is
	 * used. 
	 * 
	 * @see http://download.oracle.com/javase/6/docs/api/java/text/DecimalFormat.html
	 * @param d - the double to be formated
	 * @param format - the format like #.###
	 * @return a string representation of the double in the format
	 */
	public static String format(Double d, String format){
		
//		df.setMinimumIntegerDigits(14);
//	     df.setMinimumFractionDigits(3);
		
		if ( format == null || format.equals("") ) format = "#.##";
		if ( d == null ) return "NULL";
		DecimalFormat df = new DecimalFormat(format);
        return df.format(d);
	}
	
	public static void main(String[] args) {

		System.out.println(format(null, "#.##"));
	}
}
