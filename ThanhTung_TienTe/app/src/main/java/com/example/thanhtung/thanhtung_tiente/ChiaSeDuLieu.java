package com.example.thanhtung.thanhtung_tiente;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by thanhtung on 09/12/2016.
 */
public class ChiaSeDuLieu {
    public static void SendSMS(Context con, String strlistMobile, String message) {
        if (con.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_TELEPHONY)) {

            if (strlistMobile != null && con != null) {
                strlistMobile = strlistMobile.replace(",", ";").trim();

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.putExtra("sms_body", message);
                smsIntent.putExtra("address", strlistMobile);
                smsIntent.setType("vnd.android-dir/mms-sms");
                con.startActivity(smsIntent);
            }
        } else {
            Toast.makeText(con, "Thiet bi ko ho gui duoc tin nhan.",
                    Toast.LENGTH_SHORT).show();

        }

    }

    /**
     *
     * @param con
     * @param to
     * @param cc
     * @param subject
     * @param message
     */
    public static void SendEmail(Context con, String[] to, String[] cc,
                                 String subject, String message) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        emailIntent.setType("message/rfc822");

        con.startActivity(Intent.createChooser(emailIntent, "Email"));

    }

}
