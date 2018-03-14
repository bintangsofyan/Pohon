package com.projgaia.ereswe.surveypohon.Model;

import java.text.SimpleDateFormat;

/**
 * Created by ERESWE on 17/01/2017.
 */

public class FotoPohon {

    private String id_image;
    private String path, id_pohon, id_angle, id_survey, status;
    private String datetimeLong;
    private SimpleDateFormat df = new SimpleDateFormat("MMMM d, yy  h:mm");



    public FotoPohon(String id_survey, String path, String id_pohon, String id_angle, String datetimeLong) {
        this.id_image = id_image;
        this.id_survey = id_survey;
        this.path = path;
        this.id_pohon = id_pohon;
        this.id_angle = id_angle;
        this.datetimeLong = datetimeLong;
    }


    public FotoPohon(String id_image, String id_survey, String path, String id_pohon, String id_angle, String datetimeLong) {
        this.id_image = id_image;
        this.id_survey = id_survey;
        this.path = path;
        this.id_pohon = id_pohon;
        this.id_angle = id_angle;
        this.datetimeLong = datetimeLong;
    }

    public FotoPohon() {

    }

    //get Id
    public String getId_image() {
        return this.id_image; }


    public void setId_image(String id_image) {
        this.id_image = id_image; }

    //get Id Survey
    public String getId_survey() {
        return this.id_survey; }


    public void setId_survey(String id_survey) {
        this.id_survey = id_survey; }

// get path
    public void setPath(String path) {
        this.path = path; }

    public String getPath() {
        return path; }



  //get id pohon
    public String getId_pohon() {
        return this.id_pohon; }

    public String setId_pohon(String id_pohon) {
        this.id_pohon = id_pohon;
        return id_pohon;
    }



    //get id angle
    public String getId_angle() {
        return this.id_angle; }

    public  void setId_angle(String id_angle) {
        this.id_angle = id_angle; }




    //Gets datetimeLong.


    public String getDatetimeLong() {
        return datetimeLong; }

    public void setDatetime(String datetimeLong) {
        this.datetimeLong = datetimeLong;
    }


    //get Id Survey
    public String getStatus() {
        return this.status; }


    public void setStatus(String status) {
        this.status = status; }



/*










    */
/**
     * Gets datetime.
     *
     * @return Value of datetime.
     *//*

    public Calendar getDatetime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetimeLong);
        return cal;
    }

    */
/**
     * Sets new datetimeLong.
     *
     * @param datetimeLong New value of datetimeLong.
     *//*



    */
/**
     * Sets new datetime.
     *
     * @param datetime New value of datetime.
     *//*

    public void setDatetime(Calendar datetime) {
        this.datetimeLong = datetime.getTimeInMillis();
    }






    @Override public String toString() {
        return df.format(getDatetime().getTime());
    }
*/

}