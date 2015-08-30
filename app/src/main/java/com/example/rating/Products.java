package com.example.rating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.rating.helper.DatabaseHelper;
import com.example.rating.model.Productratings;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Gopinath on 8/24/2015.
 */
public class Products extends Activity implements
                View.OnClickListener {


    SharedPreferences shared;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);
        db = new DatabaseHelper(getApplicationContext());

        Button logout=(Button)findViewById(R.id.logout);
        Button results=(Button)findViewById(R.id.button2);
        ImageButton imageButton=(ImageButton)findViewById(R.id.imageButton);
        ImageButton imageButton2=(ImageButton)findViewById(R.id.imageButton2);
        ImageButton imageButton3=(ImageButton)findViewById(R.id.imageButton3);
        ImageButton imageButton4=(ImageButton)findViewById(R.id.imageButton4);
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        logout.setOnClickListener(this);
        results.setOnClickListener(this);

    }
    public void onClick(View v)

    {
        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("userid");
        Intent intent = new Intent(this, Rating.class);
        Bundle bundle1 = new Bundle();
        int pid;
        String rating="0.0";
        String comments="";
        List<Productratings> pratings;
        switch(v.getId())

        {

            case R.id.imageButton:
                pid=1;
                if(db.getprodcutidcount(Integer.parseInt(id),pid)>0){
                    pratings= db.getpratingcom(Integer.parseInt(id),pid);
                    for (Productratings pr :pratings ) {
                        rating=String.valueOf(pr.getRating());
                        comments=pr.getComments();
                    }
                }

                bundle1.putString("userid",id);
                bundle1.putString("prodcutid", "1");
                bundle1.putString("rating", rating);
                bundle1.putString("comments", comments);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.imageButton2:
                pid=2;
                if(db.getprodcutidcount(Integer.parseInt(id),pid)>0){
                    pratings= db.getpratingcom(Integer.parseInt(id),pid);
                    for (Productratings pr :pratings ) {
                        rating=String.valueOf(pr.getRating());
                        comments=pr.getComments();
                    }
                }

                bundle1.putString("userid",id);
                bundle1.putString("prodcutid", "2");
                bundle1.putString("rating", rating);
                bundle1.putString("comments", comments);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.imageButton3:
                pid=3;
                if(db.getprodcutidcount(Integer.parseInt(id),pid)>0){
                    pratings= db.getpratingcom(Integer.parseInt(id),pid);
                    for (Productratings pr :pratings ) {
                        rating=String.valueOf(pr.getRating());
                        comments=pr.getComments();
                    }
                }
                bundle1.putString("userid",id);
                bundle1.putString("prodcutid", "3");
                bundle1.putString("rating", rating);
                bundle1.putString("comments", comments);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.imageButton4:
                pid=4;
                if(db.getprodcutidcount(Integer.parseInt(id),pid)>0){
                    pratings= db.getpratingcom(Integer.parseInt(id),pid);
                    for (Productratings pr :pratings ) {
                        rating=String.valueOf(pr.getRating());
                        comments=pr.getComments();
                    }
                }
                bundle1.putString("userid",id);
                bundle1.putString("prodcutid", "4");
                bundle1.putString("rating", rating);
                bundle1.putString("comments", comments);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.logout:
                onSignedOut();
                break;
            case R.id.button2:
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
                pratings=db.getmonthrating();
                 Double p1rating=0.0;
                Double p2rating=0.0;
                Double p3rating=0.0;
                Double p4rating=0.0;
                Double p1yrating=0.0;
                Double p2yrating=0.0;
                Double p3yrating=0.0;
                Double p4yrating=0.0;
                int p1counter=0;
                int p2counter=0;
                int p3counter=0;
                int p4counter=0;
                int p1ycounter=0;
                int p2ycounter=0;
                int p3ycounter=0;
                int p4ycounter=0;
                Double p1monthavng=0.0;
                Double p2monthavng=0.0;
                Double p3monthavng=0.0;
                Double p4monthavng=0.0;
                Double p1yearavng=0.0;
                Double p2yearavng=0.0;
                Double p3yearavng=0.0;
                Double p4yearavng=0.0;
                    for (Productratings pr :pratings ) {
                      switch (pr.getProductid()) {
                          case 1:
                              if((pr.getMonth()==currentMonth) &&(pr.getYear()==currentYear))
                              {
                                  p1rating +=pr.getRating();
                                  p1yrating +=pr.getRating();
                                  p1counter ++;
                                  p1ycounter ++;
                              }else if (pr.getYear()==currentYear){
                                  p1yrating +=pr.getRating();
                                  p1ycounter ++;
                              }

                              break;
                          case 2:
                              if((pr.getMonth()==currentMonth) &&(pr.getYear()==currentYear))
                              {
                                  p2rating +=pr.getRating();
                                  p2yrating +=pr.getRating();
                                  p2counter ++;
                                  p2ycounter ++;
                              }else if (pr.getYear()==currentYear){
                                  p2yrating +=pr.getRating();
                                  p2ycounter ++;
                              }
                              break;
                          case 3:
                              if((pr.getMonth()==currentMonth) &&(pr.getYear()==currentYear))
                              {
                                  p3rating +=pr.getRating();
                                  p3yrating +=pr.getRating();
                                  p3counter ++;
                                  p3ycounter ++;
                              }else if (pr.getYear()==currentYear){
                                  p3yrating +=pr.getRating();
                                  p3ycounter ++;
                              }
                              break;
                          case 4:
                              if((pr.getMonth()==currentMonth) &&(pr.getYear()==currentYear))
                              {
                                  p4rating +=pr.getRating();
                                  p4yrating +=pr.getRating();
                                  p4counter ++;
                                  p4ycounter ++;
                              }else if (pr.getYear()==currentYear){
                                  p4yrating +=pr.getRating();
                                  p4ycounter ++;
                              }
                              break;
                     }

                }
               if(p1rating !=0){
                   p1monthavng=(p1rating/p1counter)*2;
               }
                if(p2rating !=0){
                    p2monthavng=(p2rating/p2counter)*2;
                }
                if(p3rating !=0){
                    p3monthavng=(p3rating/p3counter)*2;
                }
                if(p4rating !=0){
                    p4monthavng=(p4rating/p4counter)*2;
                }
                if(p1yrating !=0){
                    p1yearavng=(p1yrating/p1ycounter)*2;
                }
                if(p2yrating !=0){
                    p2yearavng=(p2yrating/p2ycounter)*2;
                }
                if(p3yrating !=0){
                    p3yearavng=(p3yrating/p3ycounter)*2;
                }
                if(p4yrating !=0){
                    p4yearavng=(p4yrating/p4ycounter)*2;
                }

                Bundle bundle2=new Bundle();
                bundle2.putString("p1monthavg",String.valueOf(p1monthavng));
                bundle2.putString("p2monthavg",String.valueOf(p2monthavng));
                bundle2.putString("p3monthavg",String.valueOf(p3monthavng));
                bundle2.putString("p4monthavg", String.valueOf(p4monthavng));
                bundle2.putString("p1yearavg",String.valueOf(p1yearavng));
                bundle2.putString("p2yearavg",String.valueOf(p2yearavng));
                bundle2.putString("p3yearavg",String.valueOf(p3yearavng));
                bundle2.putString("p4yearavg", String.valueOf(p4yearavng));
                bundle2.putString("userid",id);
             Intent i =new Intent(this,Results.class);
             i.putExtras(bundle2);
             startActivity(i);

        }
    }
    private void onSignedOut(){
        shared=getSharedPreferences("loggedstatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("loggedin", "true");
        editor.commit();
       Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onStop() {
        super.onStop();

    }
}
