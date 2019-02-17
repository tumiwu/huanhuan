package com.example.a50067.huanhuan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50067.huanhuan.Entity.FollowFansItem;
import com.example.a50067.huanhuan.R;

import java.util.List;

public class FollowFansAdapter extends RecyclerView.Adapter<FollowFansAdapter.ViewHolder> {
    private List<FollowFansItem> FollowFansList;
    private Context mComContext;

    public FollowFansAdapter(List<FollowFansItem> starComList, Context mComContext) {
        this.FollowFansList = starComList;
        this.mComContext = mComContext;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        View fansView;
        TextView Name;

        public ViewHolder(View itemView) {
            super(itemView);
            fansView=itemView;
            Name=(TextView)itemView.findViewById(R.id.followfans_item_text);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_followfans,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.fansView.setOnClickListener(v ->  {

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FollowFansItem mfans=FollowFansList.get(position);
        holder.Name.setText(mfans.getName());
    }

    @Override
    public int getItemCount() {
        return FollowFansList.size();
    }

    public void removeStarCom(int position) {
        FollowFansList.remove(position);
        notifyItemRemoved(position);
    }

}
