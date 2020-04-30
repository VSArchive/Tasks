package com.vs.tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.vs.tasks.Room.RoomEntity;
import com.vs.tasks.Room.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {
    private static OnCheckedChangeListener listener;
    private List<RoomEntity> notes = new ArrayList<>();
    private Context context;
    private int score;

    NotesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NotesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, final int position) {
        final RoomEntity currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTask());
        if (currentNote.getStatus().equals("done")) {
            holder.status.setChecked(true);
            holder.itemView.setBackgroundColor(Color.parseColor("#FFDCDCDC"));
//            if (hideDone){
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//            }
        } else if (currentNote.getStatus().equals("pending")) {
            holder.status.setChecked(false);
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.status.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    void setNotes(List<RoomEntity> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        NotesAdapter.listener = listener;
    }

    RoomEntity getNoteAt(int position) {
        return notes.get(position);
    }

    public interface OnCheckedChangeListener {
        void OnCheckedChange();
    }

    class NotesHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private CheckBox status;

        private NotesHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.task);
            status = itemView.findViewById(R.id.taskState);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final RoomEntity currentNote = notes.get(getAdapterPosition());
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                            .setTitle("Task : " + currentNote.getTask())
                            .setMessage("Description : " + currentNote.getDescription() +
                                    "\nStatus : " + currentNote.getStatus() +
                                    "\nDate : " + currentNote.getDate() +
                                    "\nTime : " + currentNote.getTime())
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    View view = LayoutInflater.from(context).inflate(R.layout.edit_alert_dialog, null);
                                    final EditText title = view.findViewById(R.id.edit_title);
                                    final EditText des = view.findViewById(R.id.edit_des);
                                    title.setText(currentNote.getTask());
                                    des.setText(currentNote.getDescription());
                                    final AlertDialog.Builder editDialog = new AlertDialog.Builder(context)
                                            .setTitle("Edit")
                                            .setView(view)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    RoomViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(RoomViewModel.class);
                                                    RoomEntity entity = new RoomEntity(title.getText().toString(), des.getText().toString(), currentNote.getDate(), currentNote.getTime(), currentNote.getStatus());
                                                    entity.setId(currentNote.getId());
                                                    viewModel.update(entity);
                                                    dialog.dismiss();
                                                }
                                            });
                                    editDialog.show();
                                    dialog.dismiss();
                                }
                            });
                    dialog.show();
                }
            });
            status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SharedPreferences points = context.getSharedPreferences("points", Context.MODE_PRIVATE);
                    score = points.getInt("points", 0);
                    RoomEntity note = notes.get(getAdapterPosition());
                    String title = note.getTask();
                    String des = note.getDescription();
                    if (isChecked) {
                        if (note.getStatus().equals("pending")) {
                            RoomViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(RoomViewModel.class);
                            RoomEntity entity = new RoomEntity(title, des, note.getDate(), note.getTime(), "done");
                            entity.setId(note.getId());
                            viewModel.update(entity);
                            score++;
                            SharedPreferences.Editor editor = points.edit();
                            editor.putInt("points", score);
                            editor.apply();
                            listener.OnCheckedChange();
                        }
                    } else {
                        if (note.getStatus().equals("done")) {
                            RoomViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(RoomViewModel.class);
                            RoomEntity entity = new RoomEntity(title, des, note.getDate(), note.getTime(), "pending");
                            score--;
                            entity.setId(note.getId());
                            viewModel.update(entity);
                            SharedPreferences.Editor editor = points.edit();
                            editor.putInt("points", score);
                            editor.apply();
                            listener.OnCheckedChange();
                        }
                    }
                }
            });
        }
    }
}
