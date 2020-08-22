package com.example.network.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.network.MainActivity
import com.example.network.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity: AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register.setOnClickListener {
            if (username.text.isNotEmpty()&&password.text.isNotEmpty()){
                loading.visibility = View.VISIBLE
                mAuth.createUserWithEmailAndPassword(username.text.toString(),password.text.toString())
                    .addOnCompleteListener { task ->
                        if ( task.isSuccessful){
                            loading.visibility = View.GONE
                            val user = mAuth.currentUser
                            updateUI(user)
                        }else{
                            Toast.makeText(this,"Authentication is failed", Toast.LENGTH_LONG).show()
                            loading.visibility = View.GONE
                        }
                    }
            }

        }

    }
    private fun updateUI(user: FirebaseUser?){
        if (user!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}