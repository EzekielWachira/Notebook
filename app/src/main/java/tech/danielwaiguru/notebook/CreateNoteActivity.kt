package tech.danielwaiguru.notebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {
    companion object{
        const val NOTE_TITLE = "NOTE_TITLE"
        const val NOTE_TEXT = "NOTE_TEXT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        imageViewCancel.setOnClickListener {
            finish()
        }
        imageViewSave.setOnClickListener {
            saveNote()
        }
        getIncomingIntent()
    }
    private fun saveNote(){
        val replyIntent = Intent()
        if (etNoteText.text.isNotEmpty() && etNoteTitle.text.isNotEmpty()){
            val title = etNoteTitle.text.toString()
            val textNote = etNoteText.text.toString()
            replyIntent.putExtra(NOTE_TITLE, title)
            replyIntent.putExtra(NOTE_TEXT, textNote)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        else{
            Toast.makeText(applicationContext, "Cannot save empty note", Toast.LENGTH_LONG).show()
        }
        finish()
    }
    private fun getIncomingIntent(){
        if (intent.hasExtra("TITLE_EXTRA") && intent.hasExtra("TEXT_EXTRA")){
            etNoteTitle.setText(intent.getStringExtra("TITLE_EXTRA"))
            etNoteText.setText(intent.getStringExtra("TEXT_EXTRA"))
        }
    }
}