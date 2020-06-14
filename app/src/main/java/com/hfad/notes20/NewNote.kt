package com.hfad.notes20

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import java.util.*


class NewNote : AppCompatActivity(), ColorPickerDialogListener{

    lateinit var button: ImageButton

    lateinit var title: EditText
    lateinit var note: EditText

    lateinit var switch: Switch
    lateinit var date: DatePicker

   lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        button = findViewById(R.id.colorButton)
        title = findViewById(R.id.editTextTitle)
        note = findViewById(R.id.editTextDescription)
        switch = findViewById(R.id.switchWidget)
        date = findViewById(R.id.date)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                date.visibility = View.VISIBLE
            }
            else {
                date.visibility = View.INVISIBLE
            }
        }

        val today = Calendar.getInstance()
        date.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
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
    fun onClickChooseColor(view: View) {
        createColorPickerDialog(1)
    }
    override fun onDialogDismissed(dialogId: Int) {

    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        button.setBackgroundColor(color)
    }

    fun saveNode(view: View) {
        if(title.text.isEmpty() || note.text.isEmpty())
            Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT).show()
        else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            val color = ((button.background as ColorDrawable)).color
            val title = title.text.toString()
            val description = note.text.toString()
            val date = String.format("%s.%s.%s",date.dayOfMonth, date.month+1, date.year)

            val note = Note(title, description, date, color)
            noteViewModel.insert(note)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}

