package com.projgaia.ereswe.surveypohon.Model;

/**
 * Created by ERESWE on 01/02/2017.
 */

public class Pohon {
    private String id_pohon, kode_pohon, id_persil, namalatin_pohon, namalokal_pohon, initial_pohon, status_pohon, pohon_lastupdate;

    public Pohon() {
    }

    public Pohon(String id_pohon, String namalokal_pohon, String namalatin_pohon, String kode_pohon, String id_persil, String initial, String status_pohon, String pohon_lastupdate) {
        this.id_pohon = id_pohon;
        this.id_persil = id_persil;
        this.kode_pohon = kode_pohon;
        this.initial_pohon = initial_pohon;
        this.namalatin_pohon = namalatin_pohon;
        this.namalokal_pohon = namalokal_pohon;
        this.status_pohon = status_pohon;
        this.pohon_lastupdate = pohon_lastupdate;

    }

    public String getId_pohon() {
        return id_pohon;
    }

    public void setId_pohon(String id_pohon) {
        this.id_pohon = id_pohon;
    }

    public String getId_persil() {
        return id_persil;
    }

    public void setId_persil(String id_persil) {
        this.id_persil = id_persil;
    }

    public String getKode_pohon() {
        return kode_pohon;
    }

    public void setKode_pohon(String kode_pohon) {
        this.kode_pohon = kode_pohon;
    }

    public String getNamalokal_pohon() {
        return namalokal_pohon;
    }

    public void setNamalokal_pohon(String namalokal_pohon) {
        this.namalokal_pohon = namalokal_pohon;
    }

    public String getNamalatin_pohon() {
        return namalatin_pohon;
    }

    public void setNamalatin_pohon(String namalatin_pohon) {
        this.namalatin_pohon = namalatin_pohon;
    }

    public String getInitial_pohon() {
        return initial_pohon;
    }

    public void setInitial_pohon(String initial_pohon) {
        this.initial_pohon = initial_pohon;
    }

      public String getStatus_pohon() {
        return status_pohon;
    }

    public void setStatus_pohon(String status_pohon) {
        this.status_pohon = status_pohon;
    }

    public String getPohon_lastupdate() {
        return pohon_lastupdate;
    }

    public void setPohon_lastupdate(String pohon_lastupdate) {
        this.pohon_lastupdate = pohon_lastupdate;
    }


}
