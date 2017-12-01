package com.tlcn.mvpapplication;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tlcn.mvpapplication.mvp.details.view.DetailsView;
import com.tlcn.mvpapplication.mvp.main.view.MainActivity;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.SystemUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tskil on 8/23/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gpsTracker = new GPSTracker(this);
        if (getIntent().getData() != null && getIntent().getData().getPathSegments() != null) {
            if (getIntent().getData().getPathSegments().size() > 0) {
                Intent intent = new Intent(this, DetailsView.class);
                intent.putExtra(KeyUtils.KEY_INTENT_LOCATION, getIntent().getData().getPathSegments().get(0));
                startActivity(intent);
                finish();
                return;
            }
        }
        // check android 6.0
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            insertDummyContactWrapper();
        } else {
            handler.postDelayed(waitTask, 1000);
        }
        generateHashkey();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            gpsTracker = new GPSTracker(this);
            if (gpsTracker.canGetLocation()) {
                Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            } else SystemUtils.exitApplication(SplashActivity.this);
        }
    }

    private final Runnable waitTask = new Runnable() {
        public void run() {
            if (Utilities.isConnectingToInternet(getBaseContext())) {
                if (gpsTracker.canGetLocation()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    DialogUtils.showSettingLocationDialog(SplashActivity.this, 101);
                }
            } else {
                Toast.makeText(getBaseContext(), "Please check your network connection!.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
    };

    /* todo : begind check pemission */

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    private void insertDummyContactWrapper() {

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();

        //ACCESS_FINE_LOCATION
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionsNeeded.add("Read CACCESS FINE LOCATION");
        }
        /*
        //READ_EXTERNAL_STORAGE
        if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("Read Contacts storage");
        }
        //CAMERA
        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA)) {
            permissionsNeeded.add("Read camera");
        }
        //WRITE_EXTERNAL_STORAGE
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("Write Contacts storage");
        }
        //ACCESS_COARSE_LOCATION
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissionsNeeded.add("Read ACCESS COARSE LOCATION");
        }

        //READ_PHONE_STATE
        if (!addPermission(permissionsList, android.Manifest.permission.READ_PHONE_STATE)) {
            permissionsNeeded.add("Read READ PHONE STATE");
        }
        //READ_CONTACTS
        if (!addPermission(permissionsList, android.Manifest.permission.READ_CONTACTS)) {
            permissionsNeeded.add("Read READ CONTACTS");
        }
        //GET_ACCOUNTS
        if (!addPermission(permissionsList, android.Manifest.permission.GET_ACCOUNTS)) {
            permissionsNeeded.add("Read GET ACCOUNTS");
        }
        //CALL_PHONE
        if (!addPermission(permissionsList, android.Manifest.permission.CALL_PHONE)) {
            permissionsNeeded.add("Read CALL PHONE");
        }
        //WRITE_SETTINGS
        if (!addPermission(permissionsList, WRITE_SETTINGS)) {
            permissionsNeeded.add("Read WRITE SETTINGS");
        }
        */

        if (permissionsList.size() > 0) {

            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                if (Build.VERSION.SDK_INT >= 23) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }
                return;
            }
            if (Build.VERSION.SDK_INT >= 23) {

                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }

            return;
        }

        insertDummyContact();
    }

    private void insertDummyContact() {
        handler.postDelayed(waitTask, 1000);
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    /* todo : end check pemision */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
               /* perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_SETTINGS, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);*/
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        /*&& perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED
                        */
                        ) {
                    // All Permissions Granted
                    insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(SplashActivity.this, getResources().getString(R.string.capquyen), Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    public void showSettingsAlert() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_open_location);
        dialog.setTitle("");
        dialog.setCanceledOnTouchOutside(false);
        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 101);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SystemUtils.exitApplication(SplashActivity.this);
            }
        });
        dialog.show();
    }

    public void generateHashkey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("package_name:", "" + info.packageName);
                Log.e("hash_key:", "" + Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                printHashKey();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("capt", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("capt", e.getMessage(), e);
        }
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("printHashKey:", "" + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            // Log.e("printHashKey()", e);
        } catch (Exception e) {
            //Log.e( "printHashKey()", e);
        }
    }
/* todo : end ======================================== end */
}
