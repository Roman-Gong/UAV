package com.example.yuanyuanlai.uav.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yuanyuanlai.uav.CustomView.RotateScanView;
import com.example.yuanyuanlai.uav.CustomView.WaveView;
import com.example.yuanyuanlai.uav.R;

/**
 * @author Gong Yunhao
 */
public class WaterQualityActivity extends AppCompatActivity {
    private RotateScanView rotateScanView;
    private WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_water_quality );
        rotateScanView = findViewById(R.id.rotate_scan_view);
        waveView = findViewById(R.id.wave_view);
    }
}
