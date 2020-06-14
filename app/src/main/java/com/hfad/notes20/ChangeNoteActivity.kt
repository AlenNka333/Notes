package com.hfad.notes20

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import java.util.*

class ChangeNoteActivity : AppCompatActivity(), ColorPickerDialogListener {

    lateinit var button: ImageButton

    lateinit var title: EditText
    lateinit var des: EditText

    lateinit var switch: Switch
    lateinit var date: DatePicker

    lateinit var noteLive: LiveData<Note>
    lateinit var bundle: Bundle
    lateinit var noteId: String

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_note)

        button = findViewById(R.id.colorButton2)
        title = findViewById(R.id.editTextTitle2)
        des = findViewById(R.id.editTextDescription2)
        switch = findViewById(R.id.switchWidget2)
        date = findViewById(R.id.date2)

        bundle = intent.extras!!
        if(bundle !=null){
            noteId = bundle.getInt("itemId").toString()
        }
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteLive = noteViewModel.getNote(noteId)
        noteLive.observe(this, object:
        Observer<Note>{
            override fun onChanged(t: Note?) {
                button.setBackgroundColor(t!!.priority)
                title.setText(t!!.title)
                des.setText(t!!.description)


            }

        })


        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                date.visibility = View.VISIBLE
            }
            else {
                date.visibility = View.INVISIBLE
            }
        }
        val today = Calendar.getInstance()
        date.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)
        ){ view, year, month, day ->
        }

    }


    private fun createColorPickerDialog(id: Int) {
        ColorPickerDialog.newBuilder()
            .setColor(Color.RED)
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setAllowCustom(true)
            .setAllowPresets(true)
            .setColorShape(ColorShape.CIRCLE)
            .setDialogId(id)
            .show(this)
    }
    fun onClickChooseColor2(view: View) {
        createColorPickerDialog(1)
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        button.setBackgroundColor(color)
    }

    fun updateNote(view: View) {
        if(title.text.isEmpty() || des.text.isEmpty())
            Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT).show()
        else {
            val resultIntent = Intent()
            val color = ((button.background as ColorDrawable)).color
            val title = title.text.toString()
            val description = des.text.toString()
            val date = String.format("%s.%s.%s",date.dayOfMonth, date.month+1, date.year)

            resultIntent.putExtra("noteId", noteId)
            resultIntent.putExtra("noteTitle", title)
            resultIntent.putExtra("noteDescription", description)
            resultIntent.putExtra("noteDate", date)
            resultIntent.putExtra("noteColor", color)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

}