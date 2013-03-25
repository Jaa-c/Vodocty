package com.vodocty.view.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.LG;

/**
 *
 * @author Dan Princ
 * @since 24.3.2013
 */
public class NotificationDialog extends DialogFragment {
    
    public static final String ID = "NotificationDialog";
    
    private final Activity context;
    private final OnClickListener ok;
    
    private EditText heightEditText;
    private EditText volumeEditText;
    private CheckBox checkBox;
    
    private LG lg;

    public NotificationDialog(Activity c, OnClickListener ok) {
	this.context = c;
	
	this.ok = ok;
    }
    
    public void setData(LG lg) {
	this.lg = lg;
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
	heightLabel.setText("Stav (cm)");
	TextView volumeLabel = (TextView) view.findViewById(R.id.label_volume);
	volumeLabel.setText(Html.fromHtml("Půtok (m<small><sup>3</sup></small>/s)"));
	
	heightEditText = (EditText) view.findViewById(R.id.edittext_height);
	volumeEditText = (EditText) view.findViewById(R.id.edittext_volume);
	checkBox = (CheckBox) view.findViewById(R.id.dialog_checkbox);
	checkBox.setText("Povolit upozornění");
	
	checkBox.setChecked(lg.isNotify());
	volumeEditText.setText(lg.getNotifyVolume() > 0 ? lg.getNotifyVolume()+ "" : "");
	heightEditText.setText(lg.getNotifyHeight()> 0 ? lg.getNotifyHeight()+ "" : "");
	
	builder.setView(view);

	builder.setTitle("Upozornění na stav");
	
	builder.setPositiveButton("OK", ok);
	builder.setNegativeButton("Zpět", null);
	
	return builder.create();
    }
    
    public String getHeight() {
	return heightEditText.getText().toString();
    }
    
    public String getVolume() {
	return volumeEditText.getText().toString();
    }
    
    public boolean isNotificationEnabled() {
	return checkBox.isChecked();
    }    
    

}
