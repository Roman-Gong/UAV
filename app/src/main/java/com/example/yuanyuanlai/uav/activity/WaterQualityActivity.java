package com.example.yuanyuanlai.uav.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yuanyuanlai.uav.Bean.SensorList;
import com.example.yuanyuanlai.uav.Bean.WaterQualityRoot;
import com.example.yuanyuanlai.uav.CustomView.RotateScanView;
import com.example.yuanyuanlai.uav.CustomView.WaveView;
import com.example.yuanyuanlai.uav.R;
import com.example.yuanyuanlai.uav.base.BaseActivity;
import com.example.yuanyuanlai.uav.util.DetailUrl;
import com.example.yuanyuanlai.uav.util.OkHttpUtils;
import com.example.yuanyuanlai.uav.util.ThreadPoolManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author Gong Yunhao
 */
public class WaterQualityActivity extends BaseActivity implements View.OnClickListener {
    private RotateScanView rotateScanView;
    private WaveView waveView;
    private TextView textOxygen, textNitrogen, textPh;
    private TextView standardOxygen, standardNitrogen, standardPh;
    private String phStr, oxyStr, nitroStr, temperatureStr;

    private static final String TAG = "WaterQualityActivity";

    // 检测指标方法
    private int checkPH(String ph) {
        if (ph.isEmpty()) {
            return 0;
        }
        double dph = Double.parseDouble(ph);
        if (dph < 9 && dph > 6) {
            return 1;
        } else {
            return 0;
        }
    }

    private int checkNitrogen(String nitrogen) {
        if (nitrogen.isEmpty()) {
            return 0;
        }
        double dnitrogen = Double.parseDouble(nitrogen);
        if (dnitrogen < 0.2) {
            return 1;
        } else {
            return 0;
        }
    }

    private int checkOxygen(String oxygen) {
        if (oxygen.isEmpty()) {
            return 0;
        }
        double doxygen = Double.parseDouble(oxygen);
        if (doxygen > 4 && doxygen < 10) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, WaterQualityActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_water_quality );
    }

    @Override
    public void initListener() {
        standardPh.setOnClickListener(this);
        standardOxygen.setOnClickListener(this);
        standardNitrogen.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume( );
        rotateScanView.startAnimation();
        Log.d(TAG, "------>onResume");
    }

    @Override
    protected void onPause() {
        super.onPause( );
        rotateScanView.pauseAnimation();
        Log.d(TAG, "------>onPause");
    }

    @Override
    public void initView() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.shape_blue_end));
        changeStatusBarTextColor(false);
        rotateScanView = findViewById(R.id.rotate_scan_view);
        waveView = findViewById(R.id.wave_view);
        textOxygen = findViewById(R.id.tv_water_quality_oxygen);
        textNitrogen = findViewById(R.id.tv_water_quality_nitrogen);
        textPh = findViewById(R.id.tv_water_quality_ph);
        standardNitrogen = findViewById(R.id.tv_quality_nitrogen_standard);
        standardOxygen = findViewById(R.id.tv_quality_oxygen_standard);
        standardPh = findViewById(R.id.tv_quality_ph_standard);
    }

    @Override
    public void initDate() {
        ThreadPoolManager.getInstance().addExecuteTask( new Runnable( ) {
            @Override
            public void run() {
                OkHttpUtils.getInstance().post(DetailUrl.sEquipmentData, new okhttp3.Callback( ) {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "---->" + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.d(TAG, "->" + result);

                        Gson gson = new Gson();
                        WaterQualityRoot slist=gson.fromJson(result, new TypeToken<WaterQualityRoot>(){}.getType());
                        List<SensorList> sensorLists = slist.getDeviceList().get(0).getSensorList();
                        phStr = sensorLists.get(0).getValue();
                        oxyStr = sensorLists.get(1).getValue();
                        temperatureStr = sensorLists.get(2).getValue();
                        nitroStr = sensorLists.get(3).getValue();
                        runOnUiThread( new Runnable( ) {
                            @Override
                            public void run() {
                                setTextData();
                            }
                        } );
                    }
                } );
            }
        } );

    }

    private void setTextData() {
        // PH:ph # oxygen:mg/l # temperature:℃ # nitrogen:mg/l
        if (nitroStr.isEmpty()) {
            textNitrogen.setTextSize(14);
            textNitrogen.setText("暂无数据");
        } else {
            textNitrogen.setTextSize(24);
            textNitrogen.setText(nitroStr);
        }

        if (oxyStr.isEmpty()) {
            textOxygen.setTextSize(14);
            textOxygen.setText("暂无数据");
        } else {
            textOxygen.setTextSize(24);
            textOxygen.setText(oxyStr);
        }

        if (phStr.isEmpty()) {
            textPh.setTextSize(14);
            textPh.setText("暂无数据");
        } else {
            textPh.setTextSize(24);
            textPh.setText(phStr);
        }

        int sum = checkNitrogen(nitroStr) + checkOxygen(oxyStr) + checkPH(phStr);
        String quality = RotateScanView.BAD;
        switch (sum) {
            case 0:
                quality = RotateScanView.BAD;
                break;
            case 1:
                quality = RotateScanView.BAD;
                break;
            case 2:
                quality = RotateScanView.FINE;
                break;
            case 3:
                quality = RotateScanView.GREAT;
                break;
                default:
                    break;
        }
        rotateScanView.setWaterQuality(quality, temperatureStr);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_quality_nitrogen_standard:
                break;
            case R.id.tv_quality_oxygen_standard:
                break;
            case R.id.tv_quality_ph_standard:
                break;
                default:
                    break;
        }
    }
}
