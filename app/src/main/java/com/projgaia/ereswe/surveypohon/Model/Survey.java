package com.projgaia.ereswe.surveypohon.Model;

/**
 * Created by ERESWE on 15/07/2017.
 */

public class Survey {
    private String id_survey, id_pohon, id_petani, diameter, latitude, longitude, keterangan,
            statuspohon, katagoripohon, img, tgl, tglupload, tipe;

    public Survey() {
        this.id_petani = id_petani;
        this.id_pohon = id_pohon;
        this.tgl = tgl;
    }

    public Survey( String id_pohon, String id_petani, String diameter,
                   String latitude, String longitude, String keterangan, String statuspohon,
                   String katagoripohon, String img, String tgl, String tglupload, String tipe) {

        this.id_petani = id_petani;
        this.id_survey = id_survey;
        this.statuspohon = statuspohon;
        this.id_pohon = id_pohon;
        this.img = img;
        this.keterangan = keterangan;
        this.diameter = diameter;
        this.katagoripohon = katagoripohon;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tgl = tgl;
        this.tglupload = tglupload;
        this.tipe = tipe;
    }

    public Survey( String id_survey, String id_pohon, String id_petani, String diameter,
                   String latitude, String longitude, String keterangan, String statuspohon,
                   String katagoripohon, String img, String tgl, String tglupload, String tipe) {

        this.id_petani = id_petani;
        this.id_survey = id_survey;
        this.statuspohon = statuspohon;
        this.id_pohon = id_pohon;
        this.img = img;
        this.keterangan = keterangan;
        this.diameter = diameter;
        this.katagoripohon = katagoripohon;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tgl = tgl;
        this.tglupload = tglupload;
        this.tipe = tipe;
    }



    public String getId_petani() {
        return id_petani;
    }

    public void setId_petani(String id_petani) {
        this.id_petani = id_petani;
    }

    public String getId_survey() {
        return id_survey;
    }

    public void setId_survey(String id_survey) {
        this.id_survey = id_survey;
    }

    public String getId_pohon() {
        return id_pohon;
    }

    public void setId_pohon(String id_pohon) {
        this.id_pohon = id_pohon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatuspohon() {
        return statuspohon;
    }

    public void setStatuspohon(String statuspohon) {
        this.statuspohon = statuspohon;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getKatagoripohon() {
        return katagoripohon;
    }

    public void setKatagoripohon(String katagoripohon) {
        this.katagoripohon = katagoripohon;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {return longitude; }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getTglupload() {
        return tglupload;
    }

    public void setTglupload(String tglupload) {
        this.tglupload = tglupload;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}

