package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.Data;
import com.vodocty.model.DataModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import tools.Tools;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataController extends AsyncTask<Object, Object, Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer>> {
    
    private Activity activity;
    private DataModel model;
    
    public DataController(Activity activity, DataModel model) {
	
	this.activity = activity;
	this.model = model;
	
	Data data = model.getLastData();
	
        TextView text = (TextView) activity.findViewById(R.id.data_page_heading);
	text.setText(data.getLg().getRiver().getName() + "\n" + 
		data.getLg().getName() );
	
	text = (TextView) activity.findViewById(R.id.data_page_date);
	
	Calendar c = Calendar.getInstance();
	c.setTime(data.getDate());
	
	
	String date = Tools.isToday(c) ? "dnes" : 
		c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);
	date += " " + (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + c.get(Calendar.HOUR_OF_DAY) + ":" + 
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
    
    

    @Override
    protected Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer> doInBackground(Object... arg0) {
	return initGraph();
    }

    @Override
    protected void onPostExecute(Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer> pair) {
	TextView view = (TextView) activity.findViewById(R.id.data_page_loading);
	view.setVisibility(View.GONE);
	
	GraphicalView mChartView = ChartFactory.getTimeChartView(activity, pair.first, pair.second, "dd.M.\nHH:mm");
	RelativeLayout chart = (RelativeLayout) activity.findViewById(R.id.data_chart);
	chart.addView(mChartView);
    }
    
    
    public Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer> initGraph() {
	
	XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	renderer.setAxisTitleTextSize(13);
	renderer.setLegendTextSize(13);
	renderer.setLabelsTextSize(14);
	
	renderer.setPointSize(1f);
	renderer.setMargins(new int[] { 5, 30, 15, 5 });
	
	XYSeriesRenderer r = new XYSeriesRenderer();
	r.setColor(activity.getResources().getColor(R.color.blue_light));
	//r.setFillBelowLine(true);
	//r.setFillBelowLineColor();
	
	r.setPointStyle(PointStyle.CIRCLE);
	r.setLineWidth(2.5f);
	//r.setDisplayChartValues(true);

	r.setFillPoints(false);
	renderer.addSeriesRenderer(r);
	
	///renderer.setChartTitle("");
	//renderer.setXTitle("Datum");
	renderer.setYTitle("kubíků");
	renderer.setAxesColor(Color.LTGRAY);
	renderer.setLabelsColor(Color.LTGRAY);
	
	renderer.setShowGrid(true);
	renderer.setXLabelsAlign(Paint.Align.RIGHT);
	renderer.setYLabelsAlign(Paint.Align.RIGHT);
	renderer.setZoomButtonsVisible(true);
	//renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
	
	
	XYSeries series = model.getVolumeSeries("průtok");
	double diff = (series.getMaxY() - series.getMinY()) * 0.1f;
	
	renderer.setXAxisMin(series.getMaxX() - 2 * Tools.DAY_SECONDS); //zobrazuju 2 dny
	renderer.setXAxisMax(series.getMaxX() + 0.2f * Tools.DAY_SECONDS);
	//renderer.setYAxisMin(series.getMinY());
	renderer.setYAxisMax(series.getMaxY() + 2 * diff); //posunu o trochu niz
	renderer.setXLabels(5);
	renderer.setYLabels(7);
	
	
	renderer.setPanLimits(new double[] { 
	    series.getMinX() - Tools.DAY_SECONDS, series.getMaxX() + Tools.DAY_SECONDS,
	    series.getMinY() - diff, series.getMaxY() + diff
	});
	
	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	dataset.addSeries(series);
	
	return new Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer>(dataset, renderer);
    
    }
    
    
   

}
