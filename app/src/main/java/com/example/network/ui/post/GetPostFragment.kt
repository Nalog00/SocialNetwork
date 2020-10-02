package com.example.network.ui.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.network.R
import com.example.network.data.Post
import com.example.network.ui.comment.CommentActivity
//import com.example.network.ui.comment.adding.AddCommentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_get_post.*

class GetPostFragment: Fragment(R.layout.fragment_get_post) {
    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val adapter = PostAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPost.adapter = adapter
        getAllPost()
        adapter.setOnItemClickListener {
            val intent = Intent(requireContext(), CommentActivity::class.java)
            intent.putExtra("postId", it.id)
            startActivity(intent)
        }
//        adapter.setOnCommentClickListener {
//            val intent = Intent(requireContext(), AddCommentActivity::class.java)
//            intent.putExtra("PostId", it.id)
//            startActivity(intent)
//        }
        rvPost.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun getAllPost() {
        val result: MutableList<Post> = mutableListOf()
        db.collection("users").document(mAuth.currentUser?.uid.toString()).get()
            .addOnSuccessListener {
                val username = it.get("username").toString()
                db.collection("posts").addSnapshotListener { value, error ->
                    if (error != null) {
                        Toast.makeText(
                            requireContext(),
                            error.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        return@addSnapshotListener
                    }
                }
                    result.clear()
                    db.collection("posts").get().addOnSuccessListener {
                        it.documents.forEach { doc ->
                            val model = doc.toObject(Post::class.java)
                            model?.username = username
                            model?.id = doc.id
                            model?.let {
                                result.add(model)
                            }
                        }
                        adapter.models = result
                        Log.d("magluwmat", result.toString())
                    }
                }
            }

}