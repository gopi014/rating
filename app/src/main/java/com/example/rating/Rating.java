package com.example.rating;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.rating.helper.DatabaseHelper;
import com.example.rating.model.Productratings;

import java.util.Calendar;

/**
 * Created by Gopinath on 8/26/2015.
 */
public class Rating extends Activity implements View.OnClickListener {
    RatingBar ratingbar1;
    Button submit;
    EditText comments;
    DatabaseHelper db;
    SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);
        db = new DatabaseHelper(getApplicationContext());
        ratingbar1=(RatingBar)findViewById(R.id.ratingBar);
        submit =(Button)findViewById(R.id.submit);
        comments=(EditText)findViewById(R.id.editText);
        submit.setOnClickListener(this);
        Button logout=(Button)findViewById(R.id.logout1);
        logout.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String comment=bundle.getString("comments");
        Float rating=Float.parseFloat(bundle.getString("rating"));
        comments.setText(comment);
        ratingbar1.setRating(rating);


    }
    public void onClick(View v)

    {
        switch(v.getId())

        {
            case R.id.submit:
                String rating=String.valueOf(ratingbar1.getRating());
                String comment=comments.getText().toString().trim();
                Intent i =new Intent(this,Products.class);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
                if(rating=="0.0"){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Rating")
                            .setMessage("Please give a rating")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(comment.matches("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Rating")
                            .setMessage("Please give a Comment")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    Bundle bundle = getIntent().getExtras();
                    Bundle bundle1 = new Bundle();
                    String id=bundle.getString("userid");
                    String prodcutid=bundle.getString("prodcutid");
                    if(db.getprodcutidcount(Integer.parseInt(id),Integer.parseInt(prodcutid))>0){
                        Productratings prating=new Productratings(Integer.parseInt(id),Integer.parseInt(prodcutid),Double.parseDouble(rating),comment,currentMonth,currentYear);
                        db.updateprodcuts(prating);
                        Toast.makeText(this,"rating Updated successfully",Toast.LENGTH_LONG).show();
                        bundle1.putString("userid",id);
                        i.putExtras(bundle1);
                        startActivity(i);
                    }
                    else{
                        Productratings prating=new Productratings(Integer.parseInt(id),Integer.parseInt(prodcutid),Double.parseDouble(rating),comment,currentMonth,currentYear);
                        db.createProductratings(prating);
                        Toast.makeText(this,"rating Submitted successfully",Toast.LENGTH_LONG).show();
                        bundle1.putString("userid", id);
                        i.putExtras(bundle1);
                        startActivity(i);
                    }


                }
                break;
            case R.id.logout1:
                shared=getSharedPreferences("loggedstatus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("loggedin", "true");
                editor.commit();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }


//        Log.d("id", id);
//        Log.d("pid", productid);
    }
}
