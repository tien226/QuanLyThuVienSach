package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.Model.TheLoai;
import com.poly.duanmau.R;

import java.util.List;

public class TheLoaiAdapter extends BaseAdapter {
    Context context;
    List<TheLoai> theLoaiList;

    public TheLoaiAdapter(Context context, List<TheLoai> theLoaiList) {
        this.context = context;
        this.theLoaiList = theLoaiList;
    }

    @Override
    public int getCount() {
        return theLoaiList.size();
    }

    @Override
    public TheLoai getItem(int position) {
        return theLoaiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class viewHolder {
        TextView matheloai, tentheloai;
        ImageView icon_delete_tl;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder hoder;
        final TheLoai theLoai = theLoaiList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.theloaiadapter, parent, false);
            hoder = new viewHolder();
            hoder.tentheloai = convertView.findViewById(R.id.ten_the_loai);
            hoder.matheloai = convertView.findViewById(R.id.ma_theLoai);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHolder) convertView.getTag();
        }
        hoder.matheloai.setText("Mã Thể Loại: "+theLoai.getMaTheLoai());
        hoder.tentheloai.setText("Tên thể loại: "+theLoai.getTenTheLoai());
//        hoder.icon_delete_tl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                DatabaseHelper databaseHelper = new DatabaseHelper(parent.getContext());
//                TheLoaiDao theLoaiDAO = new TheLoaiDao(context);
//                theLoaiDAO.deleteTheLoai(theLoai.getMaTheLoai());
//                changeDataset(theLoaiDAO.getAllTheLoai());
//            }
//        });
        return convertView;
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<TheLoai> items) {
        this.theLoaiList = items;
        notifyDataSetChanged();
    }
}
