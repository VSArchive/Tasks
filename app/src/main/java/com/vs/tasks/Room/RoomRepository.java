package com.vs.tasks.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class RoomRepository {
    private RoomDao noteDao;
    private LiveData<List<RoomEntity>> allNotes;

    RoomRepository(Application application) {
        mRoomDatabase database = mRoomDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getNotes();
    }

    void insert(RoomEntity name) {
        new InsertNoteAsyncTask(noteDao).execute(name);
    }

    void update(RoomEntity name) {
        new UpdateNoteAsyncTask(noteDao).execute(name);
    }

    void delete(RoomEntity name) {
        new DeleteNoteAsyncTask(noteDao).execute(name);
    }

    void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    LiveData<List<RoomEntity>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<RoomEntity, Void, Void> {
        private RoomDao notesDao;

        private InsertNoteAsyncTask(RoomDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(RoomEntity... names) {
            notesDao.insert(names[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<RoomEntity, Void, Void> {
        private RoomDao notesDao;

        private UpdateNoteAsyncTask(RoomDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(RoomEntity... names) {
            notesDao.update(names[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<RoomEntity, Void, Void> {
        private RoomDao notesDao;

        private DeleteNoteAsyncTask(RoomDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(RoomEntity... names) {
            notesDao.delete(names[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private RoomDao noteDao;

        private DeleteAllNotesAsyncTask(RoomDao nameDao) {
            this.noteDao = nameDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }
}
