package com.example.a50067.huanhuan.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50067.huanhuan.Entity.ReplyItem;
import com.example.a50067.huanhuan.R;

import java.util.List;

public class ReplyItemAdapter  extends RecyclerView.Adapter<ReplyItemAdapter.ViewHolder> {
    private List<ReplyItem> ReplyList;
    private Context mContext;

    public ReplyItemAdapter(List<ReplyItem> replyList, Context mContext) {
        ReplyList = replyList;
        this.mContext = mContext;
    }

   public static class ViewHolder extends RecyclerView.ViewHolder{
        View replyView;
        ImageView replyerHeadIcon;
        TextView replyerNickname;
        TextView replyerTime;
        Button replyBtn;
        TextView replyContent;
        ImageView commodityImage;
        TextView commodityDetails;
       public ViewHolder(View itemView) {
           super(itemView);
           replyView=itemView;
           replyerHeadIcon=(ImageView)itemView.findViewById(R.id.reply_headIcon);
           replyerNickname=(TextView)itemView.findViewById(R.id.reply_replyerName);
           replyerTime=(TextView)itemView.findViewById(R.id.reply_time);
           replyBtn=(Button)itemView.findViewById(R.id.reply_button);
           replyContent=(TextView)itemView.findViewById(R.id.reply_content);
           commodityImage=(ImageView)itemView.findViewById(R.id.reply_picture);
           commodityDetails=(TextView)itemView.findViewById(R.id.reply_commodity_detail);
       }
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        holder.replyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //弹出一个edittext框，输入回复内容
            }
        });
        return holder;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return ReplyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReplyItem replyItem=ReplyList.get(position);
        holder.replyerHeadIcon.setImageBitmap(BitmapFactory.decodeByteArray(replyItem.getReply_icon(),0,replyItem.getReply_icon().length));
        holder.replyerNickname.setText(replyItem.getReply_name());
        holder.replyerTime.setText(replyItem.getReply_time().toString());
        holder.replyContent.setText(replyItem.getReply_content());
        holder.commodityImage.setImageBitmap(BitmapFactory.decodeByteArray(replyItem.getCom_pic(),0,replyItem.getCom_pic().length));
        holder.commodityDetails.setText(replyItem.getCom_detail());
    }
}
