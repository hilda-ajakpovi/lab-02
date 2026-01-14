package com.example.listycity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList; // refers to list view in activity_main.xml
    ArrayAdapter<String> cityAdapter; // connects string list to view
    ArrayList<String> dataList;
    int selectedPosition = -1; // position in cityList to delete from


    @Override
    protected void onCreate(Bundle savedInstanceState) { // first method called when you create a new activity
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCityDialog();
            }
        });


        cityList = findViewById(R.id.city_list); // connecting cityList view with actual view in main

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities)); // add all cities from cities list to the new list

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter); // connecting view with adapter and then adapter with list

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        Button deleteCityButton = findViewById(R.id.del_btn);
        deleteCityButton.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            selectedPosition = -1;
            cityList.clearChoices();
        });


    }

    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");

        // Input field
        final EditText input = new EditText(this);
        input.setHint("Enter city name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        // Confirm button
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            String cityName = input.getText().toString().trim();

            if (!cityName.isEmpty()) {
                dataList.add(cityName);
                cityAdapter.notifyDataSetChanged();
            }
        });

        // Cancel button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
