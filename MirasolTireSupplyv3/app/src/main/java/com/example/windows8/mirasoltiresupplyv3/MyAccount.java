package com.example.windows8.mirasoltiresupplyv3;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyAccount extends Activity {
    private TextView id, name, contact, address, gender, email;
    private Button logout, back;
    private LinearLayout transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        id = (TextView) findViewById(R.id.tvId);
        name = (TextView) findViewById(R.id.tvName);
        contact = (TextView) findViewById(R.id.tvContact);
        address = (TextView) findViewById(R.id.tvAddress);
        gender = (TextView) findViewById(R.id.tvGender);
        email = (TextView) findViewById(R.id.tvEmail);
        logout = (Button) findViewById(R.id.btnLogout);
        transaction = (LinearLayout) findViewById(R.id.transaction_link);
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trans = new Intent(MyAccount.this, Transaction_activity.class);
                MyAccount.this.startActivity(trans);
                finish();
            }
        });
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomm = new Intent(MyAccount.this, MainMenu.class);
                MyAccount.this.startActivity(backtomm);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalValues.clear();
                globalValues.clearTransactions();
                Toast toast = Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_SHORT);
                toast.show();
                Intent home = new Intent(MyAccount.this, MainMenu.class);
                MyAccount.this.startActivity(home);
            }
        });
        id.setText(globalValues.user_Id);
        name.setText(globalValues.user_LName + ", " + globalValues.user_FName);
        contact.setText(globalValues.user_Contact);
        address.setText(globalValues.user_Address);
        email.setText(globalValues.user_Email);

        if(globalValues.user_Gender.equals("0")){
            gender.setText("Male");
        }else{
            gender.setText("Female");
        }
    }

}
