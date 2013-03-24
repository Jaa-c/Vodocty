package com.vodocty.view.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import com.vodocty.R;

/**
 *
 * @author Dan Princ
 * @since 24.3.2013
 */
public class NotificationDialog extends DialogFragment {
    
    public static final String ID = "NotificationDialog";
    
    private final Activity context;

    public NotificationDialog(Activity c) {
	this.context = c;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	// Get the layout inflater
	//LayoutInflater inflater = context.getLayoutInflater();

	// Inflate and set the layout for the dialog
	// Pass null as the parent view because its going in the dialog layout
	//builder.setView(inflater.inflate(R.layout.dialog_notification, null));
	// Add action buttons
	builder.setTitle("Upozornění na stav");
	builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int id) {
		       // sign in the user ...
		   }
	       });
	builder.setNegativeButton("not OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int id) {
		      NotificationDialog.this.getDialog().cancel();

		   }
	       });      
	return builder.create();
    }
    
    

}
