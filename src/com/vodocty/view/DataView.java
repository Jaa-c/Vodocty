package com.vodocty.view;

import android.app.Activity;
import android.os.AsyncTask;
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
    
    private Activity activity;
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
    
    public DataView(Activity a, DataModel m) {
	Log.i(this.getClass().getName(), "DataView constructor");
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
	
	loading.setText("Loading graph...");
	Log.i(this.getClass().getName(), "DataView constructor end");
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
	//favButton.setVisibility(View.VISIBLE);
	if(data.getLg().isFavorite()) {
	    favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_remove));
	}
	
	if(data.getLg().isNotify()) {
	    favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_remove));
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
    
    public void setStarIcon(boolean addButton) {
	if(addButton) {
	    favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_add));
	}
	else {
	    favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_remove));
	}
    }
    
    

}
