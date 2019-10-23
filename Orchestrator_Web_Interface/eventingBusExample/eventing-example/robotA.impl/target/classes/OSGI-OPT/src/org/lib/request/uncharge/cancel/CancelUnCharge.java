package org.lib.request.uncharge.cancel;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CancelUnCharge {

	public static void main(String[] a) 
    { 
  

		CancelUnCharge org = new CancelUnCharge(); 
  
	 ObjectMapper mapper = new ObjectMapper();
      
		try {
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

			
			//Convert object to JSON string
			String 			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(org);
			System.out.println(jsonInString);
			
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    } 
	
	Cancelheader header =new Cancelheader();

	public Cancelheader getHeader() {
		return header;
	}

	public void setHeader(Cancelheader header) {
		this.header = header;
	}
	
}
