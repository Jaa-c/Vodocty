package com.vodocty.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import com.vodocty.R;
import com.vodocty.Vodocty;

/**
 *
 * @author Dan Princ
 * @since 31.3.2013
 */
public class CommonHeaderView {

	private final Activity activity;
	private Button flagButton;
	private Button menuButton;

	public CommonHeaderView(Activity activity) {
		this.activity = activity;


		flagButton = (Button) activity.findViewById(R.id.button_left);
		flagButtonChanged();

		menuButton = (Button) activity.findViewById(R.id.button_right);
		menuButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.menu));
	}

	public final void flagButtonChanged() {
		Vodocty v = (Vodocty) activity.getApplicationContext();
		int icon = activity.getResources().getIdentifier(v.getDisplayedCountry().name(), "drawable", activity.getPackageName());
		flagButton.setBackgroundResource(icon);
	}

	public void setFlagButtonListener(View.OnClickListener listener) {
		flagButton.setOnClickListener(listener);
	}

	public void setMenuButtonListener(View.OnClickListener listener) {
		menuButton.setOnClickListener(listener);
	}
}
