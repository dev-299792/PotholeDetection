package com.example.mp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.Sensor;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
         {

    SensorManager sensorManager;
    Sensor accelerometerSensor;
    SQLHelper helper;
    SQLiteDatabase database;
    TextView tx,ty,tz,tt;

    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button2 = findViewById(R.id.button2);

//        startActivity(new Intent(this,GraphActivity.class));

        final TextView textView = findViewById(R.id.textView);
//        final TextView textView2 = findViewById(R.id.textView2);

        tx = findViewById(R.id.x);
        ty = findViewById(R.id.y);
        tz = findViewById(R.id.z);
        tt = findViewById(R.id.t);



        LineChart lineChart = new LineChart(this) ;


        helper = new SQLHelper(getApplicationContext());
        database = helper.getWritableDatabase();
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                textView.setText(String.valueOf(event.values[0]));
                ContentValues values = new ContentValues();
                values.put("x",event.values[0]);
                values.put("y",event.values[1]);
                values.put("z",event.values[2]);
                values.put("t",String.valueOf(System.currentTimeMillis()));
                database.insert("readings",null,values);

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        if(accelerometerSensor == null)
        {
            Log.i("sensorerror","accleroemter sensor not present");
            finish();
        }


        sensorManager.registerListener(sensorEventListener,accelerometerSensor,2*100*1000);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.delete("readings",null,null);
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.query("readings",new String[]{"x","y","z","t"},null,null,null,null,null);

                float[] arrayList = new float[2005];
                String x="X\n",y="Y\n",z="Z\n",t="time\n";

                int i=0;
                if (cursor.moveToLast()) {
                    ++i;
                    arrayList[i-1]=cursor.getFloat(2);
                    x+=""+cursor.getFloat(0)+'\n';
                    y+=""+cursor.getFloat(1)+'\n';
                    z+=""+cursor.getFloat(2)+'\n';
                    t+=""+cursor.getFloat(3)+'\n';
                    //arrayList.add(""+cursor.getFloat(0)+' '+cursor.getFloat(1)+' '+cursor.getFloat(2)+' '+cursor.getString(3));
                    while (cursor.moveToPrevious() && (i<2000)) {
                        //arrayList.add(""+cursor.getFloat(0)+' '+cursor.getFloat(1)+' '+cursor.getFloat(2)+' '+cursor.getString(3));
                        ++i;
                        arrayList[i-1]=cursor.getFloat(2);
                        x+=""+cursor.getFloat(0)+'\n';
                        y+=""+cursor.getFloat(1)+'\n';
                        z+=""+cursor.getFloat(2)+'\n';
                        t+=""+cursor.getFloat(3)+'\n';
                    }
                }

//                String s="";
//                for (int j=0;j<arrayList.size();++j)
//                {
//                    s += arrayList.get(j)+'\n';
//                }

//                textView2.setText(s);

                Intent intent = new Intent(getApplicationContext(),GraphActivity.class);
                intent.putExtra("data",arrayList);
                intent.putExtra("len",i);

                startActivity(intent);

                tx.setText(x);
                ty.setText(y);
                tz.setText(z);
                tt.setText(t);
            }
        });

    }
}

















//                if(cursor.moveToFirst()) {
//                    s = " " + cursor.getFloat(0);
//                    s += " " + cursor.getFloat(1);
//                    s += " " + cursor.getFloat(2);
//                    s += " " + cursor.getString(3);
//                    s += '\n';
//                }
//                int i=0;
//                while(cursor.moveToNext() && (i<10)) {
//                    s = " " + cursor.getFloat(0);
//                    s += " " + cursor.getFloat(1);
//                    s += " " + cursor.getFloat(2);
//                    s += " " + cursor.getString(3);
//                    s += '\n';
//                }
