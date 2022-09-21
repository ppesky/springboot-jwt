package io.ppesky.auth.config;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class ServletDefaultErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
		errorAttributes.put("timestamp", System.currentTimeMillis());
		
        // sql 관련 exception 이면, message 변경.
        String message = (String) errorAttributes.get("message");
        if(message.contains("java.sql")) {
        	errorAttributes.put("message", "Internal Server Error.");
        } else if(message.length() > 80) {
        	errorAttributes.put("message", "Server Error.");
        }
        
        return errorAttributes;
    }

}
