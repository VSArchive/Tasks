package com.vs.tasks;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vs.tasks.Room.RoomEntity;
import com.vs.tasks.Room.RoomViewModel;
import com.vs.tasks.fragments.DatePickerFragment;
import com.vs.tasks.fragments.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNotes extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private RoomViewModel viewModel;
    private String currentDate;
    private String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        editTextTitle = findViewById(R.id.first_name);
        editTextDescription = findViewById(R.id.last_name);
        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        Button date = findViewById(R.id.button);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please add a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        RoomEntity entity;
        if (currentTime != null) {
            entity = new RoomEntity(title, description, currentDate, currentTime, "pending");

        } else {
            Calendar calc = Calendar.getInstance();
            int hour = calc.get(Calendar.HOUR_OF_DAY);
            int minute = calc.get(Calendar.MINUTE);
            if (String.valueOf(minute).length() == 1) {
                currentTime = hour + ":0" + minute;
            } else {
                currentTime = hour + ":" + minute;
            }
            Date calendar = Calendar.getInstance().getTime();
            currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar);
            entity = new RoomEntity(title, description, currentDate, currentTime, "pending");
        }
        viewModel.insert(entity);
        finish();
    }

    // Date picker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        Toast.makeText(this, dayOfMonth + "" + month + "" + year, Toast.LENGTH_SHORT).show();

        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    // Time picker
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (String.valueOf(minute).length() == 1) {
            currentTime = hourOfDay + ":0" + minute;
        } else {
            currentTime = hourOfDay + ":" + minute;
        }
    }
}
