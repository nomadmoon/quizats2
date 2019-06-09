package ru.nomadmoon.quizats2


import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nomadmoon.quizats2.`object`.MainObject


class ResultFragment : Fragment() {
    override fun onStart() {
        super.onStart()
        countRightAnswers()
    }



    var resultText: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val inflated: View = inflater.inflate(R.layout.fragment_result, container, false)

        countRightAnswers()

        val tv = inflated.findViewById<TextView>(R.id.resultTextView)
        val recV = inflated.findViewById<RecyclerView>(R.id.resultRecyclerView)
        recV.adapter = ResultRecyclerViewAdapter()
        recV.layoutManager=LinearLayoutManager(context)

        tv.text=resultText

        return inflated
    }


    fun countRightAnswers()
    {
        var rightAnwrsCount = 0

        //for (answr in answers)
        for (answr in MainObject.arrayOfAnswers)
        {
            if (answr.answer==answr.right_answer) rightAnwrsCount++
        }

        resultText="Количество правильных ответов: "+rightAnwrsCount+" из "+MainObject.currentQuizMeta.questions_show_count

    }

}
