package com.example.rizqy.Model;

public class Request {
    private String Tanggal;
    private String Judul;
    private String Subjudul;
    private String Npm;

    public Request() {
    }

    public Request(String tanggal, String judul, String subjudul, String npm) {
        Tanggal = tanggal;
        Judul = judul;
        Subjudul = subjudul;
        Npm = npm;
    }

    public String getNpm() {
        return Npm;
    }

    public void setNpm(String npm) {
        Npm = npm;
    }


    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getSubjudul() {
        return Subjudul;
    }

    public void setSubjudul(String subjudul) {
        Subjudul = subjudul;
    }
}
