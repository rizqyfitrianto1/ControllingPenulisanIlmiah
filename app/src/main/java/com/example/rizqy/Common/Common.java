package com.example.rizqy.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.rizqy.Model.Dosen;
import com.example.rizqy.Model.Mahasiswa;

public class Common {
    public static Mahasiswa currentMahasiswa;

    public static final String MHS_KEY = "Mahasiswa";
    public static final String PWD_KEY = "Password";

    public static boolean isConnectedToInterner(Context baseContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager)baseContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static Dosen currentDosen;

    public static final String DSN_KEY = "Dosen";
    public static final String PAS_KEY = "Password";


}
