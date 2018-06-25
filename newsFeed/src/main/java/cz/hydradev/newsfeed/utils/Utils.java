package cz.hydradev.newsfeed.utils;

import android.os.Environment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static String getFileName(String url) {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + "/savedArchive/");
        if(!dir.exists()){
            dir.mkdirs();
        }

        String hashedUrl = "";
        if (url != null) {
            hashedUrl = Math.abs(url.hashCode()) + "";
        }

        return dir.toString() + File.separator  + hashedUrl + ".mht";
    }

    public static boolean isSdAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String reformatPublishedAt(String publishedAt) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = formatter.parse(publishedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy hh:mmaa");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(value);

        return dt;
    }
}
