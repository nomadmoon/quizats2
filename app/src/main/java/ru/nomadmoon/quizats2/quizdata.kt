package ru.nomadmoon.quizats2

data class quizdata (var img_num_id: Int, var question: String, var answers: Array<String>, var fails: Int=1)

data class quizmetadata (var name: String, var description: String, var total_questions_count: Int=-1, var questions_show_count: Int=-1, var use_statistics: Boolean=true, var immediate_show: Boolean=false, var shuffle_answers: Boolean=false)

data class quizresult (var right_answer: Int, var answer: Int, var quest_number: Int)

data class questionitem (var n: Int, var question: String)

