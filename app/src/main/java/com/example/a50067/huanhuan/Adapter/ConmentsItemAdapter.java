package com.example.a50067.huanhuan.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a50067.huanhuan.Entity.CommentsItem;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBComComments;

import java.util.List;

public class ConmentsItemAdapter {
//    private List<CommentsItem> commentsItemList;
//    private Context
//            mComContext;
//    public ConmentsItemAdapter(List<CommentsItem> commentsItemList, Context context) {
//        this.commentsItemList = commentsItemList;
//        this.mComContext=context;
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder{
//        View comView;
//        TextView uName;
//        TextView toUserName;
//        TextView comContents;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            comView=itemView;
//
//            uName=(TextView)itemView.findViewById(R.id.comments_uName);
//            toUserName=(TextView)itemView.findViewById(R.id.comments_toUserName);
//            comContents=(TextView)itemView.findViewById(R.id.comments_content);
//
//
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item_layout,parent,false);
//        final ViewHolder holder=new ViewHolder(view);
//
//        holder.comView.setOnLongClickListener(v -> {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());   //
//                    builder.setMessage("回复");
//                    builder.setPositiveButton("确认", (dialog, which) -> {
//
//                        int position = holder.getAdapterPosition();
//                         CommentsItem commentsItem= commentsItemList.get(position);
//
//                        //通过starCom获取id,TBStar设置删除标记为1
//                        /*这里错了*/
//                        TBComComments tbComComments = new TBComComments();
//                        tbComComments.
//
//                        removeStarCom(position);
//
//                    });
//
//                    builder.setNegativeButton(mComContext.getString(R.string.negativeBtn), ((dialog, which) -> {
//
//                    }));
//                    builder.create().show();
//                    return false;
//                }
//
//        );
//        holder.comView.setOnClickListener(v ->  {
//            int position=holder.getAdapterPosition();
//            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());   //
//            builder.setMessage("回复");
//            builder.setPositiveButton("确认", (dialog, which) -> {
//
//                int position = holder.getAdapterPosition();
//                CommentsItem commentsItem= commentsItemList.get(position);
//
//                //通过starCom获取id,TBStar设置删除标记为1
//                /*这里错了*/
//                TBComComments tbComComments = new TBComComments();
//                tbComComments.
//
//                        removeStarCom(position);
//
//            });
//
//            builder.setNegativeButton(mComContext.getString(R.string.negativeBtn), ((dialog, which) -> {
//
//            }));
//            builder.create().show();
//            /*
//             * 点击获取position的对象，获取对象属性的id值，跳转到AC,通过Id填充*/
//            Intent intent=new Intent(mComContext, CommodityActivity.class);
//            intent.putExtra("comId",commodity.getcId()); //传递id值
//            mComContext.startActivity(intent);
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(StarComAdapter.ViewHolder holder, int position) {
//        Commodity mCom=starComList.get(position);
//        holder.cImage.setImageBitmap(BitmapFactory.decodeByteArray(mCom.getcImage(),0,mCom.getcImage().length));
//        holder.cName.setText(mCom.getcName());
//        holder.cPrice.setText(mCom.getcPrice());
//        holder.userName.setText(mCom.getUserName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return starComList.size();
//    }
//
//    public void removeStarCom(int position) {
//        starComList.remove(position);
//        notifyItemRemoved(position);
//    }

}

