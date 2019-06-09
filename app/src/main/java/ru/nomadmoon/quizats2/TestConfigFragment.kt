package ru.nomadmoon.quizats2


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Switch
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
class TestConfigFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    lateinit var cardsnum: TextView
    lateinit var switch: Switch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var inflated = inflater.inflate(R.layout.fragment_test_config, container, false)
        var sb = inflated.findViewById<SeekBar>(R.id.testConfigSeekBar)
            sb.setOnSeekBarChangeListener(this)
            sb.max=MainObject.currentQuizMeta.total_questions_count-2
            sb.progress=MainObject.currentQuizMeta.questions_show_count-2

        cardsnum = inflated.findViewById<TextView>(R.id.cards_num)
        cardsnum.text="Показывать карточек: "+(sb.progress+2)

        switch = inflated.findViewById(R.id.test_config_intel_switch)
        switch.isChecked=MainObject.currentQuizMeta.use_statistics


        return inflated
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
       // if (p0 != null) {
       //     cardsnum.text = "Показывать карточек: " + (p0.progress + 2)
      //  }
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
      //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //
        if (p0 != null) {
                 cardsnum.text = "Показывать карточек: " + (p0.progress + 2)
          }

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
