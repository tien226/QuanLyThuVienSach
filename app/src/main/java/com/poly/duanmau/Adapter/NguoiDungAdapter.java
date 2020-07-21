package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.Model.NguoiDung;
import com.poly.duanmau.R;

import java.util.List;

public class NguoiDungAdapter extends BaseAdapter {
    List<NguoiDung> nguoiDungList;
    Context context;

    public NguoiDungAdapter(List<NguoiDung> nguoiDungList, Context context) {
        this.nguoiDungList = nguoiDungList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return nguoiDungList.size();
    }

    @Override
    public NguoiDung getItem(int position) {
        return nguoiDungList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class viewHolder {
        TextView phone, hoTen;
        ImageView icon_delete;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        viewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nguoidungadapter, parent, false);
            holder = new viewHolder();
            holder.phone = convertView.findViewById(R.id.sdt_nguoi_dung);
            holder.hoTen = convertView.findViewById(R.id.ten_nguoi_dung);
            holder.icon_delete = convertView.findViewById(R.id.close_nguoiDung);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.hoTen.setText("Họ tên: "+nguoiDungList.get(position).getHoTen());
        holder.phone.setText("Sđt: "+nguoiDungList.get(position).getPhone());

        // Xóa dữ liệu
//        holder.icon_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                DatabaseHelper databaseHelper = new DatabaseHelper(parent.getContext());
//                NguoiDungDao nguoiDungDAO = new NguoiDungDao(context);
//                nguoiDungDAO.deleteNguoiDung(nguoiDungList.get(position).getUserName());
//                changeDataset(nguoiDungDAO.getAllNguoiDung());
//            }
//        });
        return convertView;
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<NguoiDung> items) {
        this.nguoiDungList = items;
        notifyDataSetChanged();
    }

}
