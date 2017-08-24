package com.exampledemo.Group02_Group03.coe125;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    Button reg_btn;
    EditText username_id;
    EditText address_id;
    EditText pass_id;
    EditText email_id;
    private FirebaseAuth Auth2;
    DatabaseReference databaseCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Auth2=FirebaseAuth.getInstance();

        //  name_id= (EditText) findViewById(R.id.name_id);
        email_id=(EditText) findViewById(R.id.email_id);
        pass_id= (EditText) findViewById(R.id.pass_id);
        username_id= (EditText) findViewById(R.id.username_id);
        address_id=(EditText)findViewById(R.id.address_id);
        reg_btn=(Button)findViewById(R.id.reg_btn);


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//check if pass==pass1

                (Auth2.createUserWithEmailAndPassword(email_id.getText().toString(), pass_id.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //  progressDialog.dismiss(); ??????
                                if (task.isSuccessful()) {
                                    sendUserInfo();
                                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_LONG).show();
                                    Intent ToLogin = new Intent(Register.this, Login.class);
                                    startActivity(ToLogin);
                                } else {
                                  // Log.e("Error", task.getException().toString());
                                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    Intent goback = new Intent(Register.this, Register.class);
                                    //email.setText(" ");pass_id.setText(" ");pass_id.setText(" ");
                                }
                            }
                        });
            }           /*
                if(pass_id1.getText().toString().equals(pass_id.getText().toString())){
                  Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_LONG).show();
                   // Toast.makeText("Registration Success", Toast.LENGTH_SHORT).show();
                    Intent ToMainActivity = new Intent(RegisterUser.this, MainActivity.class);
                    startActivity(ToMainActivity);}
                else
                { Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_LONG).show();
                   // Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_LONG);
                    Intent goback = new Intent(RegisterUser.this, RegisterUser.class);
                    user_id.setText(" ");pass_id.setText(" ");pass_id1.setText(" ");
                }*/


        });
        databaseCommand = FirebaseDatabase.getInstance().getReference("User Info");
    }
    private void sendUserInfo()
    {

        String command="OFF";
        String username = username_id.getText().toString().trim();
        String email = email_id.getText().toString().trim();//.getText().toString().trim();
        String address = address_id.getText().toString().trim();
        String pass = pass_id.getText().toString().trim();

        if(!TextUtils.isEmpty(username)) {

            String id = databaseCommand.push().getKey();
            Command SendInfo = new Command(command,username,email,address,pass);
            databaseCommand.child(username_id.getText().toString().trim()).setValue(SendInfo);
           // Toast.makeText(this, "Command Sent",Toast.LENGTH_LONG).show();
        }
        else{Toast.makeText(this,"Running Power", Toast.LENGTH_LONG).show();
        }


    }


}
