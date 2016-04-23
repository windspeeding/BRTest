package com.test.brtest.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.brtest.R;
import com.test.brtest.model.Store;

import java.util.List;

/**
 * Created by Feng on 4/21/2016.
 */
public class StoreListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Store> storeList;

    public StoreListAdapter(Activity activity, List<Store> storeList){
        this.activity = activity;
        this.storeList = storeList;
    }
    static class ViewHolder{
        ImageView iv_logo;
        TextView tv_number,tv_address1,tv_address2;
    }
    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int position) {
        return storeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return storeList.get(position).getStoreID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.iv_logo = (ImageView) convertView.findViewById(R.id.logo);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.phone_number);
            viewHolder.tv_address1 = (TextView) convertView.findViewById(R.id.address1);
            viewHolder.tv_address2 = (TextView) convertView.findViewById(R.id.address2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(activity)
                .load(storeList.get(position).getStoreLogoURL())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerInside()
                .resize(250,250).centerInside()
                .tag(activity)
                .into(viewHolder.iv_logo);
        viewHolder.tv_number.setText(getFormatNumber(storeList.get(position).getPhone()));
        if (storeList.get(position).getAddress().length()>22) {
            viewHolder.tv_address1.setText(storeList.get(position).getAddress().substring(0,21));
            viewHolder.tv_address2.setText(storeList.get(position).getAddress().substring(22));
        } else {
            viewHolder.tv_address1.setText(storeList.get(position).getAddress());
        }
        return convertView;
    }

    public String getFormatNumber (String rawNumber) {

        String formattedNumber = String.format("(%s) %s-%s",
                rawNumber.substring(0, 3),
                rawNumber.substring(4, 7),
                rawNumber.substring(8, 12));

        return formattedNumber;
    }
}
