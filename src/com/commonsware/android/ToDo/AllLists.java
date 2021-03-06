package com.commonsware.android.ToDo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AllLists extends ListActivity {
    TextView title;
    TextView selected;
    int selected_num;
    private SharedPreferences prefs;
    String[] lists;
    Date today=new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    public static final int ADD_LIST=0;
    public static final int REMOVE_LIST=1;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        prefs=getSharedPreferences("lists", 0);
        int nlists=prefs.getInt("num_lists", 1);
        lists=new String[nlists];
        for (int i=0;i<lists.length;i++) {
            lists[i]=prefs.getString("list"+Integer.toString(i),
                                     "Empty list");
        };
        
        setContentView(R.layout.mainlists);
        TextView title=(TextView)findViewById(R.id.list_title);
        title.setText("To-do lists");

        setListAdapter(new ArrayAdapter<String>(this, 
              android.R.layout.simple_list_item_1, lists));
        
        registerForContextMenu(getListView());
    }
    
    protected void onPause() {
        super.onPause();
        
        prefs=getSharedPreferences("lists", 0);
        SharedPreferences.Editor ed=prefs.edit();
        ed.putInt("num_lists", lists.length);
        for (int i=0;i<lists.length;i++) {
            ed.putString("list"+Integer.toString(i), lists[i]);
        }
        ed.commit();
    }
    
    public void onListItemClick(ListView parent, View v, 
          int position, long id) {
        //selected_num=position;
        Intent intent=new Intent(this, ToDo.class);
        intent.putExtra("listname", lists[position]);
        prefs=getSharedPreferences("lists", 0);
        SharedPreferences.Editor ed=prefs.edit();
        ed.putString("currentlist", lists[position]);
        ed.commit();
        startActivityForResult(intent, 0);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, 
          Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                lists[selected_num]=data.getExtras().getString("task");
                setListAdapter(new ArrayAdapter<String>(this, 
                      android.R.layout.simple_list_item_1, lists));
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        populateMenu(menu);
        
        return(super.onCreateOptionsMenu(menu));
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
          ContextMenu.ContextMenuInfo menuInfo) {
        populateContextMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return(applyMenuChoice(item) || 
              super.onOptionsItemSelected(item));
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return(applyMenuChoice(item) || 
              super.onOptionsItemSelected(item));
    }
    
    private void populateMenu(Menu menu) {
        menu.add(Menu.NONE, ADD_LIST, Menu.NONE, "Add list");
    }
    
    private void populateContextMenu(Menu menu) {
        menu.add(Menu.NONE, REMOVE_LIST, Menu.NONE, "Remove list");
    }
    
    private boolean applyMenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case ADD_LIST:
                String[] temp=new String[lists.length+1];
                //String[] datestrings=new String[dates.length+1];
                //Date[] temp2=new Date[dates.length+1];
                for (int i=0;i<lists.length;i++) {
                    temp[i]=lists[i];
                    //temp2[i]=dates[i];
                };
                lists=temp;
                //dates=temp2;
                selected_num=lists.length-1;
                lists[selected_num]="New list";
                //dates[selected_num]=new Date();
                
                Intent intent=new Intent(this, DefineTask.class);
                intent.putExtra("task", lists[selected_num]);
                intent.putExtra("date", sdf.format(today));
                                //sdf.format(dates[selected_num]));
                startActivityForResult(intent, 1);
                
                return(true);
            case REMOVE_LIST:
                AdapterView.AdapterContextMenuInfo info=
                      (AdapterView.AdapterContextMenuInfo)
                      item.getMenuInfo();
                selected_num=info.position;
                String[] templists=new String[lists.length-1];
                //Date[] tempdates1=new Date[dates.length-1];
                int j=0;
                for (int i=0; i<lists.length; i++) {
                    if (i != selected_num) {
                        templists[j]=lists[i];
                        //tempdates1[j]=dates[i];
                        j++;
                    };
                };
                lists=templists;
                //dates=tempdates1;
                setListAdapter(new ArrayAdapter<String>(this, 
                      android.R.layout.simple_list_item_1, lists));
                
                return(true);
        }
        return(false);
    }
}
