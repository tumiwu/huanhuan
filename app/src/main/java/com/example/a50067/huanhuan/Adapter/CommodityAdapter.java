package com.example.a50067.huanhuan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.View.CommodityActivity;
import com.example.a50067.huanhuan.View.MainActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by 50067 on 2018/5/29.
 */

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolder> {
    private List<Commodity> commodityList;
    private Context mComContext;
    public CommodityAdapter(List<Commodity> commodityList, Context context) {
        this.commodityList = commodityList;
        this.mComContext=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View comView;
        ImageView cImage;
        TextView cImageExchangeable;
        TextView cName;
        TextView cPrice;
        TextView cDetails;
        TextView cUpDate;
        TextView userName;
        TextView userSchool;

        public ViewHolder(View itemView) {
            super(itemView);
            comView=itemView;
            cImage=(ImageView)itemView.findViewById(R.id.commodity_image);
            cImageExchangeable=(TextView)itemView.findViewById(R.id.commodity_exchangeable);
            cName=(TextView)itemView.findViewById(R.id.commodity_name);
            cPrice=(TextView)itemView.findViewById(R.id.commodity_price);
            cDetails=(TextView)itemView.findViewById(R.id.commodity_details);
            cUpDate=(TextView)itemView.findViewById(R.id.commodity_upload_date);
            userName=(TextView)itemView.findViewById(R.id.commodity_user_name);
            userSchool=(TextView)itemView.findViewById(R.id.commodity_user_school);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commodity_item_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.comView.setOnClickListener(v -> {
                int position=holder.getAdapterPosition();
                Commodity commodity=commodityList.get(position);
                Log.d("ComAdapter", "onClick:你点击了 "+position);
                /*
                * 点击获取position的对象，获取对象属性的id值，跳转到AC,通过Id填充*/
                Intent intent=new Intent(mComContext, CommodityActivity.class);
                intent.putExtra("comId",commodity.getcId()); //传递id值
            Log.d("Com Adapter", "onCreateViewHolder:  comId"+commodity.getcId());
                mComContext.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Commodity mCom=commodityList.get(position);
//        holder.cImage.setImageBitmap(BitmapFactory.decodeByteArray(mCom.getcImage(),0,mCom.getcImage().length));
        holder.cImage.setImageBitmap(byteToBitmap(mCom.getcImage()));
        holder.cImageExchangeable.setText(mCom.getcExchangeable());
        holder.cName.setText(mCom.getcName());
        holder.cPrice.setText(mCom.getcPrice());
        holder.cDetails.setText(mCom.getcDetails());
        holder.userName.setText(mCom.getUserName());
        holder.userSchool.setText(mCom.getUserSchool());
        holder.cUpDate.setText(mCom.getcUploadDate().toString());
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }

    public static Bitmap byteToBitmap(byte[] imgByte) {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }


}
