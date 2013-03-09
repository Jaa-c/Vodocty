package com.vodocty.controllers;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.Data;
import com.vodocty.model.DataModel;
import com.vodocty.view.charts.BasicChart;
import java.util.Calendar;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import tools.Tools;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataController extends AsyncTask<Void, Void, Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer>> {
    
    private Activity activity;
    private DataModel model;
    private BasicChart chart;
    
    public DataController(Activity activity, DataModel model) {
	
	this.activity = activity;
	this.model = model;
	chart = null;
	
	Data data = model.getLastData();
	
        TextView text = (TextView) activity.findViewById(R.id.data_page_heading);
	text.setText(data.getLg().getRiver().getName() + " - " + 
		data.getLg().getName() );
	
	text = (TextView) activity.findViewById(R.id.data_page_date);
	
	Calendar c = Calendar.getInstance();
	c.setTime(data.getDate());
	
	
	String date = Tools.isToday(c) ? "dnes" : 
		c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);
	date += " v " + (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + c.get(Calendar.HOUR_OF_DAY) + ":" + 
		(c.get(Calendar.MINUTE) < 10 ? "0" : "") + c.get(Calendar.MINUTE);
	
	text.setText(date);
	
	text = (TextView) activity.findViewById(R.id.data_page_height);
	String height = (data.getHeight()) ==-1 ? "?? " : data.getHeight() + "";
	text.setText(height + "cm");
	
	text = (TextView) activity.findViewById(R.id.data_page_volume);
	text.setText(Html.fromHtml(data.getVolume() + "m<small><sup>3</sup></small>/s"));
		
    }

    @Override
    protected void onPreExecute() {
	TextView view = (TextView) activity.findViewById(R.id.data_page_loading);
	view.setText("Loading graph...");
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
	chart = new BasicChart(activity);
	chart.setXYSeries(model.getVolumeSeries("průtok, m3/s"));
	return chart.getChartData();
    }

    @Override
    protected void onPostExecute(Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer> pair) {
	TextView view = (TextView) activity.findViewById(R.id.data_page_loading);
	view.setVisibility(View.GONE);
	
	GraphicalView mChartView = ChartFactory.getTimeChartView(activity, pair.first, pair.second, "dd.M.\nHH:mm");
	RelativeLayout chartLayout = (RelativeLayout) activity.findViewById(R.id.data_chart);
	chartLayout.addView(mChartView);
    }
    
}
