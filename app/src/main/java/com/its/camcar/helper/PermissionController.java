package com.its.camcar.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.master.permissionhelper.PermissionHelper;

public class PermissionController {

    private Activity activity;
    public static int PERMISSION_CAMERA_REQUEST_CODE = 100;

    public PermissionController(Activity activity) {
        this.activity = activity;
    }

    public PermissionHelper requestPermission(){
        return new PermissionHelper(
                        activity,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_CAMERA_REQUEST_CODE);
    }
}
