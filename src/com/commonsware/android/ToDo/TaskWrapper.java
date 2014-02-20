package com.commonsware.android.ToDo;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import java.util.Date;

public class TaskWrapper extends AdapterWrapper {
    Context ctxt=null;
    Date[] dates=null;
    
    public TaskWrapper(Context ctxt, ListAdapter delegate) {
        super(delegate);
        
        this.ctxt=ctxt;
        this.dates=new Date[delegate.getCount()];
        
        for (int i=0;i<delegate.getCount();i++) {
            this.dates[i]=new Date();
        }
    }
    
    public View getView(int position, View convertView, 
          ViewGroup parent) {
        TextView row=(TextView)convertView;
        
        if (convertView==null) {
            row=new TextView(ctxt);
        }
        
        return(row);
    }
}
