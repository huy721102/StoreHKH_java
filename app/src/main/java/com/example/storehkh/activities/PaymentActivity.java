package com.example.storehkh.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storehkh.R;
import com.example.storehkh.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    double amount=0.0;
    Toolbar toolbar;
    TextView subTotal,discount,shipping,total;
    Button paymentBtn;
    protected boolean shouldClearCartAutomatically = true;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    List<MyCartModel> cartModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //toolbar
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        cartModelList = new ArrayList<>();
        toolbar=findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String uid = auth.getUid();

        Intent intent=getIntent();
        subTotal=findViewById(R.id.sub_total);
        discount=findViewById(R.id.textView17);
        shipping=findViewById(R.id.textView18);
        total=findViewById(R.id.total_amt);
        paymentBtn=findViewById(R.id.pay_btn);
        amount=intent.getDoubleExtra("amount",-1);
        if (amount != -1){
            subTotal.setText(amount+"đ");
        }else {
            firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                    .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {

                                    MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                                    cartModelList.add(myCartModel);

                                }
                                int sumTotal =0;
                                for (MyCartModel item:cartModelList) {
                                    sumTotal+= item.getTongtien();
                                }
                                subTotal.setText(sumTotal+"đ");
                            }
                        }
                    });
        }





        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //note lại chổ này coi có cần xoá số 0.0 đó ko


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentMethod();
            }
        });
    }

    private void paymentMethod() {

        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_h0Lz3CRtcxmriG");

        final Activity activity = PaymentActivity.this;

        try {
            JSONObject options = new JSONObject();
            //Set Company Name
            options.put("ten", "StoreHKH App");
            //Ref no
            options.put("Thong tin", "ko có gì cả");
            //Image to be display
            options.put("Hinh anh", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_9A33XWu170gUtm");
            // Currency type
            options.put("don vi", "đ");
            //double total = Double.parseDouble(mAmountText.getText().toString());
            //multiply with 100 to get exact amount in rupee
            amount = amount * 100;
            //amount
            options.put("amount", "1000");
            JSONObject preFill = new JSONObject();
            //email
            preFill.put("email", "phamhuy721102@gmail.com");
            //contact
            preFill.put("lien lac", "0398343658");

            options.put("prefill", preFill);
            options.put("currency", "USD");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

//        /**
//         * Instantiate Checkout
//         */
//        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_test_h0Lz3CRtcxmriG");
//        /**
//         * Set your logo here
//         */
//       // checkout.setImage(R.drawable.logo);
//
//        /**
//         * Reference to current activity
//         */
//        final Activity activity = this;
//
//        /**
//         * Pass your payment options to the Razorpay Checkout as a JSONObject
//         */
//        try {
//            JSONObject options = new JSONObject();
//               //nên coi lại may cái chổ này
//            options.put("name", "StoreHKH");
//            options.put("description", "xin chào");
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            //from response of step 3.
//            options.put("theme.color", "#3399cc");
//            options.put("currency", "INR"); nayf phải nâng cấp tk mới chuyen doi đc tiền tệ
//            options.put("amount", "50000");//pass amount in currency subunits
//            options.put("email", "phamhuy721102@gmail.com");
//            options.put("contact","0398343658");
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);
//
//            checkout.open(activity, options);
//
//        } catch(Exception e) {
//            Log.e("TAG", "Error in starting Razorpay Checkout", e);
//        }
    }




    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

        // Sử dụng Intent để chuyển sang CartActivity và gọi phương thức clearCart()
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("clearCart", true); // Truyền thông báo xóa giỏ hàng
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Thanh toán bị huỷ", Toast.LENGTH_SHORT).show();
    }
}