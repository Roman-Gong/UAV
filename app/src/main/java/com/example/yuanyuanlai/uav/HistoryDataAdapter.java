package com.example.yuanyuanlai.uav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class HistoryDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<HistoryDataBean> mHistoryDataBeanList;

    public HistoryDataAdapter(Context context, List<HistoryDataBean> historyDataBeanList) {
        mContext = context;
        mHistoryDataBeanList = historyDataBeanList;
    }

    @Override
    public int getItemViewType(int position) {

        return mHistoryDataBeanList.get(position).getType();

    }

    public class HistoryChartViewHolder extends RecyclerView.ViewHolder {

        private StaticView staticView;
        private List<FrameLayout> frameLayoutList = new ArrayList<>();
        private List<TextView> textViewList = new ArrayList<>();

        public HistoryChartViewHolder(View itemView) {
            super(itemView);
            staticView = (StaticView)itemView.findViewById(R.id.sv_test);
            frameLayoutList.add((FrameLayout)itemView.findViewById(R.id.fl_week));
            frameLayoutList.add((FrameLayout)itemView.findViewById(R.id.fl_month));
            frameLayoutList.add((FrameLayout)itemView.findViewById(R.id.fl_year));
            textViewList.add((TextView)itemView.findViewById(R.id.tv_week));
            textViewList.add((TextView)itemView.findViewById(R.id.tv_month));
            textViewList.add((TextView)itemView.findViewById(R.id.tv_year));
        }

    }

    public class HistoryDataYearViewHolder extends RecyclerView.ViewHolder {


        public HistoryDataYearViewHolder(View itemView) {
            super(itemView);
        }

    }

    public class HistoryDataViewHolder extends RecyclerView.ViewHolder {

        public HistoryDataViewHolder(View itemView) {
            super(itemView);
        }

    }

    public class HistoryEndViewHolder extends RecyclerView.ViewHolder {

        public HistoryEndViewHolder(View itemView) {
            super(itemView);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == HistoryDataBean.CHART) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_history_chart, parent, false);
            return new HistoryChartViewHolder(view);
        } else if (viewType == HistoryDataBean.DATA){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_history_data, parent, false);
            return new HistoryDataViewHolder(view);
        } else if (viewType == HistoryDataBean.END) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_history_end, parent, false);
            return new HistoryEndViewHolder(view);
        } else if (viewType == HistoryDataBean.DATA_YEAR) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_history_data_year, parent, false);
            return new HistoryDataYearViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HistoryChartViewHolder) {

            HistoryChartViewHolder historyChartViewHolder = (HistoryChartViewHolder)holder;
            final List<FrameLayout> frameLayoutList = historyChartViewHolder.frameLayoutList;
            final List<TextView> textViewList = historyChartViewHolder.textViewList;

            if (mHistoryDataBeanList.get(position).getChartSelect() == HistoryDataBean.WEEK) {

                resetFrameLayout(frameLayoutList, textViewList);
                frameLayoutList.get(0).setBackground(mContext.getDrawable(R.drawable.shape_item_format_select));
                textViewList.get(0).setTextColor(mContext.getColor(R.color.colorTextSelected));

            } else if (mHistoryDataBeanList.get(position).getChartSelect() == HistoryDataBean.MONTH){

                resetFrameLayout(frameLayoutList, textViewList);
                frameLayoutList.get(1).setBackground(mContext.getDrawable(R.drawable.shape_item_format_select));
                textViewList.get(1).setTextColor(mContext.getColor(R.color.colorTextSelected));

            } else {

                resetFrameLayout(frameLayoutList, textViewList);
                frameLayoutList.get(2).setBackground(mContext.getDrawable(R.drawable.shape_item_format_select));
                textViewList.get(2).setTextColor(mContext.getColor(R.color.colorTextSelected));

            }

            historyChartViewHolder.staticView.init();

            frameLayoutList.get(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHistoryDataBeanList.get(position).setChartSelect(HistoryDataBean.WEEK);
                    resetFrameLayout(frameLayoutList, textViewList);
                    frameLayoutList.get(0).setBackground(mContext.getDrawable(R.drawable.shape_item_format_select));
                    textViewList.get(0).setTextColor(mContext.getColor(R.color.colorTextSelected));

                }
            });

            frameLayoutList.get(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHistoryDataBeanList.get(position).setChartSelect(HistoryDataBean.MONTH);
                    resetFrameLayout(frameLayoutList, textViewList);
                    frameLayoutList.get(1).setBackground(mContext.getDrawable(R.drawable.shape_item_format_select));
                    textViewList.get(1).setTextColor(mContext.getColor(R.color.colorTextSelected));

                }
            });

            frameLayoutList.get(2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHistoryDataBeanList.get(position).setChartSelect(HistoryDataBean.YEAR);
                    resetFrameLayout(frameLayoutList, textViewList);
                    frameLayoutList.get(2).setBackground(mContext.getDrawable(R.drawable.shape_item_format_select));
                    textViewList.get(2).setTextColor(mContext.getColor(R.color.colorTextSelected));

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mHistoryDataBeanList.size();
    }

    private void resetFrameLayout(List<FrameLayout> frameLayoutList, List<TextView> textViewList) {

        for (FrameLayout frameLayout : frameLayoutList)
            frameLayout.setBackground(mContext.getDrawable(R.drawable.shape_format_select));

        for (TextView textView : textViewList)
            textView.setTextColor(mContext.getColor(R.color.colorTextNormal));


    }



}
