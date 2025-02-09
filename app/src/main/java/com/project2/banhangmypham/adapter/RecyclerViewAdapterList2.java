//package com.project2.banhangmypham.adapter;
//
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.project2.banhangmypham.activity.CategoryActivity;
//import com.project2.banhangmypham.CategoryFragment.ListHomeFragment;
//import com.project2.banhangmypham.R;
//
//import java.util.List;
//
//
//public class RecyclerViewAdapterList2 extends RecyclerView.Adapter<RecyclerViewAdapterList2.MyViewHolder> {
//
//    private ListHomeFragment mContext;
//    private List<Category2> mData;
//    Intent intent;
//
//    public RecyclerViewAdapterList2(ListHomeFragment mContext, List<Category2> mData) {
//        this.mContext = mContext;
//        this.mData = mData;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater mInflater = LayoutInflater.from(mContext.getActivity());
//        view = mInflater.inflate(R.layout.cardview_danhmuc, parent, false);
//        return new MyViewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        holder.tv_category_title.setText(mData.get(position).getTitle());
//        holder.img_category_thumbnail.setImageResource(mData.get(position).getThumbnail());
//        intent = new Intent(mContext.getActivity(), CategoryActivity.class);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (mData.get(position).getTitle()) {
//                    case "Điện thoại - Máy tính":
//                        intent.putExtra("DanhMuc", "Điện thoại - Máy tính");
//                        mContext.startActivity(intent);
//                        break;
//                    case "Thời trang":
//                        intent.putExtra("DanhMuc", "Quần áo - Thời trang");
//                        mContext.startActivity(intent);
//                        break;
//                    case "Sách":
//                        intent.putExtra("DanhMuc", "Sách - Văn phòng phẩm");
//                        mContext.startActivity(intent);
//                        break;
//                    case "Làm đẹp - Sức khỏe":
//                        intent.putExtra("DanhMuc", "Sức khỏe - Làm đẹp");
//                        mContext.startActivity(intent);
//                        break;
//                    case "Nhà cửa - Nội thất":
//                        intent.putExtra("DanhMuc", "Nhà cửa - Đồ gia dụng");
//                        mContext.startActivity(intent);
//                        break;
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tv_category_title;
//        ImageView img_category_thumbnail;
//        LinearLayout cardView;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//
//            tv_category_title = (TextView) itemView.findViewById(R.id.danhmuc_title_id);
//            img_category_thumbnail = (ImageView) itemView.findViewById(R.id.danhmuc_img_id);
//            cardView = (LinearLayout) itemView.findViewById(R.id.cardview_danhmuc);
//        }
//    }
//
//
//}
