package com.Project.Closet.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.Project.Closet.social.activity_addBoard;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Utils {




    public static CropImage.ActivityBuilder CropImageSetting(){

        return CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMaxCropResultSize(40000,99999)
                .setMinCropResultSize(200,40)
                .setMinCropWindowSize(200,42)
                .setOutputCompressQuality(60)
                .setMaxZoom(3);
    }
}

