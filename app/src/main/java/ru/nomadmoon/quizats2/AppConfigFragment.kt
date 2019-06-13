package ru.nomadmoon.quizats2


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AppConfigFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var inflated = inflater.inflate(R.layout.fragment_app_config, container, false)
        inflated.findViewById<Button>(R.id.app_config_save_button).setOnClickListener(activity as View.OnClickListener)
        val lang_en = inflated.findViewById<RadioButton>(R.id.lang_en)
        lang_en.setOnClickListener(activity as View.OnClickListener)

        val lang_ru = inflated.findViewById<RadioButton>(R.id.lang_ru)
        lang_ru.setOnClickListener(activity as View.OnClickListener)

        when ((activity as MainActivity).settings.getString("appLang", "en")){
            "ru"->lang_ru.isChecked=true
            "en"->lang_en.isChecked=true
        }


        return inflated
    }


}
