package com.exampledemo.Group02_Group03.coe125;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email_id;
    private EditText pass_id;

    private Button login_btn;
    private  Button reg_btn;

    private FirebaseAuth Auth;
   // private FirebaseAuth.AuthStateListener AuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Auth=FirebaseAuth.getInstance();

        email_id=(EditText) findViewById(R.id.email_id);
        pass_id=(EditText) findViewById(R.id.pass_id);

        login_btn=(Button)findViewById(R.id.login_btn);
        reg_btn=(Button)findViewById(R.id.reg_btn);
        Auth=FirebaseAuth.getInstance();
        //-----------start Login
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (Auth.signInWithEmailAndPassword(email_id.getText().toString(),pass_id.getText().toString()))
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();
                            Intent ToMainActivity = new Intent(Login.this, MainActivity.class);
                            startActivity(ToMainActivity);
                        }
                        else
                        {
                            Log.e("Error",task.getException().toString());
                            Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        //-----------end Login
        //-----------start Reg
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToRegister();
            }
        });
        //-----------end Reg


/*
        AuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){// logged in
                    startActivity(new Intent(Login.this,MainActivity.class));
                }
            }
        };
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ToSignIn();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ToRegister();
            }
        });
    }
    @Override
    protected void onStart()
    {super.onStart();
        Auth.addAuthStateListener(AuthListener);

    }

    private void ToSignIn()
    {
        String email1=email_id.getText().toString();
        String pass1=pass_id.getText().toString();

       /* if (TextUtils.isEmpty(email1)||TextUtils.isEmpty(pass1)) {

            Toast.makeText(Login.this, "Fields are Empty", Toast.LENGTH_LONG).show();

        }else {
            Auth.signInWithEmailAndPassword(email1, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }*/





    }
    private  void ToRegister()
    {
        Intent ToRegisterPage = new Intent(Login.this, Register.class);
        startActivity(ToRegisterPage);
    }
}
