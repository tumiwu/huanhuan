package com.example.a50067.huanhuan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a50067.huanhuan.Entity.PersonnalOtherItems;
import com.example.a50067.huanhuan.R;

import java.util.List;

/**
 * Created by 50067 on 2018/6/3.
 */

public class PersonnalOtherItemsAdapter extends ArrayAdapter<PersonnalOtherItems>{
    private int resourceId;

    public PersonnalOtherItemsAdapter(Context context,int textViewResourceId, List<PersonnalOtherItems> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId =textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonnalOtherItems items=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.itemsName=(TextView)view.findViewById(R.id.personnal_items_name);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.itemsName.setText(items.getOtherItemsName());
        return view;
    }
    class ViewHolder{
        TextView itemsName;
    }
}
