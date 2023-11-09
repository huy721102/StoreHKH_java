package com.example.storehkh.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.storehkh.R;
import com.example.storehkh.activities.DetailedActivity;
import com.example.storehkh.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel>list;
    int totalAmount=0;

    public MyCartAdapter(Context context,List<MyCartModel>list){
        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        //chú ý phần này
//thêm code chỗ này
// ...

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                // Thêm thông tin sản phẩm vào Intent
                intent.putExtra("detailed", list.get(position));
                context.startActivity(intent);

            }
        });

        holder.date.setText(list.get(position).getNgay());
        holder.time.setText(list.get(position).getGio());
        holder.price.setText(list.get(position).getGiasanpham()+"đ");
        holder.name.setText(list.get(position).getTensanpham());
        holder.totalQuantity.setText(list.get(position).getTongsoluong());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTongtien())+"đ");

        //total amount pass to Cart Activity
        totalAmount = totalAmount +list.get(position).getTongtien();
        Intent intent =new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {return list.size();}
        public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,totalQuantity,totalPrice;
        ImageView hinh;
        public ViewHolder(@NonNull View itemView){
          super(itemView);
          hinh=itemView.findViewById(R.id.detailed_img);
          name=itemView.findViewById(R.id.product_name);
          price=itemView.findViewById(R.id.product_price);
          date= itemView.findViewById(R.id.current_date);
          time=itemView.findViewById(R.id.current_time);
          totalQuantity=itemView.findViewById(R.id.total_quantity);
          totalPrice=itemView.findViewById(R.id.total_price);
        }
    }
}
