package com.example.network.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.network.R
import com.example.network.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_add_post.progressBar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.etUsername
import kotlinx.android.synthetic.main.item_post.*

class AddPostFragment: Fragment(R.layout.fragment_add_post) {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSend.setOnClickListener {
            setLoading(true)
            if (!etPost.text.isNullOrEmpty()){
                val map: MutableMap<String,Any> = mutableMapOf()
                map["userId"] = mAuth.currentUser?.uid.toString()
                map["text"] = etPost.text.toString()
                map["theme"] = etTheme.text.toString()
                map["like"] = 0
                map["dislike"] = 0
                map["comment"] = arrayListOf<String>()
                db.collection("posts").document().set(map)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(),"Your post is published",Toast.LENGTH_LONG).show()
                        setLoading(false)
                    }
                    .addOnFailureListener{e->
                        Toast.makeText(requireContext(),e.localizedMessage!!.toString(),Toast.LENGTH_LONG).show()
                        setLoading(false)
                    }
            }
        }

    }
    private fun setLoading(isLoading: Boolean){
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        etPost.isEnabled  = !isLoading
        btnSend.isEnabled = !isLoading
    }

}