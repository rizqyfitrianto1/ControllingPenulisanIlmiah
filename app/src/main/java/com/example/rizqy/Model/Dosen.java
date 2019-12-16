package com.example.rizqy.Model;

public class Dosen {
    private String Nama;
    private String Notelepon;
    private String Email;
    private String Password;
    private String Kelas;
    private String Kodedospem;

    public Dosen(){
    }
    public Dosen(String nama, String notelepon, String email, String password, String kelas){
        Nama = nama;
        Notelepon = notelepon;
        Email = email;
        Password = password;
        Kelas = kelas;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNotelepon() {
        return Notelepon;
    }

    public void setNotelepon(String notelepon) {
        Notelepon = notelepon;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getKelas() {
        return Kelas;
    }

    public void setKelas(String kelas) {
        Kelas = kelas;
    }

    public String getKodedospem() {
        return Kodedospem;
    }

    public void setKodedospem(String kodedospem) {
        Kodedospem = kodedospem;
    }
}
