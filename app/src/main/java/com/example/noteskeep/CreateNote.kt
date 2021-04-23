package com.example.noteskeep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        checkintent()

        val createB=findViewById<Button>(R.id.createNoteB)
        createB.setOnClickListener {
            addNote()
        }
    }


    fun addNote(){
        val createB=findViewById<Button>(R.id.createNoteB)
        val title=findViewById<EditText>(R.id.titleET).text.toString()
        val content=findViewById<EditText>(R.id.contentET).text.toString()
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)

        if(!title.isEmpty() && !content.isEmpty()){
            if(createB.text=="CREATE") {
                val status = databaseHandler.addNote(NoteItem(0, title, content))
                if (status > -1) {
                    setResult(1)
                    finish()
                }
            }else{
                val id=intent.getIntExtra("id",-1)
                databaseHandler.updateNote(NoteItem(id,title,content))
                setResult(1)
                finish()
            }
        }else{
            Toast.makeText(this,"Title or content is empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkintent(){
        if(intent.hasExtra("title")){
            findViewById<EditText>(R.id.titleET).setText(intent.getStringExtra("title"))
            findViewById<EditText>(R.id.contentET).setText(intent.getStringExtra("content"))
            findViewById<Button>(R.id.createNoteB).text="EDIT"

        }
    }

}