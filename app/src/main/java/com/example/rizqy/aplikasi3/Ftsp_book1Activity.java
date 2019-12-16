package com.example.rizqy.aplikasi3;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import javax.net.ssl.HttpsURLConnection;

public class Ftsp_book1Activity extends AppCompatActivity {

    private static final int MY_PERMISSION = 1;

    ImageView imageView;

    ProgressDialog mProgressDialog;

    double file_size=0;
    String file_name;

    PDFView ftibook1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftsp_book1);
        //untuk judul ActionBar
        getSupportActionBar().setTitle("Panduan PI FTSP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //untuk menampilkan pdf
        ftibook1=(PDFView)findViewById(R.id.pdfbook1);
        ftibook1.fromAsset("pedoman_ftsp.pdf").load();

        imageView = (ImageView)findViewById(R.id.download);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION);
                }else{
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Controlling_PI/");
                    try {
                        dir.mkdir();
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(Ftsp_book1Activity.this, "Cannot create folder!", Toast.LENGTH_SHORT).show();
                    }
                    //ini lokasi file yang akan didownload
                    new Ftsp_book1Activity.DownloadTask().execute("http://sap.gunadarma.ac.id/upload/KK-032230.pdf");
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission granted!", Toast.LENGTH_SHORT).show();

                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Controlling_PI/");
                    try {
                        dir.mkdir();
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(Ftsp_book1Activity.this, "Cannot create folder!", Toast.LENGTH_SHORT).show();
                    }
                    //ini lokasi file yang akan didownload
                    new Ftsp_book1Activity.DownloadTask().execute("http://baak.gunadarma.ac.id/infopi/files/FORM-ISIAN-SERTIFIKAT-SETARA-SARJANA-MUDA.pdf");
                }else {
                    Toast.makeText(this,"Permission not granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            file_name = strings[0].substring(strings[0].lastIndexOf("/") + 1);
            try {
                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(strings[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                        return "Server returned HTTP" + connection.getResponseCode() + " "
                                + connection.getResponseMessage();
                    }
                    int fileLenght = connection.getContentLength();
                    file_size = fileLenght;

                    input = connection.getInputStream();
                    output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Controlling_PI/" + file_name);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        if (isCancelled()) {
                            return null;
                        }
                        total += count;
                        if (fileLenght > 0) {
                            publishProgress((int) (total * 100 / fileLenght));
                        }
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    return e.toString();
                } finally {
                    try {
                        if (output != null) {
                            output.close();
                        }
                        if (input != null) {
                            input.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }finally {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog =  new ProgressDialog(Ftsp_book1Activity.this);
            mProgressDialog.setTitle("downloading...");
            mProgressDialog.setMessage("File size: 0 MB");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);

            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(Ftsp_book1Activity.this,"Download cancelled!", Toast.LENGTH_SHORT).show();

                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Controlling_PI/" + file_name);
                    try {
                        dir.delete();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);
            mProgressDialog.setMessage("File size: " + new DecimalFormat("##.##").format(file_size / 1000000) + " MB");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if(result != null){
                Toast.makeText(Ftsp_book1Activity.this, "Error: " + result, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Ftsp_book1Activity.this, "Downloaded!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
