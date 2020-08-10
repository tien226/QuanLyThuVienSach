package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.R;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<Sach> sachList;
    List<Sach> arrSortsach;
    Filter sachfilter;
    SachDao sachDao;

    public SachAdapter(Context context, List<Sach> sachList) {
        this.context = context;
        this.sachList = sachList;
        this.arrSortsach = sachList;
        sachDao = new SachDao(context);
    }

    @Override
    public int getCount() {
        return sachList.size();
    }

    @Override
    public Sach getItem(int position) {
        return sachList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class viewHolder {
        TextView tv_tenSach, tv_soLuong;
//        ImageView img_close_sach;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder holder;
        final Sach sach = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sachadapter, parent, false);
            holder = new viewHolder();
            holder.tv_tenSach = convertView.findViewById(R.id.ten_sach);
            holder.tv_soLuong = convertView.findViewById(R.id.Gia_ban_sach);
//            holder.img_close_sach = convertView.findViewById(R.id.close_sach);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.tv_tenSach.setText("Tên Sách: "+sach.getTenSach());
        holder.tv_soLuong.setText(String.valueOf("Số Lượng: "+sach.getSoLuong()));
//        holder.img_close_sach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                DatabaseHelper databaseHelper = new DatabaseHelper(parent.getContext());
//                SachDao sachDAO = new SachDao(context);
//                sachDAO.deleteSach(sach.getMaSach());
//                changeDataset(sachDAO.getAllSach());
//            }
//        });
        return convertView;
    }

    public void resetData(){
        this.sachList = arrSortsach;
    }

    @Override
    public Filter getFilter() {
        if (sachfilter == null)
            sachfilter = new Customfilter();
        return sachfilter;
    }

    public class Customfilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                filterResults.values = arrSortsach;
                filterResults.count = arrSortsach.size();
            }else {
                List<Sach> lssach = new ArrayList<>();
                for (Sach sach : sachList){
                    if (sach.getTenSach().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lssach.add(sach);
                }
                filterResults.values = lssach;
                filterResults.count = lssach.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            if (filterResults.count == 0)
                notifyDataSetInvalidated();
            else {
                sachList = (List<Sach>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Sach> items) {
        this.sachList = items;
        notifyDataSetChanged();
    }
}
