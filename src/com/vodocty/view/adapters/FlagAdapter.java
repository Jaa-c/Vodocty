package com.vodocty.view.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.Country;

/**
 *
 * @author Dan Princ
 * @since 5.4.2013
 */
public class FlagAdapter extends ArrayAdapter {

	private Activity activity;
	private int layoutResourceId;
	private Country[] countries;

	public FlagAdapter(Activity activity, int resourceLayoutId, Country[] countries) {
		super(activity, resourceLayoutId, countries);
		this.activity = activity;
		this.layoutResourceId = resourceLayoutId;
		this.countries = countries;
	}

	/**
	 * Called for each row
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		FlagData content;

		if (row == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			content = new FlagData();
			content.name = (TextView) row.findViewById(R.id.flag_name);
			content.icon = (ImageView) row.findViewById(R.id.flag_icon);
		} else {
			content = (FlagData) row.getTag();
		}
		row.setTag(content);

		Country country = countries[position];

		content.name.setText(country.getName());

		int icon = activity.getResources().getIdentifier(country.name(), "drawable", activity.getPackageName());
		content.icon.setBackgroundResource(icon);

		return row;
	}

	static final class FlagData {

		protected ImageView icon;
		protected TextView name;
	}
}
