package com.projgaia.ereswe.surveypohon.Helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projgaia.ereswe.surveypohon.BaseApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ERESWE on 18/12/2016.
 */

public class Helper extends BaseApp {

    /*alamat utama*/
    public static String BASE_URL = "http://gaia.id/monitoring/";

    /*fungsi cek kesamaan text*/
    public static boolean isCompare(EditText et1, EditText et2){
        String a = et1.getText().toString();
        String b = et2.getText().toString();
        /*jika a sama dengan b*/
        if (a.equals(b)){
            return false;
        }else{
            return true;
        }
    }

    /*fungsi untuk menampilkan pesan*/
    public static void pesan (Context c, String msg){
        Toast.makeText(c,msg,Toast.LENGTH_LONG).show();
    }

    /*fungsi untuk mengecek apakah isian kosong*/
    public static boolean isEmpty(EditText editText){
        /*jika banyak huruf lebih dari 0*/
        if (editText.getText().toString().trim().length() > 0){
            /*tidak dikembalikan*/
            return false;
        }else {
            /*kembalikan*/
            return true;
        }
    }

    /*fungsi untuk mengecek apakah isian kosong*/
    public static boolean isEmpty(TextView textView1, TextView textView2){
        /*jika banyak huruf lebih dari 0*/
        if (textView1.getText().toString().trim().length() > 0 && textView2.getText().toString().trim().length() > 0){
            /*tidak dikembalikan*/
            return false;
        }else {
            /*kembalikan*/
            return true;
        }
    }

    /*fungsi untuk mengecek latlong*/
    public static boolean notChange(TextView textView1, TextView textView2 ) {
        /*jika banyak huruf lebih dari 0*/
        if (textView1.getText().toString() != "0.0" && textView2.getText().toString() != "0.0") {
            /*tidak dikembalikan*/
            return false;
        } else {
            return true;
        }

    }



    /*fungsi untuk mengecek apakah isian kosong*/
    public static boolean isEmpty(TextView textView){
        /*jika banyak huruf lebih dari 0*/
        if (textView.getText().toString().trim().length() > 0){
            /*tidak dikembalikan*/
            return false;
        }else {
            /*kembalikan*/
            return true;
        }
    }

    /*fungsi untuk mengecek apakah isian kosong*/
    public static boolean passMinimum(EditText editText){
        /*jika banyak huruf lebih dari 6*/
        if (editText.getText().toString().trim().length() > 6){
            /*tidak dikembalikan*/
            return false;
        }else {
            /*kembalikan*/
            return true;
        }
    }

    // validasi untuk inputan email
    public static boolean isEmailValid(EditText email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString();

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}