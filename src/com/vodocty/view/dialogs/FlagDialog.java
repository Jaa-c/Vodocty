package com.vodocty.view.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.vodocty.R;

/**
 *
 * @author Dan Princ
 * @since 24.3.2013
 */
public class FlagDialog extends DialogFragment {

	public static final String ID = "FlagDialog";
	private final Activity context;
	private ArrayAdapter adapter;
	private OnItemClickListener listClickHandler;

	public FlagDialog(Activity c, ArrayAdapter adapter, OnItemClickListener listClickHandler) {
		this.context = c;
		this.adapter = adapter;
		this.listClickHandler = listClickHandler;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		// Get the layout inflater
		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_flags, null);
		ListView list = (ListView) view.findViewById(R.id.flag_listview);
		list.setAdapter(adapter);
		list.setOnItemClickListener(listClickHandler);

		builder.setView(view);

		builder.setTitle("Vyberte stát");

		//builder.setPositiveButton("OK", ok);
		builder.setNegativeButton("Zpět", null);

		return builder.create();
	}
}
