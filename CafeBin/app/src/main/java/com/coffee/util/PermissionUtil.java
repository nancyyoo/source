package com.coffee.util;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

/**
 *
 * 퍼미션 취득을 위한 유틸.
 * 안드로이드 M 버전부터 퍼미션 사용시 권한 취득이 필요.
 * Reference: <a href="https://github.com/googlesamples/android-RuntimePermissions">Android RuntimePermissions Sample</a>
 *
 * @author ymchae
 *
 */
public final class PermissionUtil {

    // Calendar group.
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;

    // Camera group.
    public static final String CAMERA = Manifest.permission.CAMERA;

    // Contacts group.
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;

    // Location group.
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    // Microphone group.
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    // Phone group.
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String USE_SIP = Manifest.permission.USE_SIP;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;

    // Sensors group.
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    public static final String USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT;

    // SMS group.
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    public static final String READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS";

    // Bookmarks group.
    public static final String READ_HISTORY_BOOKMARKS = "com.android.browser.permission.READ_HISTORY_BOOKMARKS";
    public static final String WRITE_HISTORY_BOOKMARKS = "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS";

    /*
     * 퍼미션 요청 결과의 성공 유무를 반환.
     */
    public static boolean hasGranted(int grantResult) {
        return grantResult == PERMISSION_GRANTED;
    }

    /**
     * 다수의 퍼미션 요청 결과의 성공 유무를 반환.
     * 요청한 권한 중 하나라도 권한을 얻지 못했다면 실패를 반환.
     */
    public static boolean hasGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (!hasGranted(result)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 파라미터로 받은 퍼미션의 권한을 사용할 수 있는지 결과를 반환.
     * M버전 이하는 무조건 true를 반환.
     */
    public static boolean hasSelfPermission(Context context, String permission) {
        if (isUpperMOS()) {
            return permissionHasGranted(context, permission);
        }
        return true;
    }

    /**
     * 파라미터로 받은 다수의 퍼미션들의 권한을 사용할 수 있는지 결과를 반환.
     * 사용할 수 없는 퍼미션이 하나라도 있으면 실패를 반환.
     *
     * Always returns true on platforms below M.
     */
    public static boolean hasSelfPermissions(Context context, String[] permissions) {
        if (!isUpperMOS()) {
            return true;
        }

        for (String permission : permissions) {
            if (!permissionHasGranted(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 권한 요청 전 상세 설명을 보여주기 위한 메서드.
     * 파라미터로 넘어온 msg를 토스트로 화면에 보여줌.
     */
    public static void shouldShowRequestPermissionRationale(Activity activity, String permission, String msg) {

        }


    /**
     * 퍼미션 사용 권한 요청.
     */
    public static void requestAllPermissions( Activity activity, String[] permissions, int requestCode) {
        if (isUpperMOS()) {
            internalRequestPermissions(activity, permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void internalRequestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (activity == null) {
            throw new IllegalArgumentException("Given activity is null.");
        }
        activity.requestPermissions(permissions, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static boolean permissionHasGranted(Context context, String permission) {
        return hasGranted(context.checkSelfPermission(permission));
    }

    public static boolean isUpperMOS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean checkDefualtPermission(Context context) {
        String[] checkList = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};
        return hasSelfPermissions(context, checkList);
    }

    public static boolean checkAdPermission(Context context) {
        if (!isUpperMOS()) {
            return true;
        }

        boolean writePer = hasSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean readPer = hasSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean phonePer = hasSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        boolean windowPer = Settings.canDrawOverlays(context);

        if (!writePer || !readPer || !phonePer || !windowPer) {
            return false;
        }

        return true;
    }

    public static List<String> getDefaultPermissionList(Context context) {
        List<String> perList = new ArrayList<String>();

        boolean writePer = PermissionUtil.hasSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean readPer = PermissionUtil.hasSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean phonePer = PermissionUtil.hasSelfPermission(context, Manifest.permission.READ_PHONE_STATE);

        if (!writePer || !readPer || !phonePer) {
            if (!writePer)
                perList.add(Manifest.permission.ACCESS_COARSE_LOCATION);

            if (!readPer)
                perList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (!phonePer)
                perList.add(Manifest.permission.READ_PHONE_STATE);
        }

        return perList;
    }

    public static List<String> getAdPermissionList(Context context) {
        List<String> perList = new ArrayList<String>();

        boolean writePer = PermissionUtil.hasSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean readPer = PermissionUtil.hasSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean phonePer = PermissionUtil.hasSelfPermission(context, Manifest.permission.READ_PHONE_STATE);


        if (!writePer || !readPer || !phonePer) {
            if (!writePer)
                perList.add(Manifest.permission.ACCESS_COARSE_LOCATION);

            if (!readPer)
                perList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (!phonePer)
                perList.add(Manifest.permission.READ_PHONE_STATE);

        }

        return perList;
    }
}