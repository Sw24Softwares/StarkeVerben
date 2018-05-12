package org.sw24softwares.starkeverben;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.database.Cursor;
import android.widget.ExpandableListView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import org.sw24softwares.starkeverben.XYMarkerView;

public class ProgressGraphFragment extends Fragment {
    private DatabaseHelper mDatabaseHelper;
    private Vector<String> mDates   = new Vector<String>();
    private Vector<Integer> mScores = new Vector<Integer>();

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progress_graph, container, false);

        Context context = getActivity();

        int colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary);

        mDatabaseHelper = new DatabaseHelper(context);
        parseData();

        if(!mScores.isEmpty()) {
            LineChart chart = (LineChart) view.findViewById(R.id.chart);
            chart.setDescription(null);
            chart.setDrawGridBackground(false);
            chart.setTouchEnabled(true);
            chart.setDragEnabled(true);
            chart.setMaxHighlightDistance(300);
            chart.setPinchZoom(false);
            chart.setDoubleTapToZoomEnabled(false);
            chart.getLegend().setEnabled(false);
            XYMarkerView mv
                = new XYMarkerView(context, new XAxisValueFormatter(mDates.toArray(new String[0])));
            mv.setChartView(chart);
            chart.setMarker(mv);

            XAxis x = chart.getXAxis();
            x.setEnabled(true);
            x.setPosition(XAxis.XAxisPosition.BOTTOM);
            x.setDrawGridLines(false);
            x.setDrawLabels(false);
            x.setGranularity(1f);

            YAxis yLeft = chart.getAxisLeft();
            yLeft.setEnabled(true);
            yLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            yLeft.setDrawAxisLine(false);
            yLeft.setDrawGridLines(true);
            yLeft.enableGridDashedLine(5f, 10f, 0f);
            yLeft.setXOffset(15);
            yLeft.setAxisMinimum(0f);
            yLeft.setAxisMaximum(100f);
            yLeft.setGranularityEnabled(true);

            chart.getAxisRight().setEnabled(false);

            List<Entry> entries = new ArrayList<>();
            for(int i = 0; i < mScores.size(); i++)
                entries.add(new Entry(i, mScores.get(i)));

            LineDataSet dataSet = new LineDataSet(entries, "Scores");
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setCubicIntensity(0.2f);
            dataSet.setDrawCircles(false);
            dataSet.setLineWidth(2f);
            dataSet.setDrawValues(false);
            dataSet.setValueTextSize(12f);
            dataSet.setHighlightEnabled(true);
            dataSet.setColors(colorPrimary);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.setVisibleXRangeMaximum(60);
            chart.moveViewToX(mDates.size());
        }

        return view;
    }
    private void parseData() {
        Cursor data = mDatabaseHelper.getListContents();
        for(int i = 0; data.moveToNext(); i++) {
            String parts[] = data.getString(1).split(" ");

            mDates.addElement(parts[0] + " : " + parts[1]);
            mScores.addElement(Integer.parseInt(parts[2]));
        }
    }
}
