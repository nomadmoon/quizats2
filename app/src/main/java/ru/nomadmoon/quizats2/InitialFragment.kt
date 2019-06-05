package ru.nomadmoon.quizats2


import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nomadmoon.quizats2.`object`.MainObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class InitialFragment : Fragment() {

    lateinit var initialTV: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var inflated = inflater.inflate(R.layout.fragment_initial, container, false)
        initialTV = inflated.findViewById<TextView>(R.id.initialTV)

        var sep = System.getProperty("line.separator")

        var initialText: String = "Текущий тест:"
        initialText+=sep+MainObject.currentQuizMeta.name+sep+sep+MainObject.currentQuizMeta.description+sep

        initialText+=sep+sep+"Статистика неправильных ответов по данному тесту:"
        initialText+=sep+"Не собрана"+sep+sep

        initialText+="Показывать карточек:"+sep
        var qnum = (activity as MainActivity).settings.getInt("questions_number", 0)
        if (qnum==0) {
            initialText+="не выбрано"+sep
        }
        else {
            initialText+=qnum.toString()+sep
        }

        var intel = (activity as MainActivity).settings.getInt("statistics_enabled", -1)
        initialText+=sep+"Неправильный ответ:"+sep
        if (intel==1) {
            initialText+="Повышает вероятность показа этой карточки в дальнейшем"
        }
        else {
            initialText+="Не влияет на вероятность показа этой карточки в дальнейшем"
        }


        initialTV.text = initialText
        return inflated
    }


}
