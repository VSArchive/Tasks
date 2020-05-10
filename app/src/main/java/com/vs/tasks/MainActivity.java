package com.vs.tasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.vs.tasks.Room.RoomEntity;
import com.vs.tasks.Room.RoomViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RoomViewModel noteViewModel;
    private ImageView imageView;
    private TextView points;
    private ProgressBar progressBar;
    private SharedPreferences score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        // Toolbar menu inflater
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteAll) {
                    noteViewModel.deleteAllNotes();
                    return true;
                } else if (item.getItemId() == R.id.settings) {
                    Intent intent = new Intent(MainActivity.this, Settings.class);
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Open activity to add notes
        FloatingActionButton buttonAddNote = findViewById(R.id.Add);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNotes.class);
                startActivity(intent);
            }
        });

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NotesAdapter adapter = new NotesAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnCheckedChangeListener(new NotesAdapter.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChange() {
                updatePoints();
            }
        });

        // Set tasks in adapter
        noteViewModel.getAllNotes().observe(this, new Observer<List<RoomEntity>>() {
            @Override
            public void onChanged(@Nullable List<RoomEntity> notes) {
                adapter.setNotes(notes);
            }
        });

        // Action bar points management
        View ac_points = findViewById(R.id.ac_points);
        imageView = ac_points.findViewById(R.id.imageView);
        progressBar = ac_points.findViewById(R.id.progressBar);
        points = ac_points.findViewById(R.id.points);
        score = getSharedPreferences("points", MODE_PRIVATE);
        updatePoints();

        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void updatePoints() {
        int point = score.getInt("points", 0);
        String board = "Points : " + point;
        points.setText(board);
        double level = checkLevel();
        double points = score.getInt("points", 0);
        double progress = points / (50 * level) * 100;
        progressBar.setProgress((int) progress);
        setLevelPicture();
    }

    private void setLevelPicture() {
        switch (checkLevel()) {
            case 1:
                Picasso.get().load(R.drawable.l1).into(imageView);
                break;
            case 2:
                Picasso.get().load(R.drawable.l2).into(imageView);
                break;
            case 3:
                Picasso.get().load(R.drawable.l3).into(imageView);
                break;
            case 4:
                Picasso.get().load(R.drawable.l4).into(imageView);
                break;
            case 5:
                Picasso.get().load(R.drawable.l5).into(imageView);
                break;
            case 6:
                Picasso.get().load(R.drawable.l6).into(imageView);
                break;
            case 7:
                Picasso.get().load(R.drawable.l7).into(imageView);
                break;
            case 8:
                Picasso.get().load(R.drawable.l8).into(imageView);
                break;
            case 9:
                Picasso.get().load(R.drawable.l9).into(imageView);
                break;
            case 10:
                Picasso.get().load(R.drawable.l10).into(imageView);
                break;
            case 11:
                Picasso.get().load(R.drawable.l11).into(imageView);
                break;
            case 12:
                Picasso.get().load(R.drawable.l12).into(imageView);
                break;
            case 13:
                Picasso.get().load(R.drawable.l13).into(imageView);
                break;
        }
    }

    private int checkLevel() {
        int x = score.getInt("points", 0);
        if (x < 50) {
            return 1;
        } else if (x < 100) {
            return 2;
        } else if (x < 150) {
            return 3;
        } else if (x < 200) {
            return 4;
        } else if (x < 250) {
            return 5;
        } else if (x < 300) {
            return 6;
        } else if (x < 350) {
            return 7;
        } else if (x < 400) {
            return 8;
        } else if (x < 450) {
            return 9;
        } else if (x < 500) {
            return 10;
        } else if (x < 550) {
            return 11;
        } else if (x < 600) {
            return 12;
        } else if (x < 650) {
            return 13;
        } else {
            return 13;
        }
    }
}