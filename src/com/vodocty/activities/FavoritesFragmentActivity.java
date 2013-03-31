package com.vodocty.activities;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import com.vodocty.R;

/**
 *
 * @author Dan Princ
 * @since 31.3.2013
 */
public class FavoritesFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	getWindow().setFormat(PixelFormat.RGBA_8888);
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	
	
	setContentView(R.layout.fragment_data);
	
	
    }



    @Override
    protected void onPause() {
	super.onPause(); 
    }

    @Override
    protected void onResume() {
	super.onResume();
    }
    
    

}
