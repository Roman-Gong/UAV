package com.example.yuanyuanlai.uav;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryDataActivity extends AppCompatActivity {

    private List<HistoryDataBean> mHistoryDataBeanList = new ArrayList<>();
    private HistoryDataAdapter mHistoryDataAdapter;
    private RecyclerView mHistoryDataRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#052a4b"));

        setContentView(R.layout.activity_history_data);
        HistoryDataBean historyDataBean = new HistoryDataBean();
        historyDataBean.setType(HistoryDataBean.CHART);
        historyDataBean.setChartSelect(HistoryDataBean.YEAR);
        mHistoryDataBeanList.add(historyDataBean);
        HistoryDataBean historyDataBean3 = new HistoryDataBean();
        historyDataBean3.setType(HistoryDataBean.DATA_YEAR);
        mHistoryDataBeanList.add(historyDataBean3);

        for (int i=0; i<8; i++) {
            HistoryDataBean historyDataBean1 = new HistoryDataBean();
            historyDataBean1.setType(HistoryDataBean.DATA);
            mHistoryDataBeanList.add(historyDataBean1);
        }

        HistoryDataBean historyDataBean4 = new HistoryDataBean();
        historyDataBean4.setType(HistoryDataBean.DATA_YEAR);
        mHistoryDataBeanList.add(historyDataBean4);
        for (int i=0; i<7; i++) {
            HistoryDataBean historyDataBean1 = new HistoryDataBean();
            historyDataBean1.setType(HistoryDataBean.DATA);
            mHistoryDataBeanList.add(historyDataBean1);
        }

        HistoryDataBean historyDataBean2 = new HistoryDataBean();
        historyDataBean2.setType(HistoryDataBean.END);
        mHistoryDataBeanList.add(historyDataBean2);
        mHistoryDataAdapter = new HistoryDataAdapter(HistoryDataActivity.this, mHistoryDataBeanList);
        mHistoryDataRecyclerView  = (RecyclerView)findViewById(R.id.rv_history_data);
        mHistoryDataRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryDataActivity.this));
        mHistoryDataRecyclerView.setAdapter(mHistoryDataAdapter);

    }
}
