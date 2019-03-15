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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.a50067.huanhuan.Entity.Commodity;
import com.example.a50067.huanhuan.MyApplication;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.SQLTable.TBCommodity;
import com.example.a50067.huanhuan.SQLTable.TBOrder;
import com.example.a50067.huanhuan.SQLTable.TBUser;
import com.example.a50067.huanhuan.View.CommodityActivity;

import org.litepal.LitePal;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 50067 on 2018/6/16.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private static final String TAG = "OrderItemAdapter";
private List<Commodity> orderComList;
private Context mComContext;
private int orderCategory;
private Button orderBtn;
private LinearLayout orderBtnLayout;
private TextView orderStateText;
public OrderItemAdapter(List<Commodity> orderComList, Context context,int category) {
        this.orderComList = orderComList;
        this.mComContext=context;
        orderCategory=category;     // 0:我的发布  1:我卖出的  2:我买到的
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
        cImage=(ImageView)itemView.findViewById(R.id.order_item_image);
        cName=(TextView)itemView.findViewById(R.id.order_item_cTitle);
        cPrice=(TextView)itemView.findViewById(R.id.order_item_cPrice);
        userName=(TextView)itemView.findViewById(R.id.order_item_seller_buyer);


    }
}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout,parent,false);
        orderBtn=(Button)view.findViewById(R.id.order_item_Btn_text);
        orderBtnLayout=(LinearLayout)view.findViewById(R.id.order_item_Btn_layout);
        orderStateText=(TextView)view.findViewById(R.id.order_item_state_text);
        switch (orderCategory){     // 0:我的发布  1:我卖出的  2:我买到的
            case 0:

                orderBtn.setText("删除商品");
                break;
            case 1:


                orderBtn.setText("更改交易状态");
                break;
            case 2:
                orderBtn.setClickable(false);
                orderBtn.setText("联系卖家");    //默认未完成
                break;
        }
        final ViewHolder holder=new ViewHolder(view);

     /*   holder.comView.setOnLongClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());   //
                    builder.setMessage(mComContext.getString(R.string.dontStar));
                    builder.setPositiveButton(mComContext.getString(R.string.positiveBtn), (dialog, which) -> {

                        int position = holder.getAdapterPosition();
                        Commodity starCom = orderComList.get(position);

                        //通过starCom获取id,TBStar设置删除标记为1
                        TBStar tbStar = new TBStar();
                        tbStar.setStarDelete(1);
                        tbStar.updateAll("userId = ? and commodityId = ?", MyApplication.getUserId() + "", starCom.getcId() + "");

                        removeStarCom(position);

                    });

                    builder.setNegativeButton(mComContext.getString(R.string.negativeBtn), ((dialog, which) -> {

                    }));
                    builder.create().show();
                    return false;
                }

        );*/
        holder.comView.setOnClickListener(v ->  {
            int position=holder.getAdapterPosition();
            Commodity commodity=orderComList.get(position);
            Log.d("ComAdapter", "onClick: "+position);
                /*
                * 点击获取position的对象，获取对象属性的id值，跳转到AC,通过Id填充*/
            Intent intent=new Intent(mComContext, CommodityActivity.class);
            Log.d("OrderAdapter:        ", "onCreateViewHolder: comId "+commodity.getcId()+commodity.getcName());
            intent.putExtra("comId",commodity.getcId()); //传递id值
            mComContext.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Commodity mCom = orderComList.get(position);
        holder.cImage.setImageBitmap(BitmapFactory.decodeByteArray(mCom.getcImage(), 0, mCom.getcImage().length));
        holder.cName.setText(mCom.getcName());
        holder.cPrice.setText(mCom.getcPrice());
        holder.userName.setText(mCom.getUserName());
        switch (orderCategory) {     // 0:我的发布  1:我卖出的  2:我买到的
            case 0:
//                orderBtn.setText("删除商品");
                orderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        TBCommodity tbCommodity=new TBCommodity();
                        tbCommodity.setcDelete(1);
                        tbCommodity.update(orderComList.get(position).getcId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                orderComList.remove(position);
                                notifyDataSetChanged();
                                mComContext.sendBroadcast(new Intent("refresh_recyclerview_data"));
                            }
                        });

//                        TBCommodity commodity = LitePal.find(TBCommodity.class, orderComList.get(position).getcId());
//                        commodity.setcDelete(1);
//                        commodity.save();

                    }
                });
                break;
            case 1:
//                orderBtn.setText("更改交易状态");       //弹出“已完成”，“已取消”
                orderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(mComContext);
                        builder.setTitle("更改交易状态");
                        String[] states={"已完成","已取消"};
                        builder.setItems(states, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:         //点击已完成，按钮内容更改，并且不能按

                                        String id=orderComList.get(position).getcId();
                                        BmobQuery<TBOrder> orderBmobQuery=new BmobQuery<TBOrder>();
                                        orderBmobQuery.addWhereEqualTo("comId",id);
                                        orderBmobQuery.findObjects(new FindListener<TBOrder>() {
                                            @Override
                                            public void done(List<TBOrder> list, BmobException e) {
                                                TBOrder tbOrder=list.get(0);
                                                tbOrder.setOrState(1);
                                                tbOrder.update(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        Toast.makeText(mComContext,"订单已完成",Toast.LENGTH_SHORT).show();
                                                        orderBtn.setText("订单已完成");
                                                    }
                                                });
                                            }
                                        });

//                                        List<TBOrder> orderList=LitePal.where("comId=?",id).find(TBOrder.class);
//                                        Log.d(TAG, "onClick: adapter get order size(查询出来应该只有1个)"+orderList.size());
//                                        Log.d(TAG, "onClick: adapter get order 0 "+orderList.get(0));
//                                        TBOrder order=orderList.get(0);
//                                        order.setOrState(1);
//                                        orderBtn.setText("订单已完成");

                                        break;
                                    case 1:
                                        String id1=orderComList.get(position).getcId();
                                        BmobQuery<TBOrder> tbOrderBmobQuery=new BmobQuery<>();
                                        tbOrderBmobQuery.addWhereEqualTo("comId",id1);
                                        tbOrderBmobQuery.findObjects(new FindListener<TBOrder>() {
                                            @Override
                                            public void done(List<TBOrder> list, BmobException e) {
                                                TBOrder tbOrder=list.get(0);
                                                tbOrder.setOrState(2);
                                                tbOrder.update(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        orderBtn.setText("订单已取消");
                                                        Toast.makeText(mComContext,"订单已取消",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });

                                       /* Toast.makeText(mComContext,"订单已取消",Toast.LENGTH_SHORT).show();


                                        List<TBOrder> orderList1=LitePal.where("comId=?",id1).find(TBOrder.class);
                                        Log.d(TAG, "onClick: 取消adapter get order size(查询出来应该只有1个)"+orderList1.size());
                                        Log.d(TAG, "onClick:取消 adapter get order 0 "+orderList1.get(0));
                                        TBOrder order1=orderList1.get(0);
                                        order1.setOrState(2);
                                        orderBtn.setText("订单已取消");*/
                                        break;
                                }
                            }
                        });
                        builder.show();

                    }
                });
                break;
            case 2:
                //左边增加字：状态栏
                String id=orderComList.get(position).getcId();
                BmobQuery<TBOrder> tbOrderBmobQuery=new BmobQuery<>();
                tbOrderBmobQuery.addWhereEqualTo("comId",id);
                tbOrderBmobQuery.findObjects(new FindListener<TBOrder>() {
                    @Override
                    public void done(List<TBOrder> list, BmobException e) {
                        TBOrder order=list.get(0);
                        switch (order.getOrState()){
                            case 0:
                                orderStateText.setText("未完成");
                                break;
                            case 1:
                                orderStateText.setText("已完成");
                                break;
                            case 2:
                                orderStateText.setText("已取消");
                                break;
                        }

                        orderBtn.setText("联系卖家");       //弹出卖家联系方式
                        orderBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BmobQuery<TBUser> tbUserBmobQuery=new BmobQuery<>();
                                tbUserBmobQuery.getObject(order.getSellerId(), new QueryListener<TBUser>() {
                                    @Override
                                    public void done(TBUser tbUser, BmobException e) {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(mComContext);
                                        builder.setTitle("买家手机：");
                                        builder.setMessage(tbUser.getuTel());
                                        builder.show();
                                    }
                                });
                               /* TBUser tbUser=LitePal.find(TBUser.class,order.getSellerId());
                                AlertDialog.Builder builder=new AlertDialog.Builder(mComContext);
                                builder.setTitle("买家手机：");
                                builder.setMessage(tbUser.getuTel());
                                builder.show();*/
                            }
                        });
                    }
                });
//                List<TBOrder> orderList=LitePal.where("comId=?",id).find(TBOrder.class);
//                TBOrder order=orderList.get(0);

                break;
        }
    }
    @Override
    public int getItemCount() {
        return orderComList.size();
    }

   /* public void removeCom(int position) {
        orderComList.remove(position);
        notifyItemRemoved(position);
    }*/

}
