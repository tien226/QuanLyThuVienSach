package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.HoaDonChiTietDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.HoaDonChiTiet;
import com.poly.duanmau.R;

import java.util.List;

public class HoaDonChiTietAdapter extends BaseAdapter {
    DatabaseHelper databaseHelper;
    HoaDonChiTietDao hoaDonChiTietDAO;
    Context context;
    List<HoaDonChiTiet> hoaDonChiTietList;

    public HoaDonChiTietAdapter(Context context, List<HoaDonChiTiet> hoaDonChiTietList) {
        this.context = context;
        this.hoaDonChiTietList = hoaDonChiTietList;
    }

    @Override
    public int getCount() {
        return hoaDonChiTietList.size();
    }

    @Override
    public HoaDonChiTiet getItem(int position) {
        return hoaDonChiTietList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder holder;
        final HoaDonChiTiet hoaDonChiTiet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoadonchitietadapter, parent, false);
            holder = new viewHolder();
            holder.maSach = convertView.findViewById(R.id.maSach_rowHDCT);
            holder.giaBan = convertView.findViewById(R.id.giaBan_rowHDCT);
            holder.soLuong = convertView.findViewById(R.id.soLuong_rowHDCT);
            holder.thanhTien = convertView.findViewById(R.id.thanhTien_rowHDCT);
//            holder.close_hoaDonChiTiet = convertView.findViewById(R.id.close_hoaDonChiTiet);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.maSach.setText("Mã sách: " + hoaDonChiTiet.getSach().getMaSach());
        holder.giaBan.setText("Giá bán: " + hoaDonChiTiet.getSach().getGiaBan());
        holder.soLuong.setText("Số lượng: " + hoaDonChiTiet.getSoLuongMua());
        holder.thanhTien.setText("Thành tiền: " + hoaDonChiTiet.getSach().getGiaBan() * hoaDonChiTiet.getSoLuongMua());
        holder.close_hoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                databaseHelper = new DatabaseHelper(parent.getContext());
                hoaDonChiTietDAO = new HoaDonChiTietDao(context);
                hoaDonChiTietDAO.deleteHDCT(String.valueOf(hoaDonChiTiet.getMaHDCT()));
                changeDataSet(hoaDonChiTietDAO.getAllHoaDonChiTiet());
            }
        });

        return convertView;
    }

    public class viewHolder {
        TextView maSach, soLuong, giaBan, thanhTien;
        ImageView close_hoaDonChiTiet;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataSet(List<HoaDonChiTiet> items) {
        this.hoaDonChiTietList = items;
        notifyDataSetChanged();
    }
}
