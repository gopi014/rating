package com.example.rating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Gopinath on 8/28/2015.
 */
public class Results extends Activity implements View.OnClickListener{
    int id=0;
    SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        TextView p1m=(TextView)findViewById(R.id.rcol2);
        TextView p2m=(TextView)findViewById(R.id.tcol2);
        TextView p3m=(TextView)findViewById(R.id.fcol2);
        TextView p4m=(TextView)findViewById(R.id.focol2);
        TextView p1y=(TextView)findViewById(R.id.rcol3);
        TextView p2y=(TextView)findViewById(R.id.tcol3);
        TextView p3y=(TextView)findViewById(R.id.fcol3);
        TextView p4y=(TextView)findViewById(R.id.focol3);
        Button products=(Button)findViewById(R.id.products);
        Bundle bundle = getIntent().getExtras();
        p1m.setText(bundle.getString("p1monthavg"));
        p2m.setText(bundle.getString("p2monthavg"));
        p3m.setText(bundle.getString("p3monthavg"));
        p4m.setText(bundle.getString("p4monthavg"));
        p1y.setText(bundle.getString("p1yearavg"));
        p2y.setText(bundle.getString("p2yearavg"));
        p3y.setText(bundle.getString("p3yearavg"));
        p4y.setText(bundle.getString("p4yearavg"));
        id=Integer.parseInt(bundle.getString("userid"));
        products.setOnClickListener(this);
        Button logout=(Button)findViewById(R.id.logout2);
       logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.products:
                Intent i =new Intent(this,Products.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("userid", String.valueOf(id));
                i.putExtras(bundle1);
                startActivity(i);
                break;
            case R.id.logout2:
                shared=getSharedPreferences("loggedstatus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("loggedin", "true");
                editor.commit();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }
}
