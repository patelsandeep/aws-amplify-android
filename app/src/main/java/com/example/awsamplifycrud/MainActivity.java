package com.example.awsamplifycrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnAddTodo;
    public ListView lv;
    public String[] st;
    Handler handler;
    // the array adapter converts an ArrayList of objects
    // into View items filled into the ListView container
    ArrayAdapter<String> arrayAdapter;

    // For Store User Id
    private String userId = null;

    // list to store data
    public static List<String> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAddTodo = findViewById(R.id.btn_addTodo);
        st = new String[100];
        lv = findViewById(R.id.lt);

        // floating button to open add todo data
        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });
        
        ls = new ArrayList<String>();

        // method to fetch data/run queries to retrieve the stored data
        getData();

        handler = new Handler();
        final Runnable r = new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            public void run() {
                handler.postDelayed(this, 2000);
                arrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        ls);
                lv.setAdapter(arrayAdapter);
                lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                arrayAdapter.notifyDataSetChanged();
            }
        };
        handler.postDelayed(r, 1000);

        // data click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAlertDialogButtonClicked(view, ls.get(i), i);
            }
        });
    }

    private void getData() {
        // read data from amplify
        Amplify.API.query(ModelQuery.list(Todo.class), response -> {
                    for (Todo todo : response.getData()) {
                        ls.add(todo.getName());
                        userId = todo.getId();
                        Log.i("MyAmplifyApp", todo.getName());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error));
    }

    private void showAlertDialogButtonClicked(View view, String s, int itemPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Any One Operation...");

        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update " + s);
                // set the custom layout
                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.update_layout,
                                null);
                builder.setView(customLayout);

                // add a positive button
                builder
                        .setPositiveButton(
                                "Update",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        EditText editText = customLayout.findViewById(R.id.editTextAlert);
                                        Todo todo = Todo.builder()
                                                .name(editText.getText().toString())
                                                .id(userId)
                                                .build();

                                        // update the API data
                                        Amplify.API.mutate(ModelMutation.update(todo),
                                                response -> {
                                                    Amplify.API.query(ModelQuery.list(Todo.class), success -> {
                                                                for (Todo todoData : success.getData()) {
                                                                    ls.set(itemPosition, editText.getText().toString());
                                                                }
                                                            },
                                                            error -> Log.e("MyAmplifyApp", "Query failure", error));
                                                }, error -> {
                                                    Log.e("TAG", "onClick: update error " + error);
                                                });
                                    }
                                });

                // create and show
                // the alert dialog
                AlertDialog dialog
                        = builder.create();
                dialog.show();
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are You Sure Want To Delete ???");

                // set the custom layout
                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.delete_layout,
                                null);
                builder.setView(customLayout);

                // add a button
                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {

                                        // code for build the data for delete
                                        Todo todo1 = Todo.builder()
                                                .name(s)
                                                .build();

                                        //delete the API data
                                        Amplify.API.mutate(ModelMutation.delete(todo1),
                                                done -> {
                                                    Amplify.API.query(ModelQuery.list(Todo.class), success -> {
                                                                for (Todo todoData : success.getData()) {
                                                                    ls.remove(String.valueOf(todo1.getName()));
                                                                }
                                                            },
                                                            error -> Log.e("MyAmplifyApp", "Query failure", error));
                                                }, err -> {
                                                    Log.e("MyAmplifyApp", "Not Deleted");
                                                });

                                    }
                                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog dialog
                                = builder.create();
                        dialog.dismiss();
                    }
                });

                // create and show
                // the alert dialog
                AlertDialog dialog
                        = builder.create();
                dialog.show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        arrayAdapter.notifyDataSetChanged();
    }
}