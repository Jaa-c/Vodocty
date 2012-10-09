package com.vodocty.update;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpReader {
   
    public static InputStream load(String url) throws IOException {
	HttpGet uri = new HttpGet(url);    

	DefaultHttpClient client = new DefaultHttpClient();
	HttpResponse resp = client.execute(uri);

	StatusLine status = resp.getStatusLine();
	if (status.getStatusCode() != 200) {
	    Log.e("vodocty HttpReader status", status.getStatusCode() + "");
	    throw new HttpResponseException(status.getStatusCode(), "Nepodarilo se stahnout aktualizace");
	}
	
	return resp.getEntity().getContent();
    }

}
