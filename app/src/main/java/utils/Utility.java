package utils;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class Utility {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static boolean checkAndRequestPermissions(Context context) {
        int permissionCamera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        int permissionwifi = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_WIFI_STATE);
        int phonestateCamera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);
        int writeStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int recordAudioPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (phonestateCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionwifi != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (recordAudioPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }



        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public static int age(int strDay, int strMonth, int strYear) {
        Date today = new Date();
     /*   int day = Integer.parseInt(strDay);
        int month = Integer.parseInt(strMonth);
        int year = Integer.parseInt(strYear);*/
        Date birthDate = new Date(strYear - 1900, strMonth - 1, strDay);
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(today));
        int age = (d2 - d1) / 10000;
        return age;
    }
    public static int hoursDifference(Date date1, Date date2) {


        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        if (date1.after(date2)) {
            return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
        }
        else
        {
            return (int) (date2.getTime() - date1.getTime()) / MILLI_TO_HOUR;
        }

    }
    public static Date UTCDateToLocalTime(String UtcDateFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(UtcDateFormat);
            SimpleDateFormat istDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeZone tz = TimeZone.getDefault();
            istDateFormat.setTimeZone(TimeZone.getTimeZone(tz.getID()));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date UTCDateToLocalTimeForWithT(String UtcDateFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(UtcDateFormat);
            SimpleDateFormat istDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            TimeZone tz = TimeZone.getDefault();
            istDateFormat.setTimeZone(TimeZone.getTimeZone(tz.getID()));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void applyFont(final Context context, final View root, final String fontName) {
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    applyFont(context, viewGroup.getChildAt(i), fontName);
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
        } catch (Exception e) {
            Log.e("ProjectName", String.format("Error occured when trying to apply %s font for %s view", fontName, root));
            e.printStackTrace();
        }
    }
    public static Date UTCDateToLocalTimeForWithoutT(String UtcDateFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(UtcDateFormat);
            SimpleDateFormat istDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeZone tz = TimeZone.getDefault();
            istDateFormat.setTimeZone(TimeZone.getTimeZone(tz.getID()));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515*1.60934;
        return (dist*1000);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
    /**
     * Is valid email boolean.
     *
     * @param target the target
     * @return the boolean
     */
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }



    public static  int calculateAge(Date birthdate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();

        int yearDifference = today.get(Calendar.YEAR)
                - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth
                    .get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }

        }

        return yearDifference;
    }

    /**
     * Is valid password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean IsValidPassword(String password) {
        boolean hasDigit = containsDigit(password);
        boolean hasCharacter = password.matches(".*[a-zA-Z]+.*");
        boolean hasCorrectLength = (password.length() >= 6);
        return hasDigit & hasCharacter & hasCorrectLength;
    }
    /**
     * Contains digit boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public static boolean containsDigit(String s) {
        boolean containsDigit = false;
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }
        return containsDigit;
    }
    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 10) {
            } else if (phone.length() == 10) {
                check = true;
            }
        }
        if (!phone.equals("")) {
            if (phone.length() == 10) {
                long p =Long.parseLong(phone);
                if (p == 0) {
                    check = false;
                } else {
                    check = true;
                }
            }
            else
            {
                check = false;
            }
        } else {
            check = false;
        }
        return check;
    }

}