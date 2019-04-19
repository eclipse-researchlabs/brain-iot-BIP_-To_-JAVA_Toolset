package org.lib;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class RestDriver {





	

	public synchronized static Integer getIntegerValue( String str){
	if(params !=null){
		Integer  X =Integer.parseInt((String)params.get(str));	 
		return X;
	} return 0; }	

	public synchronized static Double getDoubleValue( String str){
	if(params !=null){
		Double  X =Double.parseDouble((String)params.get(str));	 
		return X;
	} return 0.0; }	
	

	
	public static String getText(String target, String value) {
		String answer ="";
		 HttpURLConnection con = null;
        try {
        	target =target+ "?"+value;
            URL myurl = new URL(target);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            StringBuilder content = null;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                answer= content.toString();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				answer="";
			}


        } catch (ProtocolException e) {

			//e.printStackTrace();
		} catch (IOException e1) {

			//e1.printStackTrace();
		} finally {
            
            con.disconnect();
        }
        
        return answer;
	}


	public static Integer getINTEGER_VALUE(String target, String value) {
			String res= getText( target,  value);
		return  Integer.parseInt(res); 
	}
	

	public static Double getDouble_VALUE(String target, String value) {
		String res= getText( target,  value);
			
	return  Double.parseDouble(res); 
	}
	
	
    public static void startDriver( )  {
		try {
	        Server server = new Server(8002);
	        
	        server.setHandler(new MyHandler());

			server.start();
			
	      //  server.join();
	        System.out.println("REST API : OK");
	        System.out.println("PORT NUMBER : 8002" );
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    static class MyHandler extends AbstractHandler {


		@Override
		public void handle(String target,
                Request baseRequest,
                HttpServletRequest request,
                HttpServletResponse response )
				throws IOException, ServletException {
        	// Declare response encoding and types
        	response.setContentType("text/html; charset=utf-8");

        	// Declare response status code
        	response.setStatus(HttpServletResponse.SC_OK);
           // URI requestURI = baseRequest.getRequestURI();
            printRequestInfo(request.getQueryString());
        	// Write back response
        	response.getWriter().println("<h1>Hello World :"+request.getQueryString()+"</h1>");

        	// Inform jetty that this request has now been handled
        	baseRequest.setHandled(true);
                                           
			
		}


    }
    
    private static void printRequestInfo(String query) {

        System.out.println(query);
       params=getQueryMap(query);

    }
    
    static Map<String, String> params;
    
    public static  Map<String, String> getQueryMap(String query)  
    {  
    String[] params = query.split("&");  
    Map<String, String> map = new HashMap<String, String>();  
    for (String param : params)  
    {  String [] p=param.split("=");
        String name = p[0];  
      if(p.length>1)  {String value = p[1];  
        map.put(name, value);
      }  
    }  
    return map;  
    } 
    
    
    
}
