package com.example.mediacompanionmini;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class LoginAction {
	
	
	public boolean login(String username, String password) throws MalformedURLException, IOException{
		
		
		String settings[];
		boolean status=false;
        InputStream inStream;  
        DataInputStream dataStream;
        String uri = "", response="";
        
        String restURL = "http://10.0.2.2/MediaCompanion/SLIM_API/index.php/login/"+username;
        URL url = new URL(restURL);
        inStream = url.openStream(); 
        dataStream  = new DataInputStream(inStream);  
        
        response=dataStream.readLine();
        response = response.replace("[\"", "").replace("\"]", "");
		
        String encryptedpw = MD5HashingAction.MD5(password);
        
        if(encryptedpw.equals(response)){
        	status=true;
        	System.out.println("Matched");
        }else{
        	status=false;
        }

        return status;
        
	}
}
