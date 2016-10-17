package com.example.windows8.mirasoltiresupplyv3;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class SignIn_Reg extends Activity {
    private Button btnSignIn, btnRegister, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__reg);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(SignIn_Reg.this, Registration.class);
                SignIn_Reg.this.startActivity(registerIntent);
            }
        });

        int tempId = Integer.parseInt(globalValues.user_Id);
        if(tempId > 0) {
            btnSignIn.setText(globalValues.user_FName + " Account");
        }

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomm = new Intent(SignIn_Reg.this, MainMenu.class);
                SignIn_Reg.this.startActivity(backtomm);
                finish();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempId = Integer.parseInt(globalValues.user_Id);
                if(tempId > 0) {

                    Intent myAccountIntent = new Intent(SignIn_Reg.this, MyAccount.class);
                    SignIn_Reg.this.startActivity(myAccountIntent);
                    finish();
                }else {
                    Intent signInIntent = new Intent(SignIn_Reg.this, SignIn.class);
                    SignIn_Reg.this.startActivity(signInIntent);
                    finish();
                }
            }
        });
    }


}


