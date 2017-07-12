package com.example.diyanshu.fareyeproject;

/**
 * Created by diyanshu on 7/7/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Databases extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    public String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";

    public Databases(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_URL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new details
    void addContact(Getting getting) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, getting.getId()); // Contact id
        values.put(KEY_NAME, getting.getTitle()); // Contact Title.
        values.put(KEY_URL, getting.getUrl());

        // Inserting Row
        long value = db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public void deleteall() {
        String selectQuery = "DELETE  FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
//            db.
        db.close();

    }

    // Getting single contact
    Getting getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_URL}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Getting getting = new Getting(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return getting;
    }

    List<Getting> contactList;

    // Getting All Contacts
    public List<Getting> getAllContacts() {
        contactList = new ArrayList<Getting>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Getting gt = new Getting(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

                // Adding contact to list.
                contactList.add(gt);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    String countQuery;
    SQLiteDatabase db;


    // Getting contacts Count
    public int getContactsCount() {
        try {
            countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int count = cursor.getCount();
            db.close();
            cursor.close();

            return count;
        } catch (Exception e) {

            Log.e("ERROR", e.getMessage(), e);//Logs are used to show what to know whether it got success or it get failed.
            return 0;
        }
    }
}