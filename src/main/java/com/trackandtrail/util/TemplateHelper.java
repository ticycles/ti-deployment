package com.trackandtrail.util;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;


public class TemplateHelper {

	
	public static String mergeTemplateWithContent(String templateString, Map<String,String> templateVariables) {
				
		// Initialize StringSubstitutor instance with value map
        StringSubstitutor  stringSubstitutor = new StringSubstitutor(templateVariables);

		// replace value map to template string
        return stringSubstitutor.replace(templateString);
		
	}
	
	
}
