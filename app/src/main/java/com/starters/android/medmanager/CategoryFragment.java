package com.starters.android.medmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.starters.android.medmanager.mDataBase.Constants;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private static final String TAG = CategoryFragment.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Float> intakeArray = new ArrayList<>();
    String[] monthArray = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    BarChart chart;
    public CategoryFragment() {
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        chart = (BarChart) view.findViewById(R.id.chart);
        return view;
    }
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            // ...
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            BarData data = new BarData(getXAxisValues(), getDataSet());
            chart.setData(data);
            //chart.setDescription("Medication intake Chart");
            chart.animateXY(2000, 2000);
            chart.invalidate();
        }
    }
    private ArrayList<BarDataSet> getDataSet() {
        SharedPreferences mPref = getContext().getSharedPreferences("MONTHLY_INTAKE", getContext().MODE_PRIVATE);
        ArrayList<BarDataSet> dataSets = null;
        for(int counter = 0;counter < monthArray.length;counter++){
            //monthArray[counter]
            intakeArray.add((float)mPref.getInt(monthArray[counter]+ Constants.MONTHLY_COUNTER,0));
        }
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(intakeArray.get(0), 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(intakeArray.get(1), 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(intakeArray.get(2), 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(intakeArray.get(3), 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(intakeArray.get(4), 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(intakeArray.get(5), 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(intakeArray.get(6), 0); // Jul
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(intakeArray.get(7), 1); // Aug
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(intakeArray.get(8), 2); // Sep
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(intakeArray.get(9), 3); // Oct
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(intakeArray.get(10), 4); // Nov
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(intakeArray.get(11), 5); // Dec
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
}
