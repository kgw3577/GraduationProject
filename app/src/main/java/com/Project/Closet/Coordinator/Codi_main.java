package com.Project.Closet.Coordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import com.Project.Closet.R;


import java.io.File;
import java.io.IOException;

public class Codi_main extends AppCompatActivity implements View.OnClickListener {

    public static final int ACTION_REQUEST_EDITIMAGE = 9;
    private View editImage;//

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codi_main);
        initView();
    }

    private void initView() {
        editImage = findViewById(R.id.edit_image);
        editImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_image:
                try {
                    editImageClick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }//end switch
    }

    private void editImageClick() throws IOException {

        //File outputFile = FileUtil.genEditFile(); //없어도 됨.
        //EditImageActivity.start(this, outputFile.getAbsolutePath(), ACTION_REQUEST_EDITIMAGE);
    }

}