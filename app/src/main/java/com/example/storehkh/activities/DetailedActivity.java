package com.example.storehkh.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.storehkh.R;
import com.example.storehkh.models.MyCartModel;
import com.example.storehkh.models.NewProductsModel;
import com.example.storehkh.models.PopularProductsModel;
import com.example.storehkh.models.ShowAllModel;
import com.example.storehkh.utilz.ProductConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SimpleTimeZone;


public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;


    Toolbar toolbar;
    int totalQuantity=1;
    int totalPrice =0;

    //New products
    NewProductsModel newProductsModel=null;

    //Popular Products
    PopularProductsModel popularProductsModel = null;

    //Show all
    ShowAllModel showAllModel=null;

    FirebaseAuth auth;

    Object product;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        firestore = FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();
        //chú ý phần này !
        // Kiểm tra xem dữ liệu sản phẩm có được truyền từ giỏ hàng hay không
        Intent intent = getIntent();
        if (intent.hasExtra("detailed")) {
            Object obj = intent.getSerializableExtra("detailed");
            if(obj instanceof MyCartModel){
                MyCartModel a = (MyCartModel) obj;

                getProductById(a.getMaSanPham(),a.getType());
//                a=a;
            }
            if (obj instanceof NewProductsModel) {
                newProductsModel = (NewProductsModel) obj;
                // Hiển thị thông tin chi tiết sản phẩm
                // Update hình ảnh, tên, giá, mô tả, v.v.
            } else if (obj instanceof PopularProductsModel) {
                popularProductsModel = (PopularProductsModel) obj;
                // Hiển thị thông tin chi tiết sản phẩm
                // Update hình ảnh, tên, giá, mô tả, v.v.
            } else if (obj instanceof ShowAllModel) {
                showAllModel = (ShowAllModel) obj;
                // Hiển thị thông tin chi tiết sản phẩm
                // Update hình ảnh, tên, giá, mô tả, v.v.
            }
        }


        toolbar=findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Object obj = getIntent().getSerializableExtra("detailed");

        if(obj instanceof NewProductsModel){
          newProductsModel =(NewProductsModel) obj;
        }else if(obj instanceof PopularProductsModel){
            popularProductsModel =(PopularProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel =(ShowAllModel) obj;
        }

        detailedImg=findViewById(R.id.detailed_img);
        quantity=findViewById(R.id.quantity);
        name=findViewById(R.id.detailed_name);
        rating=findViewById(R.id.rating);
        description=findViewById(R.id.detailed_desc);
        price=findViewById(R.id.detailed_price);

        addToCart=findViewById(R.id.add_to_cart);
        buyNow  =findViewById(R.id.buy_now);

        addItems=findViewById(R.id.add_item);
        removeItems=findViewById(R.id.remove_item);

        //New products
        loadInfomation();
        //buy now(mua ngay)
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailedActivity.this,AddressActivity.class);

                if(newProductsModel != null){
                    intent.putExtra("item",newProductsModel);
                }
                if(popularProductsModel != null){
                    intent.putExtra("item",popularProductsModel);
                }
                if(showAllModel != null){
                    intent.putExtra("item",showAllModel);
                }
                startActivity(intent);

            }
        });

        //add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if(newProductsModel != null){
                        totalPrice = newProductsModel.getGiatien() * totalQuantity;
                    }
                    if(popularProductsModel != null){
                        totalPrice = popularProductsModel.getGiatien() * totalQuantity;
                    }
                    if(showAllModel != null){
                        totalPrice = showAllModel.getGiatien() * totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
    }
    private void loadInfomation(){
        if(newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getDanhgia());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getGiatien()));
            name.setText(newProductsModel.getName());

            totalPrice = newProductsModel.getGiatien() * totalQuantity;
        }

        //Popular Products (khúc này là lấy hình ảnh và thông tin trong firebase)
        if(popularProductsModel != null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getDanhgia());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getGiatien()));
            name.setText(popularProductsModel.getName());

            totalPrice = popularProductsModel.getGiatien() * totalQuantity;
        }
        //Show all Products (khúc này là lấy hình ảnh và thông tin trong firebase)
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getDanhgia());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getGiatien()));
            name.setText(showAllModel.getName());

            totalPrice = showAllModel.getGiatien() * totalQuantity;
        }
    }
    private void getProductById(String id,Integer type){
        if(type == ProductConstants.NEW_PRODUCT){
            CollectionReference r =firestore.collection("Sản phẩm mới");
            r.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.e("DATAAA","abcxyz" +documentSnapshot.toString());
                        newProductsModel = documentSnapshot.toObject(NewProductsModel.class);
                        loadInfomation();
                    }
                }});
        }else if(type == ProductConstants.PORPULAR_PRODUCT){
            CollectionReference r =firestore.collection("Sản phẩm");
            r.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.e("DATAAA","abcxyz" +documentSnapshot.toString());
                        popularProductsModel = documentSnapshot.toObject(PopularProductsModel.class);
                        loadInfomation();
                    }
                }});
        }else if(type ==ProductConstants.SHOW_ALL_PRODUCT){
            CollectionReference r =firestore.collection("ShowAll");
            r.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.e("DATAAA","abcxyz" +documentSnapshot.toString());
                        showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                        loadInfomation();
                    }
                }});
        }
    }
    private void addToCart() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate =Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate =currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("Tensanpham",name.getText().toString());
        cartMap.put("Giasanpham",price.getText().toString());
        cartMap.put("Gio",saveCurrentTime);
        cartMap.put("Ngay",saveCurrentDate);
        cartMap.put("Tongsoluong",quantity.getText().toString());
        cartMap.put("Tongtien",totalPrice);

        if(newProductsModel!=null){
            cartMap.put("MaSanPham",newProductsModel.getId());
            cartMap.put("Type", ProductConstants.NEW_PRODUCT);
        }else if(popularProductsModel!=null){
            cartMap.put("MaSanPham",popularProductsModel.getId());
            cartMap.put("Type", ProductConstants.PORPULAR_PRODUCT);
        }else if(showAllModel!=null){
            cartMap.put("MaSanPham",showAllModel.getId());
            cartMap.put("Type", ProductConstants.SHOW_ALL_PRODUCT);
        }

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}