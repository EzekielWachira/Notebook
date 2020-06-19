package tech.danielwaiguru.notebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        imageViewCancel.setOnClickListener {
            finish()
        }
    }
    fun saveNote(){
        val replyIntent = Intent()
        val title = etNoteTitle.text.toString()
        val textNote = etNoteText.text.toString()
        replyIntent.putExtra("", title)
        replyIntent.putExtra("", textNote)

    }
}