package ru.nomadmoon.quizats2


import android.os.Bundle
import android.app.Fragment
import android.graphics.BitmapFactory
import android.support.design.widget.Snackbar
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import ru.nomadmoon.quizats2.`object`.MainObject
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class questionFrag : Fragment(), View.OnClickListener {
    override fun onDestroy() {
        super.onDestroy()
        quizButtons.clear()
        //arrayOfAnswers.clear()


        quiznumlist.clear()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        quizButton1 = Button(activity)
        quizButton1.text = "Zzzzzzzz b1 Zzzzzzzz "
        quizButton1.tag=0
        quizButton1.setOnClickListener(this)

        quizButtons.add(quizButton1)
   //     quizButtons[0]=quizButton1



        quizButton2 = Button(activity)
        quizButton2.text = "Zzzzzzzz b2"
        quizButton2.tag=1
        quizButton2.setOnClickListener(this)

        quizButtons.add(quizButton2)
    //    quizButtons[1]=quizButton2


        quizButton3 = Button(activity)
        quizButton3.text = "Zzzzzzzz b3"
        quizButton3.tag=2
        quizButton3.setOnClickListener(this)

        quizButtons.add(quizButton3)
    //    quizButtons[2]=quizButton3

    }


    var innerquizdata: ArrayList<quizdata> = arrayListOf(quizdata(0, "q1", arrayOf("a1", "a2", "a3"), 0))
    var currentquizdata: quizdata = quizdata(0, "q1", arrayOf("a1", "a2", "a3"),0)
    var currentquiznumber: Int = -1
    var quiznumlist: ArrayList<Int> = arrayListOf(0)
    lateinit var quizLayout: LinearLayout
    lateinit var quizImage: ImageView
    lateinit var quizText: TextView

    lateinit var quizButton1: Button
    lateinit var quizButton2: Button
    lateinit var quizButton3: Button
    var rint = 0
    var rightAnswer = -1
    var localQuestionsCount: Int = 0

    var quizButtons: ArrayList<Button> = ArrayList()
   // var arrayOfAnswers = ArrayList<quizresult>()

    override fun onClick(p0: View) {
       // arrayOfAnswers[currentquiznumber].answer=p0.tag as Int
        MainObject.arrayOfAnswers[currentquiznumber].answer=p0.tag as Int

        if (p0.tag==rightAnswer)
            {
                if (MainObject.arrayOfQuestions[currentquiznumber].fails>1) MainObject.arrayOfQuestions[currentquiznumber].fails--
                  Snackbar.make(view, "Правильный ответ", Snackbar.LENGTH_LONG).show()
            }
        else
            {
                if (MainObject.arrayOfQuestions[currentquiznumber].fails<90) MainObject.arrayOfQuestions[currentquiznumber].fails++
                Snackbar.make(view, "Неправильный ответ", Snackbar.LENGTH_LONG).show()
            }

        Log.d("Zzzz befo", quiznumlist.toString())
        //when (quiznumlist.count()) {
        when (localQuestionsCount) {
            0->{
                val act = activity as MainActivity

                Log.d("Zzzz localQuestionsCount 0", "Zzzz localQuestionsCount 0")

                if (MainObject.currentQuizMeta.use_statistics) {
                    val qzFile = File(act.filesDir.toString() + "/quizes/" + MainObject.currentQuizDir + "/quiz_questions.txt")
                    qzFile.writeText(act.gson.toJson(MainObject.arrayOfQuestions))
                }

                MainObject.arrayOfAnswers.removeIf{it.answer==-1}


                act.showResultFragment()
                return
            }
            1-> {
                rint=0
                Log.d("Zzzz localQuestionsCount 1", "Zzzz localQuestionsCount 1")
                localQuestionsCount--

            }

            else -> {

                Log.d("Zzzz aft", quiznumlist.toString())
                Log.d("Zzzz localQuestionsCount", localQuestionsCount.toString())

                rint = Random().nextInt(quiznumlist.count() - 1)

                if (MainObject.currentQuizMeta.use_statistics) {
                    var rand100 = (Random().nextInt(118))-20

                    while (rand100 > MainObject.arrayOfQuestions[rint].fails) {
                        rint = Random().nextInt(quiznumlist.count() - 1)
                        rand100 = (Random().nextInt(118))-20
                        Log.d("Zzzz rolling", rint.toString())
                    }
                }

                localQuestionsCount--
            }
        }


        displayQuiz()
        quiznumlist.removeAt(rint)
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.d("Zzz", "onCreateView")




        innerquizdata = MainObject.arrayOfQuestions
        quiznumlist.clear()

        MainObject.arrayOfAnswers.clear()

        for (i in 1..MainObject.arrayOfQuestions.count()) {
            quiznumlist.add(i)
            //arrayOfAnswers.add(quizresult(-1,-1))
            MainObject.arrayOfAnswers.add(quizresult(-1,-1, i-1))
        }



        localQuestionsCount = MainObject.currentQuizMeta.questions_show_count-1


        var dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)

        var butHeight = dm.heightPixels/9


        quizLayout = LinearLayout(activity)
        quizLayout.orientation=LinearLayout.VERTICAL
        var layParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layParams.topMargin=30
        quizLayout.layoutParams=layParams

        quizImage = ImageView(activity)


        var imgParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, butHeight*3)
        quizImage.layoutParams = imgParams
        quizImage.setImageBitmap(BitmapFactory.decodeFile(context.filesDir.toString()+"/ping.jpg"))

        quizText = TextView(activity)
        var textParams  = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, butHeight)
        quizText.layoutParams = textParams
        quizText.text=""

        var butParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, butHeight)



        quizButton1.layoutParams=butParams
        quizButton2.layoutParams=butParams
        quizButton3.layoutParams=butParams

        rint = Random().nextInt(quiznumlist.count())
        Log.d("Zzzz", rint.toString())
        Log.d("Zzzz", quiznumlist.toString())
        displayQuiz()
        quiznumlist.removeAt(rint)


        quizLayout.addView(quizImage)
        quizLayout.addView(quizText)

        quizLayout.addView(quizButton1)
        quizLayout.addView(quizButton2)
        quizLayout.addView(quizButton3)

        return quizLayout

    }

    fun displayQuiz()
    {
        currentquizdata=MainObject.arrayOfQuestions[quiznumlist[rint]-1]

        currentquiznumber = quiznumlist[rint]-1

        quizImage.setImageBitmap(BitmapFactory.decodeFile(context.filesDir.toString()+"/quizes/"+MainObject.currentQuizDir+"/"+currentquizdata.img_num_id+".jpg"))
        quizText.text=currentquizdata.question

        for (z in 0..2) {
            if (currentquizdata.answers[z].substring(0,3)=="QQQ")
            {

                quizButtons[z].text=currentquizdata.answers[z].drop(3)
                rightAnswer = z
 //               arrayOfAnswers[quiznumlist[rint]-1].right_answer=z
                MainObject.arrayOfAnswers[quiznumlist[rint]-1].right_answer=z
            }
            else
            {
                quizButtons[z].text=currentquizdata.answers[z]
            }

        }

    }

}
