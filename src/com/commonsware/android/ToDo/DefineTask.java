package com.commonsware.android.ToDo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefineTask extends Activity {
    String taskname;
    Button btn;
    EditText task;
    DatePicker date;
    String oldtask, newtask;
    Date olddate, newdate;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.task);
        btn=(Button)findViewById(R.id.done);
        task=(EditText)findViewById(R.id.taskname);
        date=(DatePicker)findViewById(R.id.datepicker);
        oldtask=getIntent().getExtras().getString("task");
        olddate=sdf.parse(getIntent().getExtras().getString("date"),
                          new ParsePosition(0));
        task.setText(oldtask);
        date.updateDate(1900+olddate.getYear(), olddate.getMonth(),
              olddate.getDate());
        
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newtask=task.getText().toString();
                Intent returnStuff=new Intent();
                newdate=new Date(date.getYear()-1900,
                                 date.getMonth(),
                                 date.getDayOfMonth());
                returnStuff.putExtra("task", newtask);
                returnStuff.putExtra("date", sdf.format(newdate));
                setResult(RESULT_OK, returnStuff);
                finish();
            }
        });
    }
}
