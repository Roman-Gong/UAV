package com.example.yuanyuanlai.uav.CustomView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanyuanlai.uav.R;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/11/12
 * @github https://github.com/Roman-Gong
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class TitleLayout extends RelativeLayout {
    private ImageButton buttonBack;
    private TextView textTitle;
    private String title;

    public TitleLayout(Context context, AttributeSet attrs) {
        super( context, attrs );
        LayoutInflater.from(context).inflate(R.layout.title, this);
        buttonBack = findViewById(R.id.ib_title_back);
        textTitle = findViewById(R.id.tv_title_title);

        buttonBack.setOnClickListener( new OnClickListener( ) {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        } );
    }

    public void setTitle(String t) {
        title = t;
        updateTitle();
    }

    private void updateTitle() {
        textTitle.setText(title);
    }

}
