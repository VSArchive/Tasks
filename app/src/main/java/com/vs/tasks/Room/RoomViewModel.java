package com.vs.tasks.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private RoomRepository repository;
    private LiveData<List<RoomEntity>> allNotes;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        repository = new RoomRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(RoomEntity note) {
        repository.insert(note);
    }

    public void update(RoomEntity note) {
        repository.update(note);
    }

    public void delete(RoomEntity note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<RoomEntity>> getAllNotes() {
        return allNotes;
    }
}