package com.example.network.ui.comment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.network.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.item_comment.*

class CommentActivity : AppCompatActivity() {

    private val adapter = CommentListAdapter()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        var id = ""
        id = intent.getStringExtra("postId") ?: ""
        rvComment.adapter = adapter
        setComments(id)
        timer.start()

    }
    private val timer = object : CountDownTimer(1000 , 1000){

        override fun onTick(p0: Long) {
        }

        override fun onFinish() {
            if (adapter.models.isNotEmpty()) {
                tvComment?.visibility = View.VISIBLE
                tvCommentType.visibility = View.GONE
            } else {
                tvComment?.visibility = View.GONE
                tvCommentType.visibility = View.VISIBLE
            }
        }
    }
    private fun setComments(id: String) {
            db.collection("posts").document(id).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.get("comment")?.let { comments ->
                            adapter.models = comments as List<Map<String,String>>
                        }
                    }
                }


        }
    }