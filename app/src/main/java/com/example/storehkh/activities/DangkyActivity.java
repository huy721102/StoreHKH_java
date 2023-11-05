package com.example.storehkh.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storehkh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangkyActivity extends AppCompatActivity {

    EditText ten,email,password;
    private FirebaseAuth auth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        //getSupportActionBar().hide(); //nên kiểm tra lại dòng code này bởi vì có nó sẽ không chạy được chương trình

        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(DangkyActivity.this,MainActivity.class));
            finish();
        }//khúc tắt để đăng nhâp

        ten= findViewById(R.id.ten);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);

        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if(isFirstTime){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent =new Intent(DangkyActivity.this,OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void dangky(View view) {

        String userName =ten.getText().toString();
        String userEmail =email.getText().toString();
        String userPassword =password.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Nhập Tên!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Nhập địa chỉ Email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Nhập password!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length() < 6){
            Toast.makeText(this, "Password qué ngắn ,vui lòng nhập 6 ký tự trở lên!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(DangkyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(DangkyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(DangkyActivity.this,MainActivity.class));
                        }else {
                            Toast.makeText(DangkyActivity.this, "Đăng ký không thành công"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //startActivity(new Intent(DangkyActivity.this,MainActivity.class));
    }

    public void dangnhap(View view) {
        startActivity(new Intent(DangkyActivity.this,LoginActivity.class));

    }
}