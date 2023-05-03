package com.example.project2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "health_reminder.db";
    private static final int DATABASE_VERSION = 1;
    private String TABLE_NAME;
    String userid;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userQuery = "CREATE TABLE users(userid INTEGER  PRIMARY KEY AUTOINCREMENT,  username  TEXT, password TEXT, email Text, confPassword text)";
        db.execSQL(userQuery);

//        String pharmacyQuery = "CREATE TABLE pharmacy(pharmacyid INTEGER  PRIMARY KEY AUTOINCREMENT,  pharmacyname  TEXT, p_num integer, address Text, longi float, lati float, userid integer)";
//        db.execSQL(pharmacyQuery);

        db.execSQL("CREATE TABLE pharmacy(pharmacyid INTEGER  PRIMARY KEY AUTOINCREMENT,  pharmacyname  TEXT)");

        String reminderQuery = "CREATE TABLE reminders(remindid INTEGER  PRIMARY KEY AUTOINCREMENT,  mediname  TEXT, quantity integer, repeat boolean, userid integer)";
        db.execSQL(reminderQuery);
    }

    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS pharmacy");
        onCreate(db);
    }
    //**************************************************
    // reminder
    public boolean addPhar(String pharName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pharmacyName", pharName);
        long insertionResult = database.insert("pharmacy", null, contentValues);
        return (insertionResult != -1);
    }
    //*****************************************************************
// reminder
    public boolean addReminder(String reminderText, long timestamp,boolean repeat) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mediName", reminderText);
        contentValues.put("quantity", timestamp);
        contentValues.put("repeat", repeat);
        contentValues.put("userid", this.userid);
        long insertionResult = database.insert("reminders", null, contentValues);
        return (insertionResult != -1);
    }

// add pharmacy
    public boolean addPharmacy(String name, String address, String phone, int p_num, float longi, float lati) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pharmacyName", name);
        contentValues.put("p_num", p_num);
        contentValues.put("address", address);
        contentValues.put("longi", longi);
        contentValues.put("lati", lati);
        contentValues.put("userid",this.userid);
        long insertionResult = database.insert("pharmacy", null, contentValues);
        return (insertionResult != -1);
    }

// get pharmacy methord
    public Cursor getPharmacies() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM pharmacy where userid =?";
        Cursor cursor = database.rawQuery(query, new String[]{this.userid});
        return cursor;
    }

// get reminder method
    public Cursor getReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM reminders where userid =?";
        Cursor cursor = database.rawQuery(query, new String[]{this.userid});
        return cursor;
    }

// login
    public void login(String username, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM users where username =? AND password =?";
        Cursor cursor = database.rawQuery(query,new String[]{username,password});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        this.userid=userId+"";
    }

    // register
    public void register(String username, String password, String email){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "INSERT INTO users(username, password, email, confPassword) VALUES(?,?,?,?)";
        database.execSQL(query,new String[]{username,password,email,password});
        database.close();
    }

    // get user prof details

    public Cursor getUserProfileData() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM users_table WHERE id = " + this.userid;
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}