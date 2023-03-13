package com.example.faraapp

import android.content.Intent
import android.media.Image
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import com.tapadoo.alerter.Alerter

class MainActivity : AppCompatActivity() {
    lateinit var seekBar: SeekBar
    lateinit var questionsList: ArrayList<QuestionModel>
    private var index: Int = 0
    lateinit var questionModel: QuestionModel
    lateinit var questionsImg: ImageView
    lateinit var questions: TextView
    lateinit var option1: Button
    lateinit var option2: Button
    lateinit var option3: Button
    lateinit var option4: Button
    private var correctAnswerCount: Int = 0
    private var wrongAnswerCount: Int = 0
    private var timeIndex = 0
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // hide the ActionBar
        supportActionBar?.hide()

        // Declare the Variable
        questionsImg = findViewById(R.id.imageView2)
        questions = findViewById(R.id.textView2)
        option1 = findViewById(R.id.button2)
        option2 = findViewById(R.id.button3)
        option3 = findViewById(R.id.button4)
        option4 = findViewById(R.id.button5)
        seekBar = findViewById(R.id.seekBar)
        // Declare the Array
        questionsList = ArrayList()
        questionsList.add(
            QuestionModel(
                R.drawable.ariha,
                "تشتهر بزراعة الموز وتقع في الأغوار ؟",
                "القدس",
                "يافا",
                "أريحا",
                "سوريا",
                "أريحا"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.yafa,
                "يعتبر ضمن أحد الأماكن الأثرية العظيمة والواقع في ؟",
                "أريحا",
                "المغرب",
                "يافا",
                "القدس",
                "يافا"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.jerusalem,
                "عاصمة فلسطين الأبدية ؟",
                "الجزائر",
                "القدس",
                "الخرطوم",
                "مسقط",
                "القدس"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.hifa,
                "تعود الصورة الأثرية السابقة ل ؟",
                "حيفا",
                "القدس",
                "غزة",
                "المغرب",
                "حيفا"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.yafa,
                "المكان في الصورة يفع في مدينة ؟",
                "أريحا",
                "المغرب",
                "يافا",
                "القدس",
                "يافا"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.tenen,
                "فاكهة غريبة ؟",
                "التنين",
                "الموز",
                "المانجا",
                "العنب",
                "التنين"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.bana,
                "الشجرة في الصورة تعود لفاكهة ؟",
                "الموز",
                "العنب",
                "الإجاص",
                "التين",
                "الموز"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.zayton,
                "تعتبر أحد الأشجار المباركة والمذكروة في القرءان ؟",
                "التين",
                "الزيتون",
                "البندورة",
                "الخيار",
                "الزيتون"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.ananas,
                "أوراق الشجر المقابل يعود لشجرة ؟",
                "الأناناس",
                "التوت",
                "العنب",
                "الخيار",
                "الأناناس"
            )
        )
        questionsList.add(
            QuestionModel(
                R.drawable.toto,
                "الشجرة في الصورة لفاكهة ؟",
                "التوت",
                "الإجاص",
                "البطيخ",
                "الشمام",
                "التوت"
            )
        )

        //questionsList.shuffle()
        questionModel = questionsList[index]
        setAllQuestions()
        countdown()
    }
    // Create the fun countdown
    fun countdown(){
        var duration:Long=TimeUnit.SECONDS.toMillis(5)
        object :CountDownTimer(duration, 50) {
            override fun onTick(millisUntilFinished: Long) {
                    seekBar.progress = timeIndex
                    timeIndex++
            }
            override fun onFinish() {
                index++
                if (index<questionsList.size){
                    questionModel=questionsList[index]
                    setAllQuestions()
                    resetBackground()
                    enableButton()
                    countdown()
                    timeIndex = 0
                }
            }
        }.start()
    }
    // Create the fun correctAns
    private fun correctAns(option: Button){
        option.background=getDrawable(R.drawable.right_bg)
        correctAnswerCount++
        textView.text = "Score : "+correctAnswerCount
        mediaPlayer = MediaPlayer.create(this,R.raw.yes_sound)
        mediaPlayer.start()
        // Alerter the Correct Ans
        Alerter.create(this@MainActivity)
            .setText("Greate!")
            .setIcon(R.drawable.ic_baseline_notifications_active_24)
            .setBackgroundColorRes(R.color.colorGreen)
            .setIconColorFilter(0) // Optional - Removes white tint
            .show()

    }

    // Create the fun wrongAns
    private fun wrongAns(option:Button){
        option.background=resources.getDrawable(R.drawable.wrong_bg)
        wrongAnswerCount++
        mediaPlayer = MediaPlayer.create(this,R.raw.no_sound)
        mediaPlayer.start()

        // Alerter the Wrong Ans
        Alerter.create(this@MainActivity)
            .setText("Bad!")
            .setIcon(R.drawable.ic_baseline_notifications_active_24)
            .setBackgroundColorRes(R.color.red)
            .setIconColorFilter(0) // Optional - Removes white tint
            .show()
    }
    // Create the fun setAllQuestions
    private fun setAllQuestions() {
        questionsImg.setBackgroundResource(questionModel.image)
        questions.text=questionModel.question
        questions.text=questionModel.question
        option1.text=questionModel.option1
        option2.text=questionModel.option2
        option3.text=questionModel.option3
        option4.text=questionModel.option4
    }
    // Create the fun enableButtons
    private fun enableButton(){
        option1.isClickable=true
        option2.isClickable=true
        option3.isClickable=true
        option4.isClickable=true
        button.isClickable=true
    }
    // Create the fun disableButton
    private fun disableButton(){
        option1.isClickable=false
        option2.isClickable=false
        option3.isClickable=false
        option4.isClickable=false
        button.isClickable=false
    }
    // Create the fun resetBackground
    private fun resetBackground(){
        option1.background=resources.getDrawable(R.drawable.option_bg)
        option2.background=resources.getDrawable(R.drawable.option_bg)
        option3.background=resources.getDrawable(R.drawable.option_bg)
        option4.background=resources.getDrawable(R.drawable.option_bg)
        option1.isVisible=true
        option2.isVisible=true
        option3.isVisible=true
        option4.isVisible=true

    }
    // Create the fun option1Clicked
    fun option1Clicked(view:View){
        disableButton()
        if(questionModel.option1==questionModel.answer){
            option1.background=resources.getDrawable(R.drawable.right_bg)
            correctAns(option1)
        }
        else{
            wrongAns(option1)
        }
    }
    // Create the fun option2Clicked
    fun option2Clicked(view:View){
        disableButton()
        if(questionModel.option2==questionModel.answer){
            option2.background=resources.getDrawable(R.drawable.right_bg)
            correctAns(option2)
        }
        else{
            wrongAns(option2)
        }
    }
    // Create the fun option3Clicked
    fun option3Clicked(view:View){
        disableButton()
        if(questionModel.option3==questionModel.answer){
            option3.background=resources.getDrawable(R.drawable.right_bg)
            correctAns(option3)
        }
        else{
            wrongAns(option3)
        }
    }
    // Create the fun option4Clicked
    fun option4Clicked(view: View){
        disableButton()
        if(questionModel.option4==questionModel.answer){
            option4.background=resources.getDrawable(R.drawable.right_bg)
            correctAns(option4)
        }
        else{
            wrongAns(option4)
        }
    }
    // Create the fun onBackPressed
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            finish()
        }
        else {
            backToast = Toast.makeText(baseContext, "اضغط مرتين للخروج من اللعبة!", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
    // Create the fun DeleteOption
    fun DeleteOption(view: View) {
        if (correctAnswerCount != 0 || correctAnswerCount == 3) {
            correctAnswerCount--
            textView.text = "Score : " + correctAnswerCount
            deletebtn()
        }
    }
    fun deletebtn(){
        var i = 0;
        var  array = arrayOf(option1,option2,option3,option4)
        for (k in array.indices step 1){
            if (array[k].text != questionsList[index].answer){
                array[k].isVisible = false
            }
        }
//        if (option1.text != questionsList[index].answer) {
//            option1.isVisible = false
//        }else if(option2.text != questionsList[index].answer){
//            option2.isVisible = false
//        }else if(option3.text != questionsList[index].answer){
//            option3.isVisible = false
//        }else if(option4.text != questionsList[index].answer){
//            option4.isVisible = false
//        }
    }
}