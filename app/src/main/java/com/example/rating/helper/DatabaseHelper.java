package com.example.rating.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rating.model.Productratings;
import com.example.rating.model.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gopinath on 8/26/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Ratings";
    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRODUCTRATINGS = "productratings";
    // Common column names
    private static final String KEY_ID = "id";
    // users Table - column names
    private static final String KEY_MAILID = "mailid";
    // productratings Table - column names
    private static final String KEY_PRODUCTID = "productid";
    private static final String KEY_RATING = "rating";
    private static final String KEY_COMMENTS = "comments";
    private static final String KEY_USERID = "userid";
    private static final String KEY_MONTH ="month";
    private static final String KEY_YEAR ="year";

    // Table Create Statements
    // users table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MAILID
            + " TEXT" + ")";
    // Tag table create statement
    private static final String CREATE_TABLE_PRODUCTRATINGS = "CREATE TABLE " + TABLE_PRODUCTRATINGS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRODUCTID + " INTEGER,"
            + KEY_RATING + " TEXT," + KEY_COMMENTS + " TEXT," + KEY_USERID + " INTEGER,"+ KEY_MONTH + " INTEGER,"+ KEY_YEAR + " INTEGER" + ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PRODUCTRATINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTRATINGS);
        // create new tables
        onCreate(db);
    }
    public long createUsers(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MAILID, users.getmail());
        long user_id = db.insert(TABLE_USERS, null, values);


        return user_id;
    }
    public int updateUsers(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_MAILID, users.getmail());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(users.getId()) });
    }
    public long createProductratings(Productratings pratings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PRODUCTID, pratings.getProductid());
        values.put(KEY_RATING, pratings.getRating());
        values.put(KEY_COMMENTS, pratings.getComments());
        values.put(KEY_USERID, pratings.getId());
        values.put(KEY_MONTH, pratings.getMonth());
        values.put(KEY_YEAR, pratings.getYear());
        long Prodcutratings_id = db.insert(TABLE_PRODUCTRATINGS, null, values);


        return Prodcutratings_id;
    }
    public int getUserCount(String email) {
        String countQuery = "SELECT  * FROM " + TABLE_USERS +" WHERE "+KEY_MAILID +"='"+email+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public List<Users> getallusers(){
        List<Users> userList = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setMail(cursor.getString(1));

                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return userList;
    }
    public int getuserid(String email){
        int userid=0;
        String selectQuery = "SELECT  * FROM " + TABLE_USERS +" WHERE "+KEY_MAILID +"='"+email+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

               userid = Integer.parseInt(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return userid;

    }
    public int getprodcutidcount(int id,int pid) {
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTRATINGS +" WHERE "+KEY_USERID +"='"+id+"' AND "+KEY_PRODUCTID +"='"+pid+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updateprodcuts(Productratings pratings) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_RATING, pratings.getRating());
        values.put(KEY_COMMENTS, pratings.getComments());

        // updating row
        return db.update(TABLE_PRODUCTRATINGS, values, (KEY_PRODUCTID + " = ? AND "+KEY_USERID +"= ?"),
                new String[] { String.valueOf(pratings.getProductid()),String.valueOf(pratings.getId()) });
    }
    public List<Productratings> getpratingcom(int id,int pid) {
        List<Productratings> ratingList = new ArrayList<Productratings>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTRATINGS + " WHERE " + KEY_USERID + "='" + id + "' AND " + KEY_PRODUCTID + "='" + pid + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Productratings prating = new Productratings();
                prating.setRating(Double.parseDouble(cursor.getString(2)));
                prating.setComments(cursor.getString(3));

                // Adding contact to list
                ratingList.add(prating);
            } while (cursor.moveToNext());
        }
        return ratingList;
    }
    public List<Productratings> getmonthrating(){
        List<Productratings> monthList = new ArrayList<Productratings>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTRATINGS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Productratings prating = new Productratings();
                prating.setRating(Double.parseDouble(cursor.getString(2)));
                prating.setProductid(Integer.parseInt(cursor.getString(1)));
                prating.setMonth(Integer.parseInt(cursor.getString(5)));
                prating.setYear(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                monthList.add(prating);
            } while (cursor.moveToNext());
        }
        return monthList;

    }
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
