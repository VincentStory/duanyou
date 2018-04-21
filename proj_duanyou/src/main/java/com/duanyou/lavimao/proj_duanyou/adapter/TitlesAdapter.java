package com.duanyou.lavimao.proj_duanyou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.duanyou.lavimao.proj_duanyou.R;

import java.util.List;

/**
 * @author Lavimao
 */
public class TitlesAdapter extends RecyclerView.Adapter<TitlesAdapter.ViewHolder> {
    private List<String>  mList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition = 1;//默认第2项选中

    public TitlesAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_titles,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == selectedPosition){
            holder.tvTitle.setTextColor(Color.parseColor("#FB3888"));
        }else {
            holder.tvTitle.setTextColor(Color.parseColor("#4d4d4d"));
        }
        holder.tvTitle.setText(mList.get(position));
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(position);
                onItemClickListener.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);

        }
    }


    public interface OnItemClickListener{
        void click(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 获取当前选中的索引
     */
    public void setSelected(int selectedPosition){
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }
}
