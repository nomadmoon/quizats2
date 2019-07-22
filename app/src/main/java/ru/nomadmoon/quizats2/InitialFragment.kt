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


        /////+++++++++++++++++++++
        refreshFragment()
        ////-------------
        return inflated
    }


    override fun onStart() {
        super.onStart()
        refreshFragment()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshFragment()
    }

    fun refreshFragment()
    {
        var sep = System.getProperty("line.separator")

        var initialText: String = resources.getString(R.string.initial_current_test)   //"Текущий тест:"
        initialText+=sep+MainObject.currentQuizMeta.name+sep+sep+MainObject.currentQuizMeta.description+sep+sep+resources.getString(R.string.initial_questions_in_quiz)+sep+MainObject.currentQuizMeta.total_questions_count+sep+sep



        initialText+=resources.getString(R.string.initial_cards_to_show)+sep
        var qnum = MainObject.currentQuizMeta.questions_show_count//(activity as MainActivity).settings.getInt("questions_number", 0)
        if (qnum==0) {
            initialText+="не выбрано"+sep
        }
        else {
            initialText+=qnum.toString()+sep
        }

        var intel = MainObject.currentQuizMeta.use_statistics//(activity as MainActivity).settings.getInt("statistics_enabled", -1)
        initialText+=sep+resources.getString(R.string.initial_wrong_anwer_selector)+sep
        if (intel) {
            initialText+=resources.getString(R.string.initial_wrong_anwer_affects)
        }
        else {
            initialText+=resources.getString(R.string.initial_wrong_anwer_no_affect)
        }


        initialTV.text = initialText

    }

}
