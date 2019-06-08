package ru.nomadmoon.quizats2

data class quizdata (var img_num_id: Int, var answers: Array<String>)

data class quizmetadata (var name: String, var description: String, var total_questions_count: Int, var questions_show_count: Int, var use_statistics: Boolean)

data class quizresult (var right_answer: Int, var answer: Int)
