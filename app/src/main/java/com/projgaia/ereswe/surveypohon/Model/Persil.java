package com.projgaia.ereswe.surveypohon.Model;

/**
 * Created by ERESWE on 10/09/2017.
 */

public class Persil  {
    private String id_persil, id_desa, id_kth, persil_nomor;

        public Persil() {
        }

        public Persil(String id_persil, String id_desa, String id_kth, String persil_nomor ) {
            this.id_persil = id_persil;
            this.id_desa = id_desa;
            this.id_kth = id_kth;
            this.persil_nomor = persil_nomor;
        }

        public String getId_persil() {
            return id_persil;
        }

        public void setId_persil(String id_persil) {
            this.id_persil = id_persil;
        }

        public String getId_desa() {
        return id_desa;
    }

        public void setId_desa(String id_desa) {
        this.id_desa = id_desa;
    }


        public String getId_kth() {
            return id_kth;
        }

        public void setId_kth(String id_kth) {
            this.id_kth = id_kth;
        }


        public String getPersil_nomor() {
            return persil_nomor;
        }

        public void setPersil_nomor(String persil_nomor) {
            this.persil_nomor = persil_nomor;
        }


}
