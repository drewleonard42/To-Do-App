package com.commonsware.android.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Date;

public class TaskWrapper extends AdapterWrapper {
    Context ctxt=null;
    Date[] dates=null;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private SharedPreferences prefs;
    
    public TaskWrapper(Context ctxt, ListAdapter delegate) {
        super(delegate);
        
        this.ctxt=ctxt;
        this.dates=new Date[delegate.getCount()];
        prefs=ctxt.getSharedPreferences("lists", 0);
        
        for (int i=0;i<delegate.getCount();i++) {
            this.dates[i]=sdf.parse(
                prefs.getString("date"+Integer.toString(i),
                                sdf.format(new Date())),
                new ParsePosition(0));
        }
    }
    
    public View getView(int position, View convertView, 
          ViewGroup parent) {
        TextView row=(TextView)convertView;
        //
        
        Date today=new Date();
        if (convertView==null) {
            row=(TextView)delegate.getView(position, null, parent); 
            if (dates[position].compareTo(today)<1) {
                row.setBackgroundColor(Color.RED);
            }
            else {
                row.setBackgroundColor(Color.GREEN);
                row.setTextColor(Color.BLACK);
            }
        }
        
        return(row);
    }
}
