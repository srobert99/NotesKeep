package com.example.noteskeep

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "NotesDatabase"
        private val TABLE_NOTES = "NotesTable"

        private val KEY_ID = "_id"
        private val KEY_TITLE = "title"
        private val KEY_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES)
        onCreate(db)
    }


    /**
     * Function to insert data
     */
    fun addNote(note: NoteItem): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, note.title)
        contentValues.put(KEY_CONTENT, note.content)

        val success = db.insert(TABLE_NOTES, null, contentValues)

        return success
    }

    /**
     * Function to insert data
     */
    //method to read data
    fun viewNotes(): ArrayList<NoteItem> {
        val noteList:ArrayList<NoteItem> = ArrayList<NoteItem>()

        val selectQuery = "SELECT * FROM $TABLE_NOTES"

        val db=this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)

        }catch(e:SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var title: String
        var content: String

        if(cursor.moveToFirst()) {
            do {
                id=cursor.getInt(cursor.getColumnIndex(KEY_ID))
                title=cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                content=cursor.getString(cursor.getColumnIndex(KEY_CONTENT))

                val note = NoteItem(id=id, title=title, content=content)
                noteList.add(note)

            }while (cursor.moveToNext())
        }
        return noteList
    }

    /**
     * Function to update record
     */
    fun updateNote(note:NoteItem): Int {
        val db=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE,note.title)
        contentValues.put(KEY_CONTENT,note.content)

        val succes=db.update(TABLE_NOTES, contentValues, KEY_ID + "=" + note.id, null)

        db.close()

        return succes
    }

    fun deleteNote(note: NoteItem): Int {
        val db=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, note.id)

        val succes=db.delete(TABLE_NOTES, KEY_ID + "=" +note.id,null)

        db.close()
        return succes
    }
}