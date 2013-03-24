package com.vodocty.view.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.vodocty.R;

/**
 *
 * @author Dan Princ
 * @since 24.3.2013
 */
public class NotificationDialog extends DialogFragment {
    
    public static final String ID = "NotificationDialog";
    
    private final Activity context;
    private final OnClickListener ok, cancel, delete;

    public NotificationDialog(Activity c, OnClickListener ok, 
	    OnClickListener cancel, OnClickListener delete) {
	this.context = c;
	
	this.ok = ok;
	this.cancel = cancel;
	this.delete = delete;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
	// Get the layout inflater
	LayoutInflater inflater = context.getLayoutInflater();
	View view = inflater.inflate(R.layout.dialog_notification, null);
	TextView info = (TextView) view.findViewById(R.id.info);
	info.setText("Zde můžete nastavit hodnoty, při jejichž překročení vás "
		+ "aplikace automaticky upozorní.\nMůžete vyplnit jedno nebo obě pole");
	
	TextView heightLabel = (TextView) view.findViewById(R.id.label_height);
	heightLabel.setText("Stav (cm):");
	TextView volumeLabel = (TextView) view.findViewById(R.id.label_volume);
	volumeLabel.setText(Html.fromHtml("Půtok (m<small><sup>3</sup></small>/s):"));
	
	builder.setView(view);

	builder.setTitle("Upozornění na stav");
	
	builder.setPositiveButton("OK", ok);
	builder.setNegativeButton("Smazat upozornění", cancel); 
	builder.setNeutralButton("Zpět", delete);  
	
	return builder.create();
    }
    
    

}
