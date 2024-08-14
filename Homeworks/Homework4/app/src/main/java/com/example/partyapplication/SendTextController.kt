package com.example.partyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SendTextController : AppCompatActivity() {
    private lateinit var phoneNo : String
    private lateinit var message : String
    private lateinit var phoneEditText : EditText
    private lateinit var msgEditText : EditText
    private lateinit var sendMsgButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms)
        phoneEditText = findViewById(R.id.phone_edit_text)
        msgEditText = findViewById(R.id.msg_edit_text)
        sendMsgButton = findViewById(R.id.send_msg_button)

        sendMsgButton.setOnClickListener() {
            sendSMS(sendMsgButton)
        }
    }

    private fun sendSMS(v : View) {
        phoneNo = phoneEditText.text.toString()
        message = msgEditText.text.toString()
        val dataIntent = Intent()
        dataIntent.putExtra(EXTRA_MSG, message)
        dataIntent.putExtra(EXTRA_SENDTO, phoneNo)
        setResult(RESULT_OK, dataIntent)
        finish()
    }
}