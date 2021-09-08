package com.trackandtrail.util;

public class StringUtils {

	public static String padLeftZeros(String inputString, int length) {
	    if (inputString.length() >= length) {
	        return inputString;
	    }
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < length - inputString.length()) {
	        sb.append('0');
	    }
	    sb.append(inputString);

	    return sb.toString();
	}




	 public  static String getAlphaNumericString() {
		   
		   String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+ "0123456789"; 
			StringBuilder sb = new StringBuilder(10); 
			for (int i = 0; i < 10; i++) { 
				int index = (int)(AlphaNumericString.length()* Math.random());
				sb.append(AlphaNumericString.charAt(index)); 
			}
			return sb.toString();
	   }
	 
	 
	 public static class StringUtil {

		 public static String padLeftZeros(String inputString, int length) {
			 if (inputString.length() >= length) {
		            return inputString;
		        }
		        return String
		          .format("%1$" + length + "s", inputString)
		          .replace(' ', '0');
		    }
		
	}

}
