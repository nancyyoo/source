package com.coffee.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import com.coffee.coffeebean.R;

import com.coffee.util.CommonUtil;
import com.coffee.util.Logger;
import com.coffee.util.PermissionUtil;

public class ActivityPermission extends Activity {

    private static final String TAG = "ActivityPermission";

    public static final String ALL_PERMISSION = "all_permission";
    public static final String WINDOW_PERMISSION = "win_permission";

    private static final int ALL_PERMISSION_REQ_CODE = 9090;
    private static final int WINDOW_PERMISSION_REQ_CODE = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate()");

        final String[] pers = getIntent().getStringArrayExtra(ALL_PERMISSION);
        final boolean winPer = getIntent().getBooleanExtra(WINDOW_PERMISSION, false);

        if (pers == null || pers.length == 0) {
            if (!winPer) {
                finish();
                return;
            }
        }

        requestOSPermission(pers, winPer);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d(TAG, "onRequestPermissionsResult");
        Logger.d(TAG, "req code : " + requestCode);
        Logger.d(TAG, "permissions : " + permissions);
        Logger.d(TAG, "grantResults : " + grantResults);

        boolean perResult = true;

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                perResult = false;
                break;
            }
        }

        if (perResult) {
            finishAct();
        }
        /**
         * ��Ȳ������ permission �źν� ó�� ���� Activity ���� ó����.
         */
//		else {
//			requestSettingPermission();
//		}
    }

    private void requestOSPermission(final String[] pers, final boolean winPer) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.dialog_permission_check, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        Button btAgree = (Button) view.findViewById(R.id.btn_agree);
        btAgree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pers != null && pers.length > 0) {
                    PermissionUtil.requestAllPermissions(ActivityPermission.this, pers, ALL_PERMISSION_REQ_CODE);
                }
                if (winPer) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, WINDOW_PERMISSION_REQ_CODE);
                }
                CommonUtil.dismissDialog(dialog);
            }
        });
        CommonUtil.showDialog(dialog);
    }

    /**
     * ��Ȳ�� ���� permission �źν� ó�� �� �� �ֵ��� �ҽ� ������ �۾�.
     */
//	private void requestSettingPermission() {
//		final Dialog dialog = new Dialog(this);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		View view = getLayoutInflater().inflate(R.layout.dialog_permission_check_complete, null);
//		dialog.setContentView(view);
//		dialog.setCancelable(false);
//
//		Button btAgree = (Button) view.findViewById(R.id.btn_agree);
//		btAgree.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//				intent.setData(Uri.parse("package:" + getPackageName()));
//				startActivity(intent);
//
//				CommonUtil.dismissDialog(dialog);
//				finishAct();
//			}
//		});
//		CommonUtil.showDialog(dialog);
//	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d(TAG, "requestCode : " + requestCode);
        Logger.d(TAG, "resultCode : " + resultCode);

        finishAct();
    }

    private void finishAct() {
        if (CommonUtil.isActivityRunning(this)) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(TAG, "onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy()");
    }
}