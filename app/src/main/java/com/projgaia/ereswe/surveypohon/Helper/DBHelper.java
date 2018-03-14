package com.projgaia.ereswe.surveypohon.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projgaia.ereswe.surveypohon.Model.FotoPohon;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ERESWE on 22/01/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // Contacts table name
    public static final String TABLE_IMAGE = "tbl_images";
    String encoded_string;
    // Contacts Table Columns names
    public static final String KEY_ID = "id_img";
    public static final String KEY_IDPOHON = "id_pohon";
    public static final String KEY_IDSURVEY= "id_survey";
    public static final String KEY_IMAGE_PATH = "path_img";
    public static String KEY_ID_ANGLE = "id_angle_img";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATUS = "status";

    private SQLiteDatabase db;

    public DBHelper(Context applicationcontext) {
        super(applicationcontext, "image.db", null, 1);


    }



    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String foto;

        foto = "CREATE TABLE " + TABLE_IMAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_IDSURVEY + " TEXT null," + KEY_IDPOHON + " TEXT null," + KEY_IMAGE_PATH + " TEXT null," + KEY_ID_ANGLE + " TEXT null," + KEY_DATE + " TEXT null," + KEY_STATUS + " TEXT no)";
        Log.d("Data", "onCreate: " + foto);

        database.execSQL(foto);

    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {

        String foto;
        foto = ("DROP TABLE IF EXISTS " + TABLE_IMAGE);

        database.execSQL(foto);
        //    database.execSQL(DELETE_TABLE);
        onCreate(database);
    }


    // Adding new image
    public void addImage(FotoPohon image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, image.getId_image()); // Id Image
        values.put(KEY_IDSURVEY, image.getId_survey()); // Image path
        values.put(KEY_IMAGE_PATH, image.getPath()); // Image path
        values.put(KEY_IDPOHON, image.getId_pohon()); // Id Pohon
        values.put(KEY_ID_ANGLE, image.getId_angle()); // Id angle
        values.put(KEY_DATE, image.getDatetimeLong()); // Tanggal
        values.put(KEY_STATUS, "no"); // Tanggal

        // Inserting Row
        db.insert(TABLE_IMAGE, null, values);
        db.close(); // Closing database connection
    }


    // Updating single contact
    public int updateImage(int idImg, String pathImg, String dateImg, String statusImg) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues recordImage = new ContentValues();
        recordImage.put(KEY_IMAGE_PATH, pathImg);
        recordImage.put(KEY_DATE, dateImg);
        recordImage.put(KEY_STATUS, statusImg);
        return database.update(TABLE_IMAGE, recordImage, KEY_ID+"= " + idImg, null);
    }


    public List<FotoPohon> getImag(String idP) {
        List<FotoPohon> imageList = new ArrayList<FotoPohon>();

            Cursor cursor = getWritableDatabase().query(TABLE_IMAGE, new String[]{KEY_ID, KEY_IDSURVEY,
                            KEY_IDPOHON, KEY_IMAGE_PATH, KEY_ID_ANGLE, KEY_DATE, KEY_STATUS}, KEY_IDPOHON + "=?",
                    new String[]{idP}, null, null, null, null);
        cursor.moveToFirst();
            if (cursor.getCount()>0) {

                do {
                    FotoPohon image = new FotoPohon();
                    image.setId_image(cursor.getString(0));
                    image.setId_survey(cursor.getString(1));
                    image.setId_pohon(cursor.getString(2));
                    image.setPath(cursor.getString(3));
                    image.setId_angle(cursor.getString(4));
                    image.setDatetime(cursor.getString(5));
                    image.setStatus(cursor.getString(6));

                    imageList.add(image);
                } while (cursor.moveToNext());
            }else {

            }
                cursor.close();


        // return contact
        return imageList;
    }


    // Getting All Contacts
    public List<FotoPohon> getAllImage() {
        List<FotoPohon> imageList = new ArrayList<FotoPohon>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FotoPohon image = new FotoPohon();
                image.setId_image(cursor.getString(0));
                image.setId_survey(cursor.getString(1));
                image.setId_pohon(cursor.getString(2));
                image.setPath(cursor.getString(3));
                image.setId_angle(cursor.getString(4));
                image.setDatetime(cursor.getString(5));
                image.setStatus(cursor.getString(6));

                // Adding image to list
                imageList.add(image);
            } while (cursor.moveToNext());
        }
        // return image list
        return imageList;
    }

    // Deleting single contact
    public void deleteContact(String path) {
        FotoPohon image = new FotoPohon();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE, KEY_IMAGE_PATH + " = ?",
                new String[] { String.valueOf(path) });


        db.close();
    }

    // Deleting All
    public void deleteAll() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_IMAGE);
        database.close();
    }



    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public void insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_survey", queryValues.get("id_survey"));
        values.put("nama_pohon", queryValues.get("nama_pohon"));
        values.put("keterangan", queryValues.get("keterangan"));
        values.put("diameter_pohon", queryValues.get("diameter_pohon"));
        values.put("latitude", queryValues.get("latitude"));
        values.put("longitude", queryValues.get("longitude"));
        values.put("tgl_edit", queryValues.get("tgl_edit"));
        values.put("surveyor", queryValues.get("surveyor"));
        values.put("status", queryValues.get("status"));
        database.insert("tbl_survey", null, values);

        values.put("id_img", queryValues.get("id_img"));
        database.insert("tbl_image", null, values);
        database.close();
    }

    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllImag() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM "+TABLE_IMAGE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_ID, cursor.getString(0));
                map.put(KEY_IDSURVEY, cursor.getString(1));
                map.put(KEY_IDPOHON, cursor.getString(2));
                map.put(KEY_IMAGE_PATH, cursor.getString(3));
                map.put(KEY_ID_ANGLE, cursor.getString(4));
                map.put(KEY_DATE, cursor.getString(5));
                map.put(KEY_STATUS, cursor.getString(6));

                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }



    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_IMAGE+" where "+KEY_STATUS+" = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(KEY_ID, cursor.getString(0));
                map.put(KEY_IDSURVEY, cursor.getString(1));
                map.put(KEY_IDPOHON, cursor.getString(2));
              map.put(KEY_IMAGE_PATH,  cursor.getString(3));
                map.put(KEY_ID_ANGLE, cursor.getString(4));
                map.put(KEY_DATE, cursor.getString(5));
                map.put(KEY_STATUS, cursor.getString(6));


                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync needed\n";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM "+TABLE_IMAGE+" where "+KEY_STATUS+" = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    /**
     * Update Sync status against each User ID
     * @param id
     * @param status
     */
    public void updateSyncStatus(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update "+TABLE_IMAGE+" set "+KEY_STATUS+" = '"+ status +"' where "+KEY_ID+" ="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }


}
