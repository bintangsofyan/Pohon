package com.projgaia.ereswe.surveypohon.Model;

/**
 * Created by ERESWE on 20/02/2017.
 */

public class Alokasi {
    private String  username,  nama_pohon, kode_pohon;
    private String userid, id_alokasi, pohonid, status;

    public Alokasi() {
    }

    public Alokasi(String id_survey, String userid, String username, String nama_pohon, String pohonid, String kode_pohon, String status ) {
        this.id_alokasi = id_survey;
        this.username = username;
        this.nama_pohon = nama_pohon;
        this.userid = userid;
        this.pohonid = pohonid;
        this.kode_pohon = kode_pohon;
    }

    //id survey
    public String getIdAlokasi() {

        return id_alokasi;
    }

    public void setIdAlokasi(String id_alokasi) {

        this.id_alokasi = id_alokasi;
    }

    // username
    public String getNamauser() {

        return username;
    }

    public void setNamauser(String username) {
        this.username = username;
    }

    // userid
    public String getUserid() {

        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    //namapohon
    public String getNamaPoh() {

        return nama_pohon;
    }

    public void setNamaPoh (String nama_pohon) {

        this.nama_pohon = nama_pohon;
    }

    //kodepohon
    public String getKodePoh() {

        return kode_pohon;
    }

    public void setKodePoh (String kode_pohon) {

        this.kode_pohon = kode_pohon;
    }

    //id pohon
    public String getPohonid() {

        return pohonid;
    }

    public void setPohonid (String pohonid) {

        this.pohonid = pohonid;
    }

    //status
    public String getStatus() {

        return status;
    }

    public void setStatus (String status) {

        this.status = status;
    }


}
