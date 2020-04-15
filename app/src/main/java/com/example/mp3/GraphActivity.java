package com.example.mp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.security.KeyStore;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity
         {

    LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mChart = findViewById(R.id.graph);

//        mChart.setOnChartGestureListener(this);
//        mChart.setOnChartValueSelectedListener(this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);


        Intent intent=getIntent();

        float[] d = intent.getFloatArrayExtra("data");
        int n = intent.getIntExtra("len",0);

        ArrayList<Entry> yValues = new ArrayList<>();

        for (int i=0;i<n;++i)
        {
            yValues.add(new Entry(i,d[i]));
        }

        LineDataSet set1 = new LineDataSet(yValues,"dataset 1");

        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);



    }
}
