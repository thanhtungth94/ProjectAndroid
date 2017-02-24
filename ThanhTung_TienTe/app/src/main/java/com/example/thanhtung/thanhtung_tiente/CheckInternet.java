package com.example.thanhtung.thanhtung_tiente;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by thanhtung on 09/12/2016.
 */
public class CheckInternet {
    private Context _context;

    //Hàm dựng khởi tạo đối tượng
    public CheckInternet(Context context) {
        this._context = context;
    }

    /**
     *
     * @return
     */
    public boolean checkMobileInternetConn() {
        //Tạo đối tương ConnectivityManager để trả về thông tin mạng
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //Nếu đối tượng khác null
        if (connectivity != null) {
            //Nhận thông tin mạng
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                //Tìm kiếm thiết bị đã kết nối được internet chưa
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}
