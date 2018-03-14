package com.projgaia.ereswe.surveypohon.Model;

/**
 * Created by ERESWE on 29/01/2017.
 */

public class User {
    private String id_user, nama_user, password, username, level, id_level;

    public User() {
    }

    public User(String id_user, String nama_user, String level, String password, String username, String id_level ) {
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.level = level;
        this.password = password;
        this.username = username;
        this.id_level = id_level;
    }

    public String getId() {
        return id_user;
    }

    public void setId(String id_user) {
        this.id_user = id_user;
    }


    public String getIdLevel() {
        return id_level;
    }

    public void setIdLevel(String id_level) {
        this.id_level = id_level;
    }


    public String getNama() {
        return nama_user;
    }

    public void setNama(String nama_user) {
        this.nama_user = nama_user;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel (String level) {
        this.level = level;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

}
