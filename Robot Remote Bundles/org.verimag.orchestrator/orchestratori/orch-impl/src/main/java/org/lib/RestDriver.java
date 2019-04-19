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

		//	e.printStackTrace();
		} catch (IOException e1) {

		//	e1.printStackTrace();
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
        HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(8000), 0);
	        server.createContext("/robot", new MyHandler());
	        server.setExecutor(null); // creates a default executor
	        server.start();
	        System.out.println("REST API : OK");
	        System.out.println("PORT NUMBER : 8000" );
		} catch (IOException e) {

			e.printStackTrace();
		}

    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            URI requestURI = t.getRequestURI();
            printRequestInfo(t);
            
            
            
            String response = "This is the response " +requestURI;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
          //  System.out.println(t.g);
            os.write(response.getBytes());
            os.close();
        }
    }
    
    private static void printRequestInfo(HttpExchange exchange) {

        URI requestURI = exchange.getRequestURI();

        String query = requestURI.getQuery();
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
