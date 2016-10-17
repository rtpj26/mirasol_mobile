package com.example.windows8.mirasoltiresupplyv3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;

public class Transaction_activity extends Activity {
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
    float tblWeight = (float) 0.3;
    LinearLayout account;
    LinearLayout tl;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_activity);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomm = new Intent(Transaction_activity.this, MainMenu.class);
                Transaction_activity.this.startActivity(backtomm);
                finish();
            }
        });
        tl = (LinearLayout) findViewById(R.id.table_value);
        ArrayList<Transaction> t = globalValues.transactions;
        for(int i = 0; i < t.size(); i++){
            LinearLayout tr = new LinearLayout(this);

            LinearLayout.LayoutParams trparam = new LinearLayout.LayoutParams(matchParent,wrapContent);
            tr.setLayoutParams(trparam);

            LinearLayout.LayoutParams tvparam = new LinearLayout.LayoutParams(matchParent,wrapContent);
            tvparam.gravity= Gravity.CENTER_HORIZONTAL;
            tvparam.weight = (float) 0.3;
            tvparam.bottomMargin = 10;

            TextView val1 = new TextView(this);
            val1.setText(t.get(i).getTimestamp());
            val1.setLayoutParams(tvparam);
            val1.setTextColor(Color.rgb(0,0,0));
            val1.setGravity(Gravity.CENTER);
            TextView val2 = new TextView(this);
            if(t.get(i).getMode().equals("1")){
                val2.setText("CARD");
            }else if(t.get(i).getMode().equals("2")){
                val2.setText("COD");
            }else{
                val2.setText("CHEQUE");
            }
            val2.setLayoutParams(tvparam);
            val2.setTextColor(Color.rgb(0,0,0));
            val2.setGravity(Gravity.CENTER);
            TextView val3 = new TextView(this);
            if(t.get(i).getStatus().equals("1")){
                val3.setText("PENDING");
            }else{
                val3.setText("DONE");
            }

            val3.setLayoutParams(tvparam);
            val3.setTextColor(Color.rgb(0,0,0));
            val3.setGravity(Gravity.CENTER);
            tr.addView(val1);
            tr.addView(val2);
            tr.addView(val3);
            tl.addView(tr);

            Log.d("Values", t.get(i).getMode());
            Log.d("Values", t.get(i).getStatus());
            Log.d("Values", t.get(i).getTimestamp());
        }

        account = (LinearLayout) findViewById(R.id.account_link);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountAct = new Intent(Transaction_activity.this, MyAccount.class);
                Transaction_activity.this.startActivity(accountAct);
                finish();
            }
        });
    }

}
