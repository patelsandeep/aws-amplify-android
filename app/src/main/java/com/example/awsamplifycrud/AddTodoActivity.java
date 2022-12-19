package com.example.awsamplifycrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;

public class AddTodoActivity extends AppCompatActivity {

    private EditText nameEditText,descriptionEditText;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        setupViews();
        setupClicks();

    }
    private void setupClicks() {

        // add button click listener

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  method to creates a Todo item with two properties: a name and a description
                Todo todo = Todo.builder()
                        .name(nameEditText.getText().toString())
                        .description(descriptionEditText.getText().toString())
                        .build();

                // Create data for API
                Amplify.API.mutate(ModelMutation.create(todo),
                        response -> {
                            // Success of create data
                            Intent intent = new Intent(AddTodoActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        },
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
            }
        });
    }

    private void setupViews() {
        nameEditText = findViewById(R.id.editTextName);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addBtn = findViewById(R.id.addButton);
    }

}