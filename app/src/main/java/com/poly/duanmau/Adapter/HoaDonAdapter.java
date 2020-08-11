package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.HoaDonDao;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<HoaDon> hoaDonList;
    List<HoaDon> arrSorthoadon;
    Filter hoadonfilter;
    HoaDonDao hoaDonDao;

    public HoaDonAdapter(Context context, List<HoaDon> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
        this.arrSorthoadon = hoaDonList;
        hoaDonDao = new HoaDonDao(context);
    }

    @Override
    public int getCount() {
        return hoaDonList.size();
    }

    @Override
    public HoaDon getItem(int position) {
        return hoaDonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class viewHolder {
        TextView tv_mahoadon;
        TextView ngay_mua;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder holder;
        final HoaDon hoaDon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoadonadapter, parent, false);
            holder = new viewHolder();
            holder.tv_mahoadon = convertView.findViewById(R.id.maHoaDon);
            holder.ngay_mua = convertView.findViewById(R.id.ngayHD);

            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.tv_mahoadon.setText("Mã Hóa Đơn: "+hoaDon.getMaHoaDon());
        // chuyển ngày sang dạng String
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(hoaDon.getNgayMua());
        holder.ngay_mua.setText("Ngày Mua Hóa Đơn: "+s);


        //hiệu ứng listview
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_list);
        convertView.startAnimation(animation);
        return convertView;
    }

    public void resethoadon(){
        this.hoaDonList = arrSorthoadon;
    }

    @Override
    public Filter getFilter() {
        if (hoadonfilter == null)
            hoadonfilter = new Customfilter();
        return hoadonfilter;
    }

    public class Customfilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                filterResults.values = arrSorthoadon;
                filterResults.count = arrSorthoadon.size();
            }else {
                List<HoaDon> lshoadon = new ArrayList<>();
                for (HoaDon hoaDon : hoaDonList){
                    if (hoaDon.getMaHoaDon().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lshoadon.add(hoaDon);
                }
                filterResults.values = lshoadon;
                filterResults.count = lshoadon.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            if (filterResults.count == 0)
                notifyDataSetInvalidated();
            else {
                hoaDonList = (List<HoaDon>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDon> items) {
        this.hoaDonList = items;
        notifyDataSetChanged();
    }
}
