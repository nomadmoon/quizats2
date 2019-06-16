package ru.nomadmoon.quizats2

import android.app.FragmentManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File
import com.google.gson.reflect.TypeToken
import java.util.zip.ZipFile
import android.content.Intent
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.LocaleList
//import android.widget.Toolbar
import android.support.v7.widget.Toolbar
import android.view.View
import ru.nomadmoon.quizats2.`object`.DummyContent
import ru.nomadmoon.quizats2.`object`.MainObject
import java.io.FileOutputStream
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SelectQuizFragment.OnListFragmentInteractionListener, View.OnClickListener {

    override fun attachBaseContext(newBase: Context) {
        val conf = Configuration()

                      val se = newBase.getSharedPreferences("quizats", Context.MODE_PRIVATE)
                  MainObject.appLang = se.getString("appLang", "en")

        //val quizesCount = File(newBase.filesDir.toString()+"/counter.txt")



        val locale = Locale(MainObject.appLang)
        val locList = LocaleList(locale)
        Locale.setDefault(locale)

        conf.setLocale(locale)
        conf.locales=locList

        super.attachBaseContext(newBase.createConfigurationContext(conf))

    }


    var initfrag = InitialFragment()
    var quefrag = questionFrag()
    var selfrag = SelectQuizFragment()
    var resfrag = ResultFragment()
    var confrag = TestConfigFragment()
    var appconfrag = AppConfigFragment()

    var fragMan: FragmentManager = fragmentManager
    var qdarr: ArrayList<quizdata> = ArrayList()
    var qmd = quizmetadata("Тест не выбран", "Загрузите файл с тестом", -1, -1, true)
    val gson = GsonBuilder().setPrettyPrinting().create()

    var main_questions: ArrayList<quizdata> = arrayListOf(quizdata(-1, "", arrayOf(""), 0))
    var main_answers: ArrayList<quizresult> = arrayListOf(quizresult(-1,-1, -1))

    lateinit var settings: SharedPreferences
    lateinit var sideMenu: Menu
    lateinit var maintoolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        maintoolbar=findViewById(R.id.toolbar)

        settings = getSharedPreferences("quizats", Context.MODE_PRIVATE)



        //MainObject.appLang = settings.getString("appLang", "english")


       //     }
       // }
        Log.d("Zzzz locale", resources.configuration.locales[0].language)

        MainObject.currentQuizDir=settings.getString("selected_test", "-1")
        if (MainObject.currentQuizDir!="-1") MainObject.currentQuizMeta=getMetaFromQuiz(MainObject.currentQuizDir)

        var navigation = findViewById<NavigationView>(R.id.nav_view)
        sideMenu = navigation.menu

        updateStartButton()


        qdarr.add(quizdata(1, "Hardcoded question", arrayOf("Hardcoded Answer 1","Hardcoded Answer 2","Hardcoded Answer 3"), 0))



        val quizesDir = File(filesDir.toString()+"/quizes")
        if (!quizesDir.exists()) quizesDir.mkdir()
        val quizesCount = File(filesDir.toString()+"/counter.txt")
        if (!quizesCount.exists())
        {
            quizesCount.createNewFile()
            quizesCount.writeText("0")
        }


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        val ft = fragMan.beginTransaction()

        //initfrag.initialTV.text="Booooooooo"

        ft.replace(R.id.fragmentMy, initfrag)
        ft.commit()


    }

    fun restartApp()
    {
        val i: Intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    fun loadFromZip()
    {


        // TEMPORARY!!!!
  //      val dirtodel = File(filesDir.toString().plus("/quizes/1"))
 //       if (dirtodel.exists()) dirtodel.deleteRecursively()

        val fzip = ZipFile(filesDir.toString().plus("/test.zip"))
//        var zipentries = fzip.entries().iterator()


        if (fzip.getEntry("quiz_questions.txt")==null) {
            Snackbar.make(findViewById(R.id.rootView), "Отсутствует файл с вопросами (quiz_questions.txt)", Snackbar.LENGTH_LONG).show()
            return
        }
        if (fzip.getEntry("quiz_metadata.txt")==null) {
            Snackbar.make(findViewById(R.id.rootView), "Отсутствует файл с метаданными (quiz_metadata.txt)", Snackbar.LENGTH_LONG).show()
            return
        }

        var quizQuestionsFile = fzip.getInputStream(fzip.getEntry("quiz_questions.txt"))
        var quizQuestionsJSON = quizQuestionsFile.bufferedReader().readText()

        val collectionType = object : TypeToken<ArrayList<quizdata>>() {}.type
        var qdarr_test: ArrayList<quizdata> = ArrayList()

        try {
            qdarr_test = gson.fromJson(quizQuestionsJSON, collectionType)
        }
        catch (e: JsonParseException)
        {
            Snackbar.make(findViewById(R.id.rootView), "Ошибка разбора JSON файла с вопросами (quiz_questions.txt)", Snackbar.LENGTH_LONG).show()
        }


        var quizMetaFile = fzip.getInputStream(fzip.getEntry("quiz_metadata.txt"))
        var quizMetaJSON = quizMetaFile.bufferedReader().readText()

        var quizMetaTest =  quizmetadata("Тест не выбран", "Загрузите файл с тестом", -1, -1, true)

        try {
            quizMetaTest = gson.fromJson(quizMetaJSON, quizmetadata::class.java)
        }
        catch (e: JsonParseException)
        {
            Snackbar.make(findViewById(R.id.rootView), "Ошибка разбора JSON файла с метаданными (quiz_metadata.txt)", Snackbar.LENGTH_LONG).show()
        }

        quizMetaTest.total_questions_count=qdarr_test.count()
        quizMetaTest.questions_show_count=qdarr_test.count()
        quizMetaTest.use_statistics=true

        for (qdarr_item in qdarr_test)
        {
            if (fzip.getEntry(qdarr_item.img_num_id.toString()+".jpg")==null)
            {
                Snackbar.make(findViewById(R.id.rootView), "Не найден файл "+qdarr_item.img_num_id.toString()+".jpg", Snackbar.LENGTH_LONG).show()
            }
        }

        var zipentries = fzip.entries().iterator()

        val quizesCount = File(filesDir.toString()+"/counter.txt")
        var cc = quizesCount.readText().toInt()
        cc=cc+1
        quizesCount.writeText(cc.toString())

        val quizesDirCC = File(filesDir.toString()+"/quizes/"+cc)

        if (!quizesDirCC.exists()) quizesDirCC.mkdir()

        for (iii in zipentries)
        {
            val outfile = File(quizesDirCC.toString()+"/"+iii.name).outputStream()
            fzip.getInputStream(iii).copyTo(outfile)
            // Log.d("Aaaaa", iii.name)
        }

        val quiz_metadata_txt = File(quizesDirCC.toString()+"/quiz_metadata.txt")
        quiz_metadata_txt.writeText(gson.toJson(quizMetaTest))

        File(filesDir.toString()+"/test.zip").delete()

        }



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun getMetaFromQuiz(quiz_dir: String): quizmetadata
        {
            val qzMetaFile = File(filesDir.toString()+"/quizes/"+quiz_dir+"/quiz_metadata.txt")

            return gson.fromJson<quizmetadata>(qzMetaFile.readText(), quizmetadata::class.java)

        }

    fun dumpMetaToQuiz()
    {
        val qzMetaFile = File(filesDir.toString()+"/quizes/"+MainObject.currentQuizDir+"/quiz_metadata.txt")
        qzMetaFile.writeText(gson.toJson(MainObject.currentQuizMeta))

        //return gson.fromJson<quizmetadata>(, quizmetadata::class.java)
        return

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_select_test -> {

                DummyContent.clearItems()
                val qzes = File(filesDir.toString().plus("/quizes/"))

                for (dir_entry in qzes.list()) {
                    Log.d("Bbbb", dir_entry)
//                    val qzMetaFile = File(filesDir.toString()+"/quizes/"+dir_entry+"/quiz_metadata.txt")

  //                  var localMeta = gson.fromJson<quizmetadata>(qzMetaFile.readText(), quizmetadata::class.java)

                    var localMeta = getMetaFromQuiz(dir_entry)
                    DummyContent.addItem(DummyContent.DummyItem(localMeta.name + " ("+dir_entry+")", localMeta.description, dir_entry))
                }

                val ft = fragMan.beginTransaction()

                ft.replace(R.id.fragmentMy, selfrag)
                ft.commit()
            }
            R.id.nav_start_test -> {
                val selected_test = settings.getString("selected_test", "-1")
                if (selected_test=="-1") return false

                val quizQuestionsJSON = File(filesDir.toString().plus("/quizes/"+selected_test+"/quiz_questions.txt")).readText()
                val collectionType = object : TypeToken<ArrayList<quizdata>>() {}.type


                try {
                    MainObject.arrayOfQuestions = gson.fromJson(quizQuestionsJSON, collectionType)
                } catch (e: JsonParseException) {
                    Snackbar.make(findViewById(R.id.rootView), "Ошибка разбора JSON файла с вопросами (quiz_questions.txt)", Snackbar.LENGTH_LONG).show()
                }



                val ft = fragMan.beginTransaction()

                ft.replace(R.id.fragmentMy, quefrag)
                //ft.addToBackStack(null)
                ft.commit()
            }
            R.id.nav_load_file -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"
                startActivityForResult(intent, 10510)

            }
            R.id.nav_test_settings -> {
                val ft = fragMan.beginTransaction()
                maintoolbar.title="Настройки теста"
                ft.replace(R.id.fragmentMy, confrag)
                ft.commit()
            }
            R.id.nav_app_settings -> {

                val ft = fragMan.beginTransaction()
                maintoolbar.title=getString(R.string.nav_app_settings_title)
                ft.replace(R.id.fragmentMy, appconfrag)
                ft.commit()

            }
            R.id.nav_exit -> {
                finishAndRemoveTask()
                //System.exit(0)
                //initfrag.refreshFragment()
               // val ft = fragMan.beginTransaction()
                //ft.replace(R.id.fragmentMy, initfrag)
               // ft.remove(confrag)


            }
            R.id.nav_hlp -> {

            }
            R.id.nav_about -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun updateStartButton()
    {
        val selected_test = settings.getString("selected_test", "-1")
        sideMenu.findItem(R.id.nav_start_test).setEnabled(false)
        sideMenu.findItem(R.id.nav_test_settings).setEnabled(false)

        if (selected_test=="-1") return

        if (File(filesDir.toString()+"/quizes/"+selected_test).exists())
        {
            sideMenu.findItem(R.id.nav_start_test).setEnabled(true)
            sideMenu.findItem(R.id.nav_test_settings).setEnabled(true)

        }


    }

    fun showResultFragment()
    {
        val ft = fragMan.beginTransaction()
        ft.replace(R.id.fragmentMy, resfrag)
        ft.commit()

    }


    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        if (item!=null) {
            settings.edit().putString("selected_test", item.dirId).apply()
            MainObject.currentQuizDir=item.dirId
            updateStartButton()
        }

        MainObject.currentQuizMeta=getMetaFromQuiz(MainObject.currentQuizDir)

        val ft = fragMan.beginTransaction()

        ft.replace(R.id.fragmentMy, initfrag)

        //    ft.remove(selfrag)
        ft.commit()
        //ft.add(initfrag)
    }

    override fun onClick(p0: View) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Log.d("Zzzzz p0.id", p0.id.toString())

        when (p0.id) {
            R.id.test_config_save_button->{
                dumpMetaToQuiz()

                if (MainObject.clearTestStat) {

                    val quizQuestionsJSON = File(filesDir.toString().plus("/quizes/"+ MainObject.currentQuizDir+"/quiz_questions.txt")).readText()
                    val collectionType = object : TypeToken<ArrayList<quizdata>>() {}.type


                    try {
                        MainObject.arrayOfQuestions = gson.fromJson(quizQuestionsJSON, collectionType)
                    } catch (e: JsonParseException) {
                        Snackbar.make(findViewById(R.id.rootView), "Ошибка разбора JSON файла с вопросами (quiz_questions.txt)", Snackbar.LENGTH_LONG).show()
                    }


                    MainObject.arrayOfQuestions.forEach {
                        it.fails = 1
                    }

                    val qzFile = File(filesDir.toString() + "/quizes/" + MainObject.currentQuizDir + "/quiz_questions.txt")
                    qzFile.writeText(gson.toJson(MainObject.arrayOfQuestions))
                    MainObject.clearTestStat=false
                }

                val ft = fragMan.beginTransaction()
                ft.replace(R.id.fragmentMy, initfrag)
                ft.commit()
            }
            R.id.lang_en->MainObject.appLang="en"
            R.id.lang_ru->MainObject.appLang="ru"
            R.id.app_config_save_button->{
                settings.edit().putString("appLang", MainObject.appLang).apply()
                restartApp()
            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10510 -> if (resultCode === Activity.RESULT_OK) {
                //val FilePath = data.getData().path
                //textView.setText(FilePath)
                var filetodel = File(filesDir.toString()+"/test.zip")
                if (filetodel.exists()) filetodel.delete()

                val inpstr = getContentResolver().openInputStream(data?.data)

                try {
                    val out = FileOutputStream(filesDir.toString()+"/test.zip")
                    try {
                        inpstr.copyTo(out)
                    } finally {
                        out.close()
                    }
                } finally {
                    inpstr.close()
                }

                loadFromZip()

                Snackbar.make(findViewById(R.id.rootView), "RESULT_OK", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}

// https://ufile.io/4uqkv
// https://ufile.io/kf7sz