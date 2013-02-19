package com.vodocty.update;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Reads feeds via http.
 * 
 * @author Dan Princ
 * @since long time ago
 */
public class HttpReader {
   
    public static InputStream load(String url) {
	HttpGet uri = new HttpGet(url);    

	DefaultHttpClient client = new DefaultHttpClient();
	HttpResponse resp;
	
	try {
	    resp = client.execute(uri);
	}
	catch(IOException e) {
	    return null;
	}
	
	StatusLine status = resp.getStatusLine();
	if (status.getStatusCode() != 200) {
	    Log.e(HttpReader.class.getName(), "url " + url + " status: " + status.getStatusCode());
	    return null;
	}
	
	try {
	    return resp.getEntity().getContent();
	}
	catch(IOException e){
	    return null;
	}
	
    }
    
}
