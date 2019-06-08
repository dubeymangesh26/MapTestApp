package com.dotcom.maptestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText name,email,pass,cnfpass;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db =new DataBaseHelper(this);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        cnfpass=findViewById(R.id.cnfpass);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = name.getText().toString();
                String s2 = email.getText().toString();
                String s3 = pass.getText().toString();
                String s4 = cnfpass.getText().toString();
                if (s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")){
                    Toast.makeText(RegistrationActivity.this, "Please Fill All Field ", Toast.LENGTH_SHORT).show();
                }else
                    if (s3.equals(s4)){
                        Boolean checkMail=db.checkMail(s2);
                        if (checkMail==true){
                            Boolean insert =db.insert(s1,s2,s3);
                            if (insert==true){
                                Toast.makeText(getApplicationContext(),"Register Succesfull",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent (RegistrationActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(RegistrationActivity.this, "Email Allready Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Passward Not Match", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
}
