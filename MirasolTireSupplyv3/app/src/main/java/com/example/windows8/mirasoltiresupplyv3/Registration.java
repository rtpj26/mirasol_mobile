package com.example.windows8.mirasoltiresupplyv3;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {
    private EditText evName, evEmail, evPhone, evPass, evPassVer;
    private Button reg, back;
    private TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        evName = (EditText) findViewById(R.id.evName);
        evEmail = (EditText) findViewById(R.id.evEmail);
        evPhone = (EditText) findViewById(R.id.evPhone);
        evPass = (EditText) findViewById(R.id.evPass);
        evPassVer = (EditText) findViewById(R.id.evPassVer);

        reg = (Button) findViewById(R.id.btnRegister);
        signIn = (TextView) findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(Registration.this, SignIn.class);
                Registration.this.startActivity(signInIntent);
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomm = new Intent(Registration.this, MainMenu.class);
                Registration.this.startActivity(backtomm);
                finish();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name="", email="", phone="", pass="", passV="";
                name = evName.getText().toString();
                email = evEmail.getText().toString();
                phone = evPhone.getText().toString();
                pass = evPass.getText().toString();
                passV = evPassVer.getText().toString();

                if(!pass.equals(passV)){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please make sure that password verification is the same as password", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    NetRequestAsync request = new NetRequestAsync(name, email, phone, pass, passV);
                    request.execute();

                    while(globalValues.wait){}
                    if(globalValues.lastStatus) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT);
                        toast.show();

                        Intent mmIntent = new Intent(Registration.this, MainMenu.class);
                        Registration.this.startActivity(mmIntent);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Cannot add user", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

    }



}
