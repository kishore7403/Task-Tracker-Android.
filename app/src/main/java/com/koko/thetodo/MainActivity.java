package com.koko.thetodo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv_task;
    EditText et_task;
    Button btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_task=findViewById(R.id.lv_task);
        et_task=findViewById(R.id.et_task);
        btn_add=findViewById(R.id.btn_add);


        final DataBaseHelper dataBaseHelper=new DataBaseHelper(MainActivity.this);
        List<tasks> everytask=dataBaseHelper.geteverytask();
        ArrayAdapter taskArrayAdapter=new ArrayAdapter<tasks>(MainActivity.this,android.R.layout.simple_list_item_1,everytask);
        lv_task.setAdapter(taskArrayAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks taskobj;
                try {
                    if(et_task.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Enter a task", Toast.LENGTH_SHORT).show();


                    }
                    else
                    {


                        taskobj = new tasks(-1, et_task.getText().toString());
                        Toast.makeText(MainActivity.this, taskobj.toString(), Toast.LENGTH_SHORT).show();
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                        dataBaseHelper.addingtask(taskobj);
                        Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();

                        et_task.setText("");

                        List<tasks> everytask = dataBaseHelper.geteverytask();
                        ArrayAdapter taskArrayAdapter = new ArrayAdapter<tasks>(MainActivity.this, android.R.layout.simple_list_item_1, everytask);
                        lv_task.setAdapter(taskArrayAdapter);

                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Enter a task", Toast.LENGTH_SHORT).show();

                }
            }
        });



        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int i, long l) {

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                tasks clickedtask=(tasks) parent.getItemAtPosition(i);
                                dataBaseHelper.deletetask(clickedtask);

                                List<tasks> everytask=dataBaseHelper.geteverytask();
                                ArrayAdapter taskArrayAdapter=new ArrayAdapter<tasks>(MainActivity.this,android.R.layout.simple_list_item_1,everytask);
                                lv_task.setAdapter(taskArrayAdapter);
                                Toast.makeText(MainActivity.this, "Deleted:"+clickedtask.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<tasks> everytask=dataBaseHelper.geteverytask();
                                ArrayAdapter taskArrayAdapter=new ArrayAdapter<tasks>(MainActivity.this,android.R.layout.simple_list_item_1,everytask);
                                lv_task.setAdapter(taskArrayAdapter);
                            }
                        });
                AlertDialog alert=builder.create();
                alert.show();


            }
        });
    }
}
