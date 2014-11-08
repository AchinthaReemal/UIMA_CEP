package com.example.mediacompanionmini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class DownloadPostAction {
	
	public static void postID(String id,String username){
		
		try {
	        URL url = new URL("http://10.0.2.2/MediaCompanion/SLIM_API/index.php/remoteinitiation");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	        BufferedReader reader = null;
	                
	        
	            writer.write("username="+username+"&selectedid="+id);
	            writer.flush();
	           
	            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String[] infoArray = null;
                String line;
                
                while ((line = reader.readLine()) != null){
                    System.out.println(line);                            
                }
                         writer.close();

	    } catch (IOException ex) {
	        
	    }  

		
	}
}
