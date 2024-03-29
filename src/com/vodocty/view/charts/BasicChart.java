package com.vodocty.view.charts;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import com.vodocty.R;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import com.vodocty.tools.Tools;

/**
 *
 * @author Dan Princ
 * @since 2.3.2013
 */
public class BasicChart {

	private XYSeries series;
	private Activity activity;
	protected static final int[] MARGINS = {5, 5, 16, 5}; //top lef bot rig
	protected static final int TEXT_LEGEND = 18;
	protected static final int TEXT_LABELS = 13;
	protected static final float POINT_SIZE = 1.0f;
	protected static final int LABEL_COUNT_X = 6;
	protected static final int LABEL_COUNT_Y = 7;
	protected static final float LINE_WIDTH = 2.5f;
	protected static int LINE_COLOR;
	protected static int AXE_COLOR;
	protected static int BACKGROUND_COLOR;
	protected static int FONT_WHITE_COLOR;

	public BasicChart(Activity activity) {
		this.activity = activity;

		LINE_COLOR = activity.getResources().getColor(R.color.blue_2);
		AXE_COLOR = activity.getResources().getColor(R.color.grey_line);
		BACKGROUND_COLOR = activity.getResources().getColor(R.color.transparent);
		FONT_WHITE_COLOR = activity.getResources().getColor(R.color.red);
	}

	public void setXYSeries(XYSeries series) {
		this.series = series;
	}

	public Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer> getChartData() {

		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(LINE_COLOR);
		r.setPointStyle(PointStyle.CIRCLE);
		r.setFillPoints(true);
		r.setLineWidth(LINE_WIDTH);

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(r);

		renderer.setLegendTextSize(TEXT_LEGEND);
		renderer.setLabelsTextSize(TEXT_LABELS);

		renderer.setPointSize(POINT_SIZE);
		renderer.setMargins(MARGINS);

		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(BACKGROUND_COLOR);
		renderer.setMarginsColor(BACKGROUND_COLOR);


		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Paint.Align.CENTER);
		renderer.setYLabelsAlign(Paint.Align.LEFT);
		renderer.setXLabels(LABEL_COUNT_X);
		renderer.setYLabels(LABEL_COUNT_Y);

		renderer.setAxesColor(AXE_COLOR);
		renderer.setLabelsColor(FONT_WHITE_COLOR);

		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true, false);


		double diff = (series.getMaxY() - series.getMinY()) * 0.1f;

		renderer.setXAxisMin(series.getMaxX() - 2.5f * Tools.DAY_SECONDS * 1000); //zobrazuju 2,5 dne
		renderer.setXAxisMax(series.getMaxX() + 0.2f * Tools.DAY_SECONDS * 1000);
		renderer.setYAxisMin(series.getMinY());
		renderer.setYAxisMax(series.getMaxY() + 2 * diff); //posunu o trochu niz

		//TODO
//	renderer.setPanLimits(new double[] { 
//	    series.getMinX() - Tools.DAY_SECONDS, series.getMaxX() + Tools.DAY_SECONDS,
//	    series.getMinY() - diff, series.getMaxY() + diff
//	});


		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		return new Pair<XYMultipleSeriesDataset, XYMultipleSeriesRenderer>(dataset, renderer);
	}
}
