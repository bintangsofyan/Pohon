package com.projgaia.ereswe.surveypohon.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projgaia.ereswe.surveypohon.Model.Persil;
import com.projgaia.ereswe.surveypohon.Model.Petani;
import com.projgaia.ereswe.surveypohon.Model.Pohon;
import com.projgaia.ereswe.surveypohon.Model.Survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ERESWE on 22/01/2017.
 */

public class DBController extends SQLiteOpenHelper {
    SQLiteDatabase db;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "survey.db";
    //Table Name
    private static final String TABLE_USER = "petani";
    private static final String TABLE_PROVINSI = "provinsi";
    private static final String TABLE_KABKOTA = "kabkota";
    private static final String TABLE_KECAMATAN = "kecamatan";
    private static final String TABLE_DESA = "desa";
    private static final String TABLE_GAPOKTAN = "gapoktan";
    private static final String TABLE_KTH = "kth";
    private static final String TABLE_PERSIL = "persil";
    private static final String TABLE_POHON = "pohon";
    private static final String TABLE_POHONSURVEY = "pohonsurvey";
    private static final String TABLE_SURVEY = "survey";

    //Column Name
    private static final String COLUMN_USER_ID= "petani_id";
    private static final String COLUMN_USER_USERNAME = "petani_uname";
    private static final String COLUMN_USER_NAME = "petani_name";
    private static final String COLUMN_USER_ADDRESS = "petani_alamat";
    private static final String COLUMN_USER_EMAIL = "petani_email";
    private static final String COLUMN_USER_PASSWORD = "petani_password";
    private static final String COLUMN_PETANIPERSIL = "persil_id";

    private static final String COLUMN_PROVINSI_ID= "provinsi_id";
    private static final String COLUMN_PROVINSI_KODE = "provinsi_kode";
    private static final String COLUMN_PROVINSI_NAME = "provinsi_name";

    private static final String COLUMN_KABKOTA_ID = "kabkota_id";
    private static final String COLUMN_KABKOTA_KODE = "kabkota_kode";
    private static final String COLUMN_KABKOTA_NAME = "kabkota_name";
    private static final String COLUMN_KABKOTA_PROV_ID = "kabkota_prov_id";

    private static final String COLUMN_KECAMATAN_ID= "kecamatan_id";
    private static final String COLUMN_KECAMATAN_KODE = "kecamatan_kode";
    private static final String COLUMN_KECAMATAN_NAME = "kecamatan_name";
    private static final String COLUMN_KECAMATAN_KAB_ID = "kecamatan_kab_id";

    private static final String COLUMN_DESA_ID= "desa_id";
    private static final String COLUMN_DESA_INITIAL = "desa_initial";
    private static final String COLUMN_DESA_NAME = "desa_name";
    private static final String COLUMN_DESA_KEC_ID = "desa_kec_id";

    private static final String COLUMN_GAPOKTAN_ID= "gapoktan_id";
    private static final String COLUMN_GAPOKTAN_NAME = "gapoktan_name";
    private static final String COLUMN_GAPOKTAN_DESA_ID = "gapoktan_desa_id";

    private static final String COLUMN_KTH_ID= "kth_id";
    private static final String COLUMN_KTH_NAME = "kth_name";
    private static final String COLUMN_KTH_GAPOKTAN_ID = "kth_gapoktan_id";

    private static final String COLUMN_PERSIL_ID= "persil_id";
    private static final String COLUMN_PERSIL_NOMOR = "persil_nomor";
    private static final String COLUMN_PERSIL_DESA_ID = "persil_desa_id";
    private static final String COLUMN_PERSIL_KTH_ID = "persil_kth_id";

    private static final String COLUMN_POHON_ID= "pohon_id";
    private static final String COLUMN_POHON_KODE = "pohon_kode";
    private static final String COLUMN_POHON_PERSIL_ID = "pohon_persil_id";
    private static final String COLUMN_POHON_INITIAL = "pohon_initial";
    private static final String COLUMN_POHON_NAMALATIN = "pohon_namalatin";
    private static final String COLUMN_POHON_NAMALOKAL = "pohon_namalokal";
    private static final String COLUMN_POHON_STATUS = "pohon_status";
    private static final String COLUMN_POHON_LASTUPDATE = "pohon_lastupdate";


    private static final String COLUMN_POHONSURVEY_ID= "pohonsurvey_id";
    private static final String COLUMN_POHONSURVEY_IDPOHON = "pohonsurvey_pohon_id";

    private static final String COLUMN_SURVEY_ID= "survey_id";
    private static final String COLUMN_SURVEY_POHON_ID = "survey_pohon_id";
    private static final String COLUMN_SURVEY_PETANI_ID = "survey_petani_id";
    private static final String COLUMN_SURVEY_DBH = "survey_dbh";
    private static final String COLUMN_SURVEY_LAT = "survey_latitude";
    private static final String COLUMN_SURVEY_LONG = "survey_longitude";
    private static final String COLUMN_SURVEY_KETERANGAN = "survey_keterangan";
    private static final String COLUMN_SURVEY_POHONSTATUS = "survey_pohonstatus";
    private static final String COLUMN_SURVEY_POHONKATAGORI = "survey_pohonkondisi";
    private static final String COLUMN_SURVEY_IMG = "survey_img";
    private static final String COLUMN_SURVEY_TGL = "survey_tgl";
    private static final String COLUMN_SURVEY_TGLUPLOAD = "survey_tglupload";
    private static final String COLUMN_SURVEY_POHONTIPE = "survey_pohontipe";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER," + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_ADDRESS+ " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_PETANIPERSIL + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_PROVINSI_TABLE = "DROP TABLE IF EXISTS " + TABLE_PROVINSI;
    private String DROP_KABKOTA_TABLE = "DROP TABLE IF EXISTS " + TABLE_KABKOTA;
    private String DROP_KECAMATAN_TABLE = "DROP TABLE IF EXISTS " + TABLE_KECAMATAN;
    private String DROP_DESA_TABLE = "DROP TABLE IF EXISTS " + TABLE_DESA;
    private String DROP_GAPOKTAN_TABLE = "DROP TABLE IF EXISTS " + TABLE_GAPOKTAN;
    private String DROP_KTH_TABLE = "DROP TABLE IF EXISTS " + TABLE_KTH;
    private String DROP_PERSIL_TABLE = "DROP TABLE IF EXISTS " + TABLE_PERSIL;
    private String DROP_POHON_TABLE = "DROP TABLE IF EXISTS " + TABLE_POHON;
    private String DROP_POHONSURVEY_TABLE = "DROP TABLE IF EXISTS " + TABLE_POHONSURVEY;
    private String DROP_SURVEY_TABLE = "DROP TABLE IF EXISTS " + TABLE_SURVEY;

    public DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {

   //   database.execSQL(CREATE_USER_TABLE);

        String querypetani, queryprovinsi, querykabkota, querykecamatan, querydesa, querygapoktan, querykth, querypersil, querypohon, querypohonsurvey, querysurvey;
        querypetani = "CREATE TABLE petani ( petani_id INTEGER, petani_name TEXT, petani_alamat TEXT, " +
                "petani_email TEXT, petani_uname TEXT, petani_password TEXT, persil_id TEXT )";

        queryprovinsi = "CREATE TABLE provinsi ( provinsi_id INTEGER, provinsi_kode TEXT, provinsi_name TEXT )";

        querykabkota = "CREATE TABLE kabkota ( kabkota_id INTEGER, kabkota_prov_id INTEGER, kabkota_kode TEXT, kabkota_name TEXT )";

        querykecamatan = "CREATE TABLE kecamatan ( kecamatan_id INTEGER, kecamatan_kab_id INTEGER, kecamatan_kode TEXT, kecamatan_name TEXT )";

        querydesa = "CREATE TABLE desa ( desa_id INTEGER, desa_kec_id INTEGER, desa_name TEXT, desa_initial TEXT )";

        querygapoktan = "CREATE TABLE gapoktan ( gapoktan_id INTEGER, gapoktan_desa_id INTEGER, gapoktan_name TEXT )";

        querykth = "CREATE TABLE kth ( kth_id INTEGER, kth_gapoktan_id INTEGER, kth_name TEXT )";

        querypersil = "CREATE TABLE persil ( persil_id INTEGER, persil_desa_id INTEGER, persil_kth_id INTEGER, persil_nomor TEXT )";

        querypohon = "CREATE TABLE pohon ( pohon_id INTEGER, pohon_kode TEXT, pohon_persil_id INTEGER, pohon_initial TEXT, pohon_namalatin TEXT, pohon_namalokal TEXT, pohon_status TEXT, pohon_lastupdate TEXT  )";

        querypohonsurvey = "CREATE TABLE pohonsurvey ( pohonsurvey_id INTEGER, pohonsurvey_pohon_id INTEGER)";

        querysurvey = "CREATE TABLE survey ( survey_id INTEGER PRIMARY KEY, survey_pohon_id INTEGER," +
                " survey_petani_id INTEGER, survey_dbh TEXT null, survey_latitude TEXT null, " +
                "survey_longitude TEXT null, survey_keterangan TEXT null, survey_pohonstatus " +
                "TEXT null, survey_pohonkondisi TEXT null, survey_img TEXT null, survey_tgl TEXT null, " +
                "survey_tglupload TEXT null, survey_pohontipe TEXT null)";


        database.execSQL(querypetani);
        database.execSQL(queryprovinsi);
        database.execSQL(querykabkota);
        database.execSQL(querykecamatan);
        database.execSQL(querydesa);
        database.execSQL(querygapoktan);
        database.execSQL(querykth);
        database.execSQL(querypersil);
        database.execSQL(querypohon);
        database.execSQL(querypohonsurvey);
        database.execSQL(querysurvey);


    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        //Drop User Table if exist
        database.execSQL(DROP_USER_TABLE);
        database.execSQL(DROP_PROVINSI_TABLE);
        database.execSQL(DROP_KABKOTA_TABLE);
        database.execSQL(DROP_KECAMATAN_TABLE);
        database.execSQL(DROP_DESA_TABLE);
        database.execSQL(DROP_GAPOKTAN_TABLE);
        database.execSQL(DROP_KTH_TABLE);
        database.execSQL(DROP_PERSIL_TABLE);
        database.execSQL(DROP_POHON_TABLE);
        database.execSQL(DROP_POHONSURVEY_TABLE);
        database.execSQL(DROP_SURVEY_TABLE);
        // Create tables again
        onCreate(database);
    }
    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public void insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, queryValues.get("petani_id"));
        values.put(COLUMN_USER_NAME, queryValues.get("petani_name"));
        values.put(COLUMN_USER_ADDRESS, queryValues.get("petani_alamat"));
        values.put(COLUMN_USER_EMAIL, queryValues.get("petani_email"));
        values.put(COLUMN_USER_USERNAME, queryValues.get("petani_uname"));
        values.put(COLUMN_USER_PASSWORD, queryValues.get("petani_password"));
        values.put(COLUMN_PETANIPERSIL, queryValues.get("persil_id"));
        database.insert(TABLE_USER, null, values);
        database.close();
    }


    /**
     * Inserts User into SQLite DB
     * @param provinsiValues
     */
    public void insertProvinsi(HashMap<String, String> provinsiValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROVINSI_ID, provinsiValues.get("provinsi_id"));
        values.put(COLUMN_PROVINSI_KODE, provinsiValues.get("provinsi_kode"));
        values.put(COLUMN_PROVINSI_NAME, provinsiValues.get("provinsi_name"));
        database.insert(TABLE_PROVINSI, null, values);
        database.close();
    }


    /**
     * Inserts User into SQLite DB
     * @param kabkotaValues
     */
    public void insertKabKota(HashMap<String, String> kabkotaValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KABKOTA_ID, kabkotaValues.get("kabkota_id"));
        values.put(COLUMN_KABKOTA_KODE, kabkotaValues.get("kabkota_kode"));
        values.put(COLUMN_KABKOTA_NAME, kabkotaValues.get("kabkota_name"));
        values.put(COLUMN_KABKOTA_PROV_ID, kabkotaValues.get("kabkota_prov_id"));
        database.insert(TABLE_KABKOTA, null, values);
        database.close();
    }


    /**
     * Inserts User into SQLite DB
     * @param kecamatanValues
     */
    public void insertKecamatan(HashMap<String, String> kecamatanValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KECAMATAN_ID, kecamatanValues.get("kecamatan_id"));
        values.put(COLUMN_KECAMATAN_KODE, kecamatanValues.get("kecamatan_kode"));
        values.put(COLUMN_KECAMATAN_NAME, kecamatanValues.get("kecamatan_name"));
        values.put(COLUMN_KECAMATAN_KAB_ID, kecamatanValues.get("kecamatan_kab_id"));
        database.insert(TABLE_KECAMATAN, null, values);
        database.close();
    }


    /**
     * Inserts User into SQLite DB
     * @param desaValues
     */
    public void insertDesa(HashMap<String, String> desaValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESA_ID, desaValues.get("desa_id"));
        values.put(COLUMN_DESA_NAME, desaValues.get("desa_name"));
        values.put(COLUMN_DESA_INITIAL, desaValues.get("desa_initial"));
        values.put(COLUMN_DESA_KEC_ID, desaValues.get("desa_kec_id"));
        database.insert(TABLE_DESA, null, values);
        database.close();
    }


    /**
     * Inserts User into SQLite DB
     * @param gapoktanValues
     */
    public void insertGapoktan(HashMap<String, String> gapoktanValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GAPOKTAN_ID, gapoktanValues.get("gapoktan_id"));
        values.put(COLUMN_GAPOKTAN_NAME, gapoktanValues.get("gapoktan_name"));
        values.put(COLUMN_GAPOKTAN_DESA_ID, gapoktanValues.get("gapoktan_desa_id"));
        database.insert(TABLE_GAPOKTAN, null, values);
        database.close();
    }

    /**
     * Inserts User into SQLite DB
     * @param kthValues
     */
    public void insertKTH(HashMap<String, String> kthValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KTH_ID, kthValues.get("kth_id"));
        values.put(COLUMN_KTH_NAME, kthValues.get("kth_name"));
        values.put(COLUMN_KTH_GAPOKTAN_ID, kthValues.get("kth_gapoktan_id"));
        database.insert(TABLE_KTH, null, values);
        database.close();
    }

    /**
     * Inserts User into SQLite DB
     * @param persilValues
     */
    public void insertPersil(HashMap<String, String> persilValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSIL_ID, persilValues.get("persil_id"));
        values.put(COLUMN_PERSIL_NOMOR, persilValues.get("persil_nomor"));
        values.put(COLUMN_PERSIL_KTH_ID, persilValues.get("persil_kth_id"));
        values.put(COLUMN_PERSIL_DESA_ID, persilValues.get("persil_desa_id"));
        database.insert(TABLE_PERSIL, null, values);
        database.close();
    }


    /**
     * Inserts User into SQLite DB
     * @param pohonValues
     */
    public void insertPohon(HashMap<String, String> pohonValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POHON_ID, pohonValues.get("pohon_id"));
        values.put(COLUMN_POHON_KODE, pohonValues.get("pohon_kode"));
        values.put(COLUMN_POHON_PERSIL_ID, pohonValues.get("pohon_persil_id"));
        values.put(COLUMN_POHON_INITIAL, pohonValues.get("pohon_initial"));
        values.put(COLUMN_POHON_NAMALATIN, pohonValues.get("pohon_namalatin"));
        values.put(COLUMN_POHON_NAMALOKAL, pohonValues.get("pohon_namalokal"));
        values.put(COLUMN_POHON_STATUS, pohonValues.get("pohon_status"));
        values.put(COLUMN_POHON_LASTUPDATE, pohonValues.get("pohon_lastupdate"));
        database.insert(TABLE_POHON, null, values);
        database.close();
    }

    /**
     * Inserts User into SQLite DB
     * @param pohonSurveyValues
     */
    public void insertPohonSurvey(HashMap<String, String> pohonSurveyValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POHONSURVEY_ID, pohonSurveyValues.get("survey_id"));
        values.put(COLUMN_POHONSURVEY_IDPOHON, pohonSurveyValues.get("pohon_id"));
        database.insert(TABLE_POHONSURVEY, null, values);
        database.close();
    }

    // Adding new survey
    public void insertSurvey(Survey survey) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SURVEY_POHON_ID, survey.getId_pohon());
        values.put(COLUMN_SURVEY_PETANI_ID, survey.getId_petani());
        values.put(COLUMN_SURVEY_DBH, survey.getDiameter());
        values.put(COLUMN_SURVEY_LAT, survey.getLatitude());
        values.put(COLUMN_SURVEY_LONG, survey.getLongitude());
        values.put(COLUMN_SURVEY_KETERANGAN, survey.getKeterangan());
        values.put(COLUMN_SURVEY_POHONSTATUS, survey.getStatuspohon());
        values.put(COLUMN_SURVEY_POHONKATAGORI, survey.getKatagoripohon());
        values.put(COLUMN_SURVEY_IMG, survey.getImg());
        values.put(COLUMN_SURVEY_TGL, survey.getTgl());
        values.put(COLUMN_SURVEY_TGLUPLOAD, survey.getTglupload());
        values.put(COLUMN_SURVEY_POHONTIPE, survey.getTipe());
        // Inserting Row
        db.insert(TABLE_SURVEY, null, values);
        db.close(); // Closing database connection
    }

    // Updating single survey
    public int updateSurvey(int idSurvey, String idPohon, String idPetani, String dbh, String lat, String lng, String keterangan,
                            String statusPoh, String katagoriPoh, String img, String tgl, String tglUpload, String tipePoh) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues recordImage = new ContentValues();
        recordImage.put(COLUMN_SURVEY_ID, idSurvey);
        recordImage.put(COLUMN_SURVEY_POHON_ID, idPohon);
        recordImage.put(COLUMN_SURVEY_PETANI_ID, idPetani);
        recordImage.put(COLUMN_SURVEY_DBH, dbh);
        recordImage.put(COLUMN_SURVEY_LAT, lat);
        recordImage.put(COLUMN_SURVEY_LONG, lng);
        recordImage.put(COLUMN_SURVEY_KETERANGAN, keterangan);
        recordImage.put(COLUMN_SURVEY_POHONSTATUS, statusPoh);
        recordImage.put(COLUMN_SURVEY_POHONKATAGORI, katagoriPoh);
        recordImage.put(COLUMN_SURVEY_IMG, img);
        recordImage.put(COLUMN_SURVEY_TGL, tgl);
        recordImage.put(COLUMN_SURVEY_TGLUPLOAD, tglUpload);
        recordImage.put(COLUMN_SURVEY_POHONTIPE, tipePoh);
        return database.update(TABLE_SURVEY, recordImage, COLUMN_SURVEY_POHON_ID+"= " + idPohon, null);
    }


    // Updating single survey
    public int updatePohon(int idPohon, String lastUpdate) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues recordImage = new ContentValues();
        recordImage.put(COLUMN_POHON_ID, idPohon);
        recordImage.put(COLUMN_POHON_LASTUPDATE, lastUpdate);
        return database.update(TABLE_POHON, recordImage, COLUMN_POHON_ID+"= " + idPohon, null);
    }


    public int updatePohonStatus(int idPohon, String statusPohon) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues recordImage = new ContentValues();
        recordImage.put(COLUMN_POHON_ID, idPohon);
        recordImage.put(COLUMN_POHON_STATUS, statusPohon);
        return database.update(TABLE_POHON, recordImage, COLUMN_POHON_ID+"= " + idPohon, null);
    }
    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_USER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_USER_ID, cursor.getString(0));
                map.put(COLUMN_USER_NAME, cursor.getString(1));
                map.put(COLUMN_USER_ADDRESS, cursor.getString(2));
                map.put(COLUMN_USER_EMAIL, cursor.getString(3));
                map.put(COLUMN_USER_USERNAME, cursor.getString(4));
                map.put(COLUMN_USER_PASSWORD, cursor.getString(5));
				map.put(COLUMN_PETANIPERSIL, cursor.getString(6));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }



    /**
     * Get list of Provinsi from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllProvinsi() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_PROVINSI;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_PROVINSI_ID, cursor.getString(0));
                map.put(COLUMN_PROVINSI_KODE, cursor.getString(1));
                map.put(COLUMN_PROVINSI_NAME, cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Getting Provinsi
     * */
    public List<String> getProvinsi(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROVINSI;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public Cursor getProvinsiCursor() {

        // Select All Query
        String selectQuery = "SELECT provinsi_id AS _id, * FROM " + TABLE_PROVINSI;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     * Get list of Kab Kota from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllKabKota() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_KABKOTA;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_KABKOTA_ID, cursor.getString(0));
                map.put(COLUMN_KABKOTA_PROV_ID, cursor.getString(1));
                map.put(COLUMN_KABKOTA_KODE, cursor.getString(2));
                map.put(COLUMN_KABKOTA_NAME, cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }


    /**
     * Getting Kabkota
     * */
    public List<String> getKabKota(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_KABKOTA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public Cursor getKabkotaCursor() {

        // Select All Query
        String selectQuery = "SELECT kabkota_id AS _id, * FROM " + TABLE_KABKOTA;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }


    /**
     * Get list of Kecamatan from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllKecamatan() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_KECAMATAN;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_KECAMATAN_ID, cursor.getString(0));
                map.put(COLUMN_KECAMATAN_KAB_ID, cursor.getString(1));
                map.put(COLUMN_KECAMATAN_KODE, cursor.getString(2));
                map.put(COLUMN_KECAMATAN_NAME, cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Getting Kecamatan
     * */
    public List<String> getKecamatan(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_KECAMATAN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public Cursor getKecamatanCursor() {

        // Select All Query
        String selectQuery = "SELECT kecamatan_id AS _id, * FROM " + TABLE_KECAMATAN;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     * Get list of Desa from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllDesa() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_DESA;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_DESA_ID, cursor.getString(0));
                map.put(COLUMN_DESA_KEC_ID, cursor.getString(1));
                map.put(COLUMN_DESA_NAME, cursor.getString(2));
                map.put(COLUMN_DESA_INITIAL, cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Getting Desa
     * */
    public List<String> getDesa(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DESA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public Cursor getDesaCursor() {

        // Select All Query
        String selectQuery = "SELECT desa_id AS _id, * FROM " + TABLE_DESA;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }
    /**
     * Get list of Gapoktan from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllGapoktan() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_GAPOKTAN;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_GAPOKTAN_ID, cursor.getString(0));
                map.put(COLUMN_GAPOKTAN_DESA_ID, cursor.getString(1));
                map.put(COLUMN_GAPOKTAN_NAME, cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Getting Gapoktan
     * */
    public List<String> getGapoktan(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GAPOKTAN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public Cursor getGapoktanCursor() {

        // Select All Query
        String selectQuery = "SELECT gapoktan_id AS _id, * FROM " + TABLE_GAPOKTAN;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }
    /**
     * Get list of KTH from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllKTH() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_KTH;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_KTH_ID, cursor.getString(0));
                map.put(COLUMN_KTH_GAPOKTAN_ID, cursor.getString(1));
                map.put(COLUMN_KTH_NAME, cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Getting KTH
     * */
    public List<String> getKTH(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_KTH;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public Cursor getKTHCursor() {

        // Select All Query
        String selectQuery = "SELECT kth_id AS _id, * FROM " + TABLE_KTH;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public List<Pohon> getPohonbyPersilId(String idPersil) {
        List<Pohon> pohonList = new ArrayList<Pohon>();

        Cursor cursor = getWritableDatabase().query(TABLE_POHON, new String[]{COLUMN_POHON_ID, COLUMN_POHON_KODE,
                        COLUMN_POHON_PERSIL_ID, COLUMN_POHON_INITIAL, COLUMN_POHON_NAMALATIN, COLUMN_POHON_NAMALOKAL,
                        COLUMN_POHON_STATUS, COLUMN_POHON_LASTUPDATE}, COLUMN_POHON_PERSIL_ID + "=?",
                new String[]{idPersil}, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {

            do {
                Pohon pohon = new Pohon();
                pohon.setId_pohon(cursor.getString(0));
                pohon.setKode_pohon(cursor.getString(1));
                pohon.setId_persil(cursor.getString(2));
                pohon.setInitial_pohon(cursor.getString(3));
                pohon.setNamalatin_pohon(cursor.getString(4));
                pohon.setNamalokal_pohon(cursor.getString(5));
                pohon.setStatus_pohon(cursor.getString(6));
                pohon.setPohon_lastupdate(cursor.getString(7));

                pohonList.add(pohon);
            } while (cursor.moveToNext());
        }else {

        }
        cursor.close();


        // return contact
        return pohonList;
    }

    public List<Persil> getPersil(String idKTH) {
        List<Persil> persilList = new ArrayList<Persil>();

        Cursor cursor = getWritableDatabase().query(TABLE_PERSIL, new String[]{COLUMN_PERSIL_ID, COLUMN_PERSIL_DESA_ID,
                COLUMN_PERSIL_KTH_ID, COLUMN_PERSIL_NOMOR}, COLUMN_PERSIL_KTH_ID + "=?",
                new String[]{idKTH}, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {

            do {
                Persil persil = new Persil();
                persil.setId_persil(cursor.getString(0));
                persil.setId_desa(cursor.getString(1));
                persil.setId_kth(cursor.getString(2));
                persil.setPersil_nomor(cursor.getString(3));

                persilList.add(persil);
            } while (cursor.moveToNext());
        }else {

        }
        cursor.close();


        // return contact
        return persilList;
    }

    public List<Petani> getIDPetani(String nama) {
        List<Petani> petaniList = new ArrayList<Petani>();

        Cursor cursor = getWritableDatabase().query(TABLE_USER, new String[]{COLUMN_USER_ID, COLUMN_USER_NAME,
                        COLUMN_USER_ADDRESS, COLUMN_USER_EMAIL, COLUMN_USER_USERNAME, COLUMN_USER_PASSWORD, COLUMN_PETANIPERSIL}, COLUMN_USER_USERNAME + "=?",
                new String[]{nama}, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {

            do {
                Petani petani = new Petani();
                petani.setPersilId(cursor.getString(0));
                petani.setPetaniName(cursor.getString(1));
                petani.setPetaniAddress(cursor.getString(2));
                petani.setPetaniEmail(cursor.getString(3));
                petani.setPetaniUname(cursor.getString(4));
                petani.setPetaniPassword(cursor.getString(5));
                petani.setPersilId(cursor.getString(6));

                petaniList.add(petani);
            } while (cursor.moveToNext());
        }else {

        }
        cursor.close();


        // return contact
        return petaniList;
    }
    /**
     * Get list of Persil from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllPersil() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_PERSIL;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_PERSIL_ID, cursor.getString(0));
                map.put(COLUMN_PERSIL_DESA_ID, cursor.getString(1));
                map.put(COLUMN_PERSIL_KTH_ID, cursor.getString(2));
                map.put(COLUMN_PERSIL_NOMOR, cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    public Pohon getPohonbyIDPoh(String idPohon) {


        Cursor cursor = getReadableDatabase().query(TABLE_POHON, new String[]{COLUMN_POHON_ID, COLUMN_POHON_KODE,
                        COLUMN_POHON_PERSIL_ID, COLUMN_POHON_INITIAL, COLUMN_POHON_NAMALATIN, COLUMN_POHON_NAMALOKAL, COLUMN_POHON_STATUS, COLUMN_POHON_LASTUPDATE}, COLUMN_POHON_ID + "=?",
                new String[]{idPohon}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

                Pohon pohon = new Pohon(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));

        return pohon;
    }


    public List<Pohon> getPohon(String idPersil) {
        List<Pohon> pohonList = new ArrayList<Pohon>();

        Cursor cursor = getWritableDatabase().query(TABLE_POHON, new String[]{COLUMN_POHON_ID, COLUMN_POHON_KODE,
                COLUMN_POHON_PERSIL_ID, COLUMN_POHON_INITIAL, COLUMN_POHON_NAMALATIN, COLUMN_POHON_NAMALOKAL, COLUMN_POHON_STATUS}, COLUMN_POHON_PERSIL_ID + "=?",
                new String[]{idPersil}, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {

            do {
                Pohon pohon = new Pohon();
                pohon.setId_pohon(cursor.getString(0));
                pohon.setKode_pohon(cursor.getString(1));
                pohon.setId_persil(cursor.getString(2));
                pohon.setInitial_pohon(cursor.getString(3));
                pohon.setNamalatin_pohon(cursor.getString(4));
                pohon.setNamalokal_pohon(cursor.getString(5));

                pohonList.add(pohon);
            } while (cursor.moveToNext());
        }else {

        }
        cursor.close();

        // return contact
        return pohonList;
    }


    /**
     * Get list of Pohon from SQLite DB as Array List
     * @return
     */
    public List<HashMap<String,String>> getAllPohonSurvey() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_POHONSURVEY;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_POHONSURVEY_ID, cursor.getString(0));
                map.put(COLUMN_POHONSURVEY_IDPOHON, cursor.getString(1));


                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Get list of Pohon from SQLite DB as Array List
     * @return
     */
    public List<HashMap<String,String>> getAllPohon() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_POHON;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_POHON_ID, cursor.getString(0));
                map.put(COLUMN_POHON_KODE, cursor.getString(1));
                map.put(COLUMN_POHON_PERSIL_ID, cursor.getString(2));
                map.put(COLUMN_POHON_INITIAL, cursor.getString(3));
                map.put(COLUMN_POHON_NAMALATIN, cursor.getString(4));
                map.put(COLUMN_POHON_NAMALOKAL, cursor.getString(5));
                map.put(COLUMN_POHON_STATUS, cursor.getString(6));

                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }


    public List<Pohon> getPohon() {
        List<Pohon> pohonList = new ArrayList<Pohon>();
        String selectQuery = "SELECT  * FROM "+TABLE_POHON;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Pohon pohon = new Pohon();
                pohon.setId_pohon(cursor.getString(0));
                pohon.setKode_pohon(cursor.getString(1));
                pohon.setId_persil(cursor.getString(2));
                pohon.setInitial_pohon(cursor.getString(3));
                pohon.setNamalatin_pohon(cursor.getString(4));
                pohon.setNamalokal_pohon(cursor.getString(5));
                pohon.setStatus_pohon(cursor.getString(6));
                pohon.setPohon_lastupdate(cursor.getString(7));
                pohonList.add(pohon);
            } while (cursor.moveToNext());
        }
        database.close();
        return pohonList;
    }

    //RETRIEVE DATA AND FILTER
    public Cursor getPohonListByKeyword(String searchTerm) {

        String[] columns={COLUMN_POHON_ID,COLUMN_POHON_KODE,COLUMN_POHON_NAMALOKAL,COLUMN_POHON_LASTUPDATE};
        Cursor c=null;
        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+TABLE_POHON+" WHERE "+COLUMN_POHON_NAMALOKAL+" LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;
        }
        c=db.query(TABLE_POHON,columns,null,null,null,null,null);
        return c;
    }


   /* public Cursor  getPohonListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT rowid as " +
                COLUMN_POHON_ID + "," +
                COLUMN_POHON_KODE+ "," +
                COLUMN_POHON_NAMALOKAL + "," +
                COLUMN_POHON_LASTUPDATE +
                " FROM " + TABLE_POHON +
                " WHERE " +  COLUMN_POHON_NAMALOKAL + "  LIKE  '%" +search + "%' "
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }*/


    /**
     * Get list of Survey from SQLite DB as Array List
     * @return
     */
    public List<Survey> getAllSurvey() {
        List<Survey> surveyList = new ArrayList<Survey>();

        String selectQuery = "SELECT  * FROM "+TABLE_SURVEY;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Survey survey = new Survey();
                survey.setId_survey(cursor.getString(0));
                survey.setId_pohon(cursor.getString(1));
                survey.setId_petani(cursor.getString(2));
                survey.setDiameter(cursor.getString(3));
                survey.setLatitude(cursor.getString(4));
                survey.setLongitude(cursor.getString(5));
                survey.setKeterangan(cursor.getString(6));
                survey.setStatuspohon(cursor.getString(7));
                survey.setKatagoripohon(cursor.getString(8));
                survey.setImg(cursor.getString(9));
                survey.setTgl(cursor.getString(10));
                survey.setTglupload(cursor.getString(11));
                survey.setTipe(cursor.getString(12));
                surveyList.add(survey);
            } while (cursor.moveToNext());
        }
        database.close();
        return surveyList;
    }

    //TODO : EDITED
    public Survey getSurvey(String pohonId) {
        Survey survey = new Survey();
        // array of columns to fetch
        String[] columns = {
                COLUMN_SURVEY_ID,
                COLUMN_SURVEY_POHON_ID,
                COLUMN_SURVEY_PETANI_ID,
                COLUMN_SURVEY_DBH,
                COLUMN_SURVEY_LAT,
                COLUMN_SURVEY_LONG,
                COLUMN_SURVEY_KETERANGAN,
                COLUMN_SURVEY_POHONSTATUS,
                COLUMN_SURVEY_POHONKATAGORI,
                COLUMN_SURVEY_IMG,
                COLUMN_SURVEY_TGL,
                COLUMN_SURVEY_TGLUPLOAD,
                COLUMN_SURVEY_POHONTIPE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_SURVEY_POHON_ID + " = ?";

        // selection argument
        String[] selectionArgs = {pohonId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_SURVEY, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        if (cursor.moveToFirst()){
            do {
                survey.setId_survey(cursor.getString(0));
                survey.setId_pohon(cursor.getString(1));
                survey.setId_petani(cursor.getString(2));
                survey.setDiameter(cursor.getString(3));
                survey.setLatitude(cursor.getString(4));
                survey.setLongitude(cursor.getString(5));
                survey.setKeterangan(cursor.getString(6));
                survey.setStatuspohon(cursor.getString(7));
                survey.setKatagoripohon(cursor.getString(8));
                survey.setImg(cursor.getString(9));
                survey.setTgl(cursor.getString(10));
                survey.setTglupload(cursor.getString(11));
                survey.setTipe(cursor.getString(12));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return survey;
    }

    /**
     * This method to check user exist or not
     *
     * @param petaniId
     * @return true/false
     */
    public boolean checkUser(String petaniId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_ID + " = ?";

        // selection argument
        String[] selectionArgs = {petaniId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param provinsiId
     * @return true/false
     */
    public boolean checkProvinsi(String provinsiId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_PROVINSI_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_PROVINSI_ID + " = ?";

        // selection argument
        String[] selectionArgs = {provinsiId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_PROVINSI, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param kabkotaiId
     * @return true/false
     */
    public boolean checkKabKota(String kabkotaiId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_KABKOTA_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_KABKOTA_ID + " = ?";

        // selection argument
        String[] selectionArgs = {kabkotaiId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_KABKOTA, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param kecamatanId
     * @return true/false
     */
    public boolean checkKecamatan(String kecamatanId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_KECAMATAN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_KECAMATAN_ID + " = ?";

        // selection argument
        String[] selectionArgs = {kecamatanId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_KECAMATAN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param desaId
     * @return true/false
     */
    public boolean checkDesa(String desaId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_DESA_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_DESA_ID + " = ?";

        // selection argument
        String[] selectionArgs = {desaId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_DESA, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param gapoktanId
     * @return true/false
     */
    public boolean checkGapoktan(String gapoktanId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_GAPOKTAN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_GAPOKTAN_ID + " = ?";

        // selection argument
        String[] selectionArgs = {gapoktanId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_GAPOKTAN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param kthId
     * @return true/false
     */
    public boolean checkKTH(String kthId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_KTH_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_KTH_ID + " = ?";

        // selection argument
        String[] selectionArgs = {kthId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_KTH, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param persilId
     * @return true/false
     */
    public boolean checkPersil(String persilId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_PERSIL_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_PERSIL_ID + " = ?";

        // selection argument
        String[] selectionArgs = {persilId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_PERSIL, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param pohonId
     * @return true/false
     */
    public boolean checkPohon(String pohonId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_POHON_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_POHON_ID + " = ?";

        // selection argument
        String[] selectionArgs = {pohonId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_POHON, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    public boolean checkPohonSurvey(String pohonsurveyId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_POHONSURVEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_POHONSURVEY_ID + " = ?";

        // selection argument
        String[] selectionArgs = {pohonsurveyId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_POHONSURVEY, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    /**
     * This method to check user exist or not
     *
     * @param surveyId
     * @return true/false
     */
    public boolean checkSurvey(String surveyId) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_SURVEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_SURVEY_ID + " = ?";

        // selection argument
        String[] selectionArgs = {surveyId};

        // query user table with condition
        Cursor cursor = db.query(TABLE_SURVEY, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                "petani_id"
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = "petani_uname" + " = ?" + " AND " + "petani_password" + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        Cursor cursor = db.query("petani", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    // Deleting All
    public void deleteSurvey() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_SURVEY );
        database.close();
    }


    public void deleterow(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+TABLE_SURVEY+" where Google='"+id+"'");
        database.close();
    }


    /**
     * Compose JSON out of SQLite records
     * @return
     */
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String katagori = "";
        String tipe = "";
        String selectQuery = "SELECT * FROM survey";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idSurvey", cursor.getString(0));
                map.put("idPohon", cursor.getString(1));
                map.put("dbh", cursor.getString(2));
                map.put("lat", cursor.getString(3));
                map.put("lng", cursor.getString(4));
                map.put("keterangan", cursor.getString(5));
                map.put("status", cursor.getString(6));

                if (cursor.getString(7).equals("Pohon Mati")) {
                    katagori = "0";
                } else if (cursor.getString(7).equals("Pohon Hidup")) {
                    katagori = "1";
                }
                map.put("katagori", katagori);

                map.put("img", cursor.getString(8));
                map.put("tgl", cursor.getString(9));
                map.put("tglUpload", cursor.getString(10));

                if ( cursor.getString(11).equals("Penanaman")) {
                    tipe = "0";
                } else if ( cursor.getString(11).equals("Pemantauan")) {
                    tipe = "1";
                }
                map.put("tipe", tipe);

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
        String selectQuery = "SELECT  * FROM tbl_survey where status = '"+"no"+"'";
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
        String updateQuery = "Update tbl_survey set status = '"+ status +"' where id_survey="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
