package com.gabe_alex.notespace;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabe_alex.notespace.database.DbAdapter;
import com.gabe_alex.notespace.database.DbConstants;

import java.text.SimpleDateFormat;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    protected Context context;
    protected DbAdapter dbAdapter;

    public NoteAdapter(Context context) {
        this.context = context;
        this.dbAdapter = new DbAdapter(context);;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, MMM dd");

        Note note = dbAdapter.getNoteFromPosition(position, false);
        holder.titleVTextIew.setText(!note.getTitle().isEmpty() ? note.getTitle() : "(no title)");
        holder.dateTextIew.setText(dateFormat.format(note.getDate()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(context,NoteActivity.class);
                intent.putExtra(DbConstants.NOTE_ID, dbAdapter.getNoteIdFromPosition(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbAdapter.getNotesCount();
    }
}