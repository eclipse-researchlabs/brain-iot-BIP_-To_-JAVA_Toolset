package org.lib.reply.chargecomponent.cancel;

import java.io.IOException;

import org.lib.reply.gotolocation.AddReply;
import org.lib.reply.gotolocation.Result;
import org.lib.reply.gotolocation.State;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CancelReply {

	
	public static void main(String[] a) 
    { 
  

		CancelReply org = new CancelReply(); 
  
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
	
	String _format ="ros";
	
	State state =new State();
	
	Result result =new Result();

	public String get_format() {
		return _format;
	}

	public void set_format(String _format) {
		this._format = _format;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}
