package com.example.myshopping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth myAuth;
    private EditText edLogin, edPass;
    private TextView tvUserEmail;
    private Button bStart, bSignUp, bSign, bSignOut;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser= myAuth.getCurrentUser();
        Log.d("MyLog", "signUp");
        if(cUser!=null){
            tvUserEmail.setVisibility(View.VISIBLE);
            bStart.setVisibility(View.VISIBLE);
            edLogin.setVisibility(View.GONE);
            edPass.setVisibility(View.GONE);
            bSign.setVisibility(View.GONE);
            bSignUp.setVisibility(View.GONE);
            String userName = "вошли как: " + cUser.getEmail();
            tvUserEmail.setText(userName);


            Toast.makeText(this,"User not null", Toast.LENGTH_SHORT).show();
        }
        else {
            tvUserEmail.setVisibility(View.GONE);
            bStart.setVisibility(View.GONE);
            bSignOut.setVisibility(View.GONE);
            edLogin.setVisibility(View.VISIBLE);
            edPass.setVisibility(View.VISIBLE);
            bSign.setVisibility(View.VISIBLE);
            bSignUp.setVisibility(View.VISIBLE);
            Toast.makeText(this,"User  null", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickStart(View view){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);

    }
    public void onClickSignOut(View view){
        FirebaseAuth.getInstance().signOut();

        tvUserEmail.setVisibility(View.GONE);
        bStart.setVisibility(View.GONE);
        bSignOut.setVisibility(View.GONE);
        edLogin.setVisibility(View.VISIBLE);
        edPass.setVisibility(View.VISIBLE);
        bSign.setVisibility(View.VISIBLE);
        bSignUp.setVisibility(View.VISIBLE);

    }
    public void onClickSignUp(View view){

        if(!TextUtils.isEmpty(edLogin.getText().toString())&& !TextUtils.isEmpty((edPass.getText().toString()))) {

            //Log.d("MyLog", "signUp");
            myAuth.createUserWithEmailAndPassword(edLogin.getText().toString(),edPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Log.d("MyLog", "user signUp");
                        Toast.makeText(LoginActivity.this,"User  Signup", Toast.LENGTH_SHORT).show();
                        //FirebaseUser user =myAuth.getCurrentUser();
                    }
                    else {
                        Log.d("MyLog", "user not signUp");
                        Toast.makeText(LoginActivity.this,"User not Signup", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else {
            //Log.d("MyLog", "signUp");
        }
    }
    public void onClickSignIn(View view){
        if(!TextUtils.isEmpty(edLogin.getText().toString())&& !TextUtils.isEmpty((edPass.getText().toString()))) {

            //Log.d("MyLog", "signUp");
            myAuth.signInWithEmailAndPassword(edLogin.getText().toString(), edPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Log.d("MyLog", "user signUp");
                                Toast.makeText(LoginActivity.this, "User  SignIn", Toast.LENGTH_SHORT).show();
                                //FirebaseUser user =myAuth.getCurrentUser();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Log.d("MyLog", "user not signUp");
                                Toast.makeText(LoginActivity.this, "User not SignIn", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

        else {
            Toast.makeText(LoginActivity.this, "введите логин и пароль", Toast.LENGTH_SHORT).show();
        }
    }


    private void init(){
        bStart=findViewById(R.id.bStart);
        tvUserEmail=findViewById(R.id.tvEmailUser);
        bSignOut=findViewById(R.id.bSignOut);

        edLogin=findViewById(R.id.edLogin);
        edPass=findViewById(R.id.edPass);
        bSignUp=findViewById(R.id.bSignUp);
        bSign=findViewById(R.id.bSign);
        myAuth= FirebaseAuth.getInstance();
    }
}
