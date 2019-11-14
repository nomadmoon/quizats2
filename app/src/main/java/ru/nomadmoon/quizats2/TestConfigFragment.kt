package ru.nomadmoon.quizats2


import android.os.Bundle
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ru.nomadmoon.quizats2.`object`.MainObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TestConfigFragment : Fragment(), SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {
    override fun onStart() {
        super.onStart()
        Log.d("Zzzzzz", "TestConfigFragment onStart")

        sb.setOnSeekBarChangeListener(this)
        //sb.invalidate()
        sb.max=MainObject.currentQuizMeta.total_questions_count-2
        sb.progress=MainObject.currentQuizMeta.questions_show_count-2

        cardsnum.text=resources.getString(R.string.initial_cards_to_show)+(sb.progress+2)

        intel_switch.isChecked=MainObject.currentQuizMeta.use_statistics
        intel_switch.setOnCheckedChangeListener(this)
        intel_switch.text=resources.getString(R.string.initial_wrong_anwer_selector)+" "+resources.getString(R.string.initial_wrong_anwer_affects)

        immediate_switch.isChecked=MainObject.currentQuizMeta.immediate_show
        immediate_switch.setOnCheckedChangeListener(this)


        shuffle_switch.isChecked=MainObject.currentQuizMeta.shuffle_answers
        shuffle_switch.setOnCheckedChangeListener(this)

        clear_switch.isChecked=false
        clear_switch.setOnCheckedChangeListener(this)

        save_button.setOnClickListener(activity as View.OnClickListener)
    }



    lateinit var cardsnum: TextView
    lateinit var intel_switch: Switch
    lateinit var clear_switch: Switch
    lateinit var immediate_switch: Switch
    lateinit var shuffle_switch: Switch
    lateinit var save_button: Button
    lateinit var sb: SeekBar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        Log.d("Zzzzzz", "TestConfigFragment onCreateView")

        var inflated = inflater.inflate(R.layout.fragment_test_config, container, false)
         sb = inflated.findViewById<SeekBar>(R.id.testConfigSeekBar)



        cardsnum = inflated.findViewById<TextView>(R.id.cards_num)

        intel_switch = inflated.findViewById(R.id.test_config_intel_switch)

        clear_switch = inflated.findViewById(R.id.test_config_clearstat_switch)

        immediate_switch = inflated.findViewById(R.id.test_config_immediate_switch)

        shuffle_switch = inflated.findViewById(R.id.test_config_shuffle_switch)


        save_button = inflated.findViewById(R.id.test_config_save_button)



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
                 cardsnum.text = resources.getString(R.string.initial_cards_to_show) + (p0.progress + 2)
                MainObject.currentQuizMeta.questions_show_count=p0.progress + 2
          }

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if (p0!=null) {
            if (p0.id == R.id.test_config_intel_switch) {
                MainObject.currentQuizMeta.use_statistics = p1
            }
            if (p0.id == R.id.test_config_clearstat_switch) {
                MainObject.clearTestStat = p1
            }
            if (p0.id == R.id.test_config_immediate_switch) {
                MainObject.currentQuizMeta.immediate_show = p1
            }
            if (p0.id == R.id.test_config_shuffle_switch) {
                MainObject.currentQuizMeta.shuffle_answers = p1
            }
        }

        Log.d("Booooooo", MainObject.currentQuizMeta.use_statistics.toString())
    }

}
