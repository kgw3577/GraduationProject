package com.Project.Closet.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MySpinnerAdapter extends ArrayAdapter<String> {
    public MySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }


    //getView를 overriding 해준다
    public View getView(int position, View convertView, ViewGroup parent){
        View v = super.getView(position, convertView, parent);

        if(position == getCount()){
            ((TextView) v.findViewById(android.R.id.text1)).setText("");
            ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
        }
        return v;
    }

    //getCount를 overriding 해준다
    public int getCount(){
        return super.getCount() - 1;
    }
}
