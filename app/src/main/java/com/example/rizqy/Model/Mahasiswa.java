package com.example.rizqy.Model;

public class Mahasiswa {
    private String Nama;
    private String Npm;
    private String Kelas;
    private String Jurusan;
    private String Password;
    private String Kodedospem;

    public Mahasiswa(){
    }
    public Mahasiswa(String nama, String kelas, String jurusan, String password, String kodedospem){
        Nama = nama;
        Kelas = kelas;
        Jurusan = jurusan;
        Password = password;
        Kodedospem = kodedospem;
    }

    public String getKodedospem() {
        return Kodedospem;
    }

    public void setKodedospem(String kodedospem) {
        Kodedospem = kodedospem;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNpm() {
        return Npm;
    }

    public void setNpm(String npm) {
        Npm = npm;
    }

    public String getKelas() {
        return Kelas;
    }

    public void setKelas(String kelas) {
        Kelas = kelas;
    }

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String jurusan) {
        Jurusan = jurusan;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
