package com.example.network.data

data class Post(
    var id: String="",
    var theme: String = "",
    var username: String= "",
    var text: String = "",
    var like: Int = 0,
    var dislike: Int =0,
    var userId: String = "",
    var comment: MutableList<Map<String?,String?>> = mutableListOf()
)