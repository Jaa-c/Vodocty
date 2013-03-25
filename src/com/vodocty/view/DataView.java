package com.vodocty.view;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.Data;
import com.vodocty.model.DataModel;
import com.vodocty.view.charts.BasicChart;
import com.vodocty.view.dialogs.NotificationDialog;
import java.util.Calendar;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import tools.Tools;

/**
 *
 * @author Dan Princ
 * @since 22.3.2013
 */
public class DataView extends AsyncTask<Void, Void, Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer>> {
    
    private FragmentActivity activity;
    private DataModel model;
    
    private Data data;
    
    private Button favButton;
    private Button notifButton;
    private TextView heading;
    private TextView date;
    private TextView flood;
    private TextView height;
    private TextView volume;
    private TextView loading;
    
    private BasicChart chart;
    
    private NotificationDialog notiDialog;
    
    public DataView(FragmentActivity a, DataModel m) {
	activity = a;
	model = m;
	
	favButton = (Button) activity.findViewById(R.id.button_fav);
	notifButton = (Button) activity.findViewById(R.id.button_right);
        heading = (TextView) activity.findViewById(R.id.data_page_heading);
	flood = (TextView) activity.findViewById(R.id.data_page_flood);
	date = (TextView) activity.findViewById(R.id.data_page_date);
	height = (TextView) activity.findViewById(R.id.data_page_height);
	volume = (TextView) activity.findViewById(R.id.data_page_volume);
	loading = (TextView) activity.findViewById(R.id.data_page_loading);
	
	//favButton.setVisibility(View.INVISIBLE);
	favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_add));
	notifButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.alert_add));
	
	chart = null;
	data = null;
	
	loading.setText("Loading graph...");
    }
    public void initDialog(DialogInterface.OnClickListener ok) {
	notiDialog = new NotificationDialog(activity, ok);
    }
    
    public void notifyDataChanged() {
	data = model.getLastData();
	checkStarIcon();
	checkNotifButton();
    }
    
    @Override
    protected void onPreExecute() {
	chart = new BasicChart(activity);
    }
    
    /**
     * This loads all the necessary data to chart.
     * It is done in a new thread in background, so the whole activity 
     * does not have to wait unitl it is finished
     * @param arg0 void
     * @return chart data
     */
    @Override
    protected Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer> doInBackground(Void... arg0) {
	data = model.getLastData();
	this.publishProgress();
	
	TimeSeries series = model.getVolumeSeries();
	series.setTitle("průtok, m3/s");
	chart.setXYSeries(series);
	return chart.getChartData();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
	this.setContent(data);
    }
    

    @Override
    protected void onPostExecute(Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer> pair) {
	loading.setVisibility(View.GONE);
	
	GraphicalView mChartView = ChartFactory.getTimeChartView(activity, pair.first, pair.second, "dd.M.\nHH:mm");
	RelativeLayout chartLayout = (RelativeLayout) activity.findViewById(R.id.data_chart);
	chartLayout.addView(mChartView);
    }
    
    
    public void setContent(Data data) {
	if(data.getLg().isFavorite()) {
	    favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_remove));
	}
	
	if(data.getLg().isNotify()) {
	    notifButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.alert_remove));
	}
	
	heading.setText(data.getLg().getRiver().getName() + " - " + 
		data.getLg().getName());
	
	if(data.getFlood() > 0) {
	    
	    flood.setText("pozor: " + data.getFlood() + ". SPA");
	    switch(data.getFlood()) {
		case 1:
		    flood.setTextColor(activity.getResources().getColor(R.color.yellow_light));
		    break;
		case 2:
		    flood.setTextColor(activity.getResources().getColor(R.color.orange_light));
		    break;
		case 3:
		    flood.setTextColor(activity.getResources().getColor(R.color.red_light));
		    break;
	    }
	}
	
	Calendar c = Calendar.getInstance();
	c.setTime(data.getDate());
	
	String dateStr = Tools.isToday(c) ? "dnes" : 
		c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);
	dateStr += " v " + (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + c.get(Calendar.HOUR_OF_DAY) + ":" + 
		(c.get(Calendar.MINUTE) < 10 ? "0" : "") + c.get(Calendar.MINUTE);
	
	date.setText(dateStr);
	
	String heightStr = (data.getHeight()) ==-1 ? "?? " : data.getHeight() + "";
	height.setText(heightStr + "cm");
	
	volume.setText(Html.fromHtml(data.getVolume() + "m<small><sup>3</sup></small>/s"));
    }
    
    public void setFavButtonListener(OnClickListener listener)  {
	favButton.setOnClickListener(listener);
    }
    
    public void setNotifButtonListener(OnClickListener listener)  {
	notifButton.setOnClickListener(listener);
    }
    
    public void checkStarIcon() {
	if(data.getLg().isFavorite()) {
	    favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_remove));
	}
	else {
	    favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_add));
	}
    }
    
    public void checkNotifButton() {
	if(data.getLg().isNotify()) {
	    notifButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.alert_remove));
	}
	else {
	    notifButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.alert_add));
	}
    
    }

    public void showNotificationDialog() {
	if(data == null) {
	    return;
	}
	notiDialog.setData(data.getLg());
	notiDialog.show(activity.getSupportFragmentManager(), NotificationDialog.ID);
    }
    
    public void cancelNotiDialog() {
	notiDialog.getDialog().cancel();
    }
    
    public String getDialogVolume() {
	return notiDialog.getVolume();
    }
    
    public String getDialogHeight() {
	return notiDialog.getHeight();
    }
    
    public boolean isNotificationEnabled() {
	return notiDialog.isNotificationEnabled();
    }
}