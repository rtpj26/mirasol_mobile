package com.example.windows8.mirasoltiresupplyv3;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends Activity {
    private EditText et_email, et_pass;
    private Button btn_signIn, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if(Integer.parseInt(globalValues.user_Id) > 0){
            Intent MyAccountIntent = new Intent(SignIn.this, MyAccount.class);
            SignIn.this.startActivity(MyAccountIntent);
        }
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        btn_signIn = (Button) findViewById(R.id.btn_signInNow);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomm = new Intent(SignIn.this, MainMenu.class);
                SignIn.this.startActivity(backtomm);
                finish();
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass;
                email = et_email.getText().toString();
                pass = et_pass.getText().toString();

                NetRequestAsync request = new NetRequestAsync(email,pass, 1);
                request.execute();

                long tStart = System.currentTimeMillis();
                long tEnd, tDelta;
                double elapsedSeconds;
                while(globalValues.user_Id == "0"){
                    tEnd = System.currentTimeMillis();
                    tDelta = tEnd - tStart;
                    elapsedSeconds = tDelta / 1000.0;
                    if(elapsedSeconds >= 6){
                        break;
                    }
                }
                if(globalValues.user_Id.equals("-1")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Unable to sign in", Toast.LENGTH_SHORT);
                    toast.show();
                    globalValues.user_Id = "0";
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Signed In", Toast.LENGTH_SHORT);
                    toast.show();
                    request = new NetRequestAsync(globalValues.user_Id, 3);
                    request.execute();

                    Intent HomeIntent = new Intent(SignIn.this, MainMenu.class);
                    SignIn.this.startActivity(HomeIntent);
                    finish();
                }
            }
        });
    }


}
