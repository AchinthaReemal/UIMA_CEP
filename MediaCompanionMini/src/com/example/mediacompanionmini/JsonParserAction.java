package com.example.mediacompanionmini;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParserAction {
	
	public static String getJSON(String username){
		
		String json="";
		try{
			InputStream inStream;  
		    DataInputStream dataStream;
		    String uri = "", response="";
		    String restURL = "http://10.0.2.2/MediaCompanion/SLIM_API/index.php/availabledownloads/"+username;
		    URL url = new URL(restURL);
		    inStream = url.openStream(); 
		    dataStream  = new DataInputStream(inStream);
		    response=dataStream.readLine();	
		    json=response;
		}
		catch (MalformedURLException ex) {
	        
	    } catch (IOException ex) {
	        
	    }
    
    return json;
		
	}
	
	public static ArrayList<HashMap<String,String>> getCompleteList(String username){
		
		ArrayList<HashMap<String,String>> totalList = new ArrayList<HashMap<String, String>>();
		
		 try {
	            		String settings[];;
	                    InputStream inStream;  
	                    DataInputStream dataStream;
	                    String uri = "", response="";
	                    String restURL = "http://10.0.2.2/MediaCompanion/SLIM_API/index.php/availabledownloads/"+username;
	                    URL url = new URL(restURL);
	                    
	                    inStream = url.openStream(); 
	                    dataStream  = new DataInputStream(inStream);            
	                    
	                    response=dataStream.readLine();
	                    response="{ available:"+response+"}";
	                    
	                    JSONObject jsonObj = new JSONObject(response);
	                    JSONArray single = jsonObj.getJSONArray("available");
	                                        
	                    for (int i = 0; i < single.length(); i++)
	                    {
	                        HashMap<String,String> downloadsSet = new HashMap<String, String>();
	                        downloadsSet.put("tvshow", single.getJSONObject(i).getString("tvshow")); 
	                        downloadsSet.put("id", single.getJSONObject(i).getString("id")); 
	                        totalList.add(downloadsSet);
	                    }
	                    
	                    for(int i=0;i<totalList.size();i++)
	                        System.out.println(totalList.get(i).get("tvshow"));
	                    
	                    inStream.close();
	                    dataStream.close();
	                                     
	                    
	                    
	        } catch (MalformedURLException ex) {
	            
	        } catch (IOException ex) {
	            
	        } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return totalList;
		
	}

}
