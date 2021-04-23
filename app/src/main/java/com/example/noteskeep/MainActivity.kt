package com.example.noteskeep

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageButton>(R.id.createNoteIM).setOnClickListener{
            createNote()
        }
        initRecyclerView()

    }


    private fun createNote(){
        val intent= Intent(this,CreateNote::class.java)
        startActivityForResult(intent,1)
    }

    private fun initRecyclerView(){
        val notesRV=findViewById<RecyclerView>(R.id.notesRv)
        val noNotes=findViewById<TextView>(R.id.noNotesTV)

        if(getItemsList().size>0){
            notesRV.visibility= View.VISIBLE
            noNotes.visibility=View.GONE

            notesRV.layoutManager = LinearLayoutManager(this)
            val noteAdapter=NoteAdapter(this,getItemsList())
            notesRV.adapter=noteAdapter
        }else{
            notesRV.visibility= View.GONE
            noNotes.visibility=View.VISIBLE
        }
    }

    private fun getItemsList():ArrayList<NoteItem> {
        val databaseHandler:DatabaseHandler= DatabaseHandler(this)
        val noteList:ArrayList<NoteItem> = databaseHandler.viewNotes()

        return noteList
    }

    fun deleteNote(note:NoteItem){
        val builder= AlertDialog.Builder(this)

        builder.setTitle("Delete record")

        builder.setMessage("Are you sure you want to delete this?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){ _, which ->
            val databaseHandler:DatabaseHandler= DatabaseHandler(this)
            databaseHandler.deleteNote(note)
            Toast.makeText(this, "the note has been deleted",Toast.LENGTH_SHORT).show()
            initRecyclerView()
        }

        builder.setNegativeButton("No"){_,which->
            Toast.makeText(this, "the note has not been deleted",Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }

    fun updateNote(note:NoteItem){
        val intent=Intent(this,CreateNote::class.java)
        intent.putExtra("id",note.id)
        intent.putExtra("title",note.title)
        intent.putExtra("content",note.content)
        startActivityForResult(intent,1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            initRecyclerView()
        }
    }


}