package ru.nomadmoon.quizats2

data class quizdata (var img_num_id: Int, var answers: Array<String>)

data class quizmetadata (var name: String, var description: String)

data class quizresult (var right_answer: Int, var answer: Int)
