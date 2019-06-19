package ru.nomadmoon.quizats2

import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.result_card_view.view.*
import ru.nomadmoon.quizats2.`object`.MainObject

class ResultRecyclerViewAdapter():  RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder>() {



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val q_num = MainObject.arrayOfAnswers[position].quest_number
        holder.rightAnsw.text=MainObject.arrayOfQuestions[q_num].answers[MainObject.arrayOfAnswers[position].right_answer]
        holder.userAnsw.text=MainObject.arrayOfQuestions[q_num].answers[MainObject.arrayOfAnswers[position].answer]

        holder.rightAnsw.text="Правильный ответ:\n"+holder.rightAnsw.text.removePrefix("QQQ")
        holder.userAnsw.text="Ваш ответ:\n"+holder.userAnsw.text.removePrefix("QQQ")

        if ((MainObject.arrayOfQuestions[q_num].question)=="") {
            holder.resQuestion.visibility=View.GONE
        }
        else
        {
            holder.resQuestion.visibility=View.VISIBLE
        }

        holder.resImg.setImageBitmap(BitmapFactory.decodeFile( holder.resImg.context.filesDir.toString()+"/quizes/"+MainObject.currentQuizDir+"/"+(q_num+1)+".jpg"))
        holder.resQuestion.text=MainObject.arrayOfQuestions[q_num].question

        holder.rightAnsw.setBackgroundColor(Color.parseColor("#00AA00"))

        if (MainObject.arrayOfAnswers[position].right_answer == MainObject.arrayOfAnswers[position].answer)
        {
            holder.userAnsw.setBackgroundColor(Color.parseColor("#00AA00"))
        }
        else
        {
            holder.userAnsw.setBackgroundColor(Color.parseColor("#AA0000"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.result_card_view, parent, false)
        return ViewHolder(view)
    }



    override fun getItemCount(): Int = MainObject.arrayOfAnswers.count()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val rightAnsw = mView.ResultTextViewRightAnswer
        val userAnsw = mView.ResultTextViewAnswer
        val resImg = mView.resultImageView
        val resQuestion = mView.ResultTextViewQuestion
    }
}