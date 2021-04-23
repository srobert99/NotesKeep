package com.example.noteskeep

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter (val context: Context, val notes:MutableList<NoteItem>):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

            class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.note_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val curNote=notes.get(position)

        holder.itemView.findViewById<TextView>(R.id.titleTV).text=curNote.title
        holder.itemView.findViewById<TextView>(R.id.contentTV).text=curNote.content

        if(position % 2==0){
            holder.itemView.setBackgroundColor(Color.parseColor("#3F51B5"))
        }

        holder.itemView.findViewById<ImageView>(R.id.deleteIV).setOnClickListener{
            if(context is MainActivity){
                context.deleteNote(curNote)
            }
        }

        holder.itemView.findViewById<ImageView>(R.id.editIV).setOnClickListener{
            if(context is MainActivity){
                context.updateNote(curNote)
            }
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }


}