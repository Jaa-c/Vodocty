package com.vodocty.update;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
	HttpResponse resp = null;
	
	try {
	    resp = client.execute(uri);
	}
	catch(IOException e) {
	    return null;
	}
	
	StatusLine status = resp.getStatusLine();
	if (status.getStatusCode() != 200) {
	    Log.e(HttpReader.class.getName(), status.getStatusCode() + "");
	    return null;
	}
	
	try {
	    return resp.getEntity().getContent();
	    //return EntityUtils.toString(resp.getEntity());
	}
	catch(IOException e){
	    return null;
	}
	
    }
    
    @Deprecated
    private static InputStream load2(String urlin) throws IOException {
	URL url = new URL(urlin);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(1000);
        urlConnection.setReadTimeout(1000);
        //BufferedReader breader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	return urlConnection.getInputStream();
        
    }

}
