package com.example.a50067.huanhuan.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.Entity.User;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBStar;
import com.example.a50067.huanhuan.SQLTable.TBUser;
import com.example.a50067.huanhuan.View.CommodityActivity;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 50067 on 2018/6/11.
 */

public class StarComAdapter extends RecyclerView.Adapter<StarComAdapter.ViewHolder> {
    private static final String TAG = "StarComAdapter";
    private List<Commodity> starComList;
    private Context mComContext;
    public StarComAdapter(List<Commodity> starComList, Context context) {
        this.starComList = starComList;
        this.mComContext=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View comView;
        ImageView cImage;
        TextView cName;
        TextView cPrice;
        TextView userName;

        public ViewHolder(View itemView) {
            super(itemView);
            comView=itemView;
            cImage=(ImageView)itemView.findViewById(R.id.star_item_image);
            cName=(TextView)itemView.findViewById(R.id.star_item_cName);
            cPrice=(TextView)itemView.findViewById(R.id.star_item_cPrice);
            userName=(TextView)itemView.findViewById(R.id.star_item_user);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.star_com_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.comView.setOnLongClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());   //
                    builder.setMessage(mComContext.getString(R.string.dontStar));
                    builder.setPositiveButton(mComContext.getString(R.string.positiveBtn), (dialog, which) -> {

                        int position = holder.getAdapterPosition();
                        Commodity starCom = starComList.get(position);

                        //通过starCom获取id,TBStar设置删除标记为1
                        /*这里错了*/
                        BmobQuery<TBStar> tbStarBmobQuery=new BmobQuery<>();
                        tbStarBmobQuery.addWhereEqualTo("starDelete",0);
                        tbStarBmobQuery.addWhereEqualTo("commodityId",starCom.getcId());
                        tbStarBmobQuery.addWhereEqualTo("userId",MyApplication.getUserObjectId());
                        tbStarBmobQuery.findObjects(new FindListener<TBStar>() {
                            @Override
                            public void done(List<TBStar> list, BmobException e) {
                            //  一个商品只能被同一人收藏一次，所以搜索出List.size为1
                            if(e==null){
                                TBStar tbStar = new TBStar();
                                tbStar=list.get(0);
                                tbStar.setStarDelete(1);
                                tbStar.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                      removeStarCom(position);
                                    }
                                });

                            }   else {

                            } 
                            }
                        });
//                        TBStar tbStar = new TBStar();
//                        tbStar.setStarDelete(1);
//                        tbStar.updateAll("userId = ? and commodityId = ?", MyApplication.getUserId() + "", starCom.getcId() + "");



                    });

                    builder.setNegativeButton(mComContext.getString(R.string.negativeBtn), ((dialog, which) -> {

                    }));
                    builder.create().show();
                    return false;
                }

        );
        holder.comView.setOnClickListener(v ->  {
            int position=holder.getAdapterPosition();
            Commodity commodity=starComList.get(position);
            Log.d("ComAdapter", "onClick: "+position);
            /*
             * 点击获取position的对象，获取对象属性的id值，跳转到AC,通过Id填充*/
            Intent intent=new Intent(mComContext, CommodityActivity.class);
            intent.putExtra("comId",commodity.getcId()); //传递id值
            mComContext.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(StarComAdapter.ViewHolder holder, int position) {
        Commodity mCom=starComList.get(position);
        holder.cImage.setImageBitmap(BitmapFactory.decodeByteArray(mCom.getcImage(),0,mCom.getcImage().length));
        holder.cName.setText(mCom.getcName());
        holder.cPrice.setText(mCom.getcPrice());
        holder.userName.setText(mCom.getUserName());
    }

    @Override
    public int getItemCount() {
        return starComList.size();
    }

    public void removeStarCom(int position) {
        starComList.remove(position);
        notifyItemRemoved(position);
    }

}