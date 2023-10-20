package com.example.capsuleapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.capsuleapp.viewModel.CapsuleViewModel
import com.example.capsuleapp.activity.MainActivity
import com.example.capsuleapp.dataclass.QuizQuestion
import com.example.capsuleapp.activity.QuizResultActivity
import com.example.capsuleapp.R
import com.example.capsuleapp.adapter.ViewPagerQuizAdapter
import com.example.capsuleapp.databinding.FragmentQuizBinding


class QuizFragment : Fragment() , ViewPagerQuizAdapter.QuizResultClickListener,
    ViewPagerQuizAdapter.nextQuestionListener {
    private val viewPager: ViewPager2 by lazy {
        binding.viewPagerQuiz
    }
    private val sharedPrefFile = "kotlinsharedpreference"
    lateinit var viewModel: CapsuleViewModel
    private  lateinit var binding: FragmentQuizBinding
    private   var nextQuestion: CardView?=null
    private   var option1: CardView?=null
    private   var option2: CardView?=null
    private   var option3: CardView?=null
    private   var option4: CardView?=null

    private var op1:TextView?=null
    private lateinit var questionList:List<QuizQuestion>
    private lateinit var adapter: ViewPagerQuizAdapter
    private val answerList: MutableList<Int> = MutableList(10) { -1 }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= (activity as? MainActivity)?.let {
            ViewPagerQuizAdapter(questionList,this,this,
                it
            )
        }!!
        viewPager.adapter=adapter
        viewPager.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateText(position)
            }
        })

        nextQuestion=view.findViewById(R.id.cd_next_que)
        nextQuestion?.setOnClickListener {
            val currentItem = viewPager.currentItem
            val nextItem = currentItem + 1
            viewPager.currentItem = nextItem
        }

        val currentItem = viewPager.currentItem

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentQuizBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CapsuleViewModel::class.java)
        questionList=viewModel.capsule.quizQuestions
        return binding.root
    }
    override fun QuizResultClickListener(){
        val score:Int=adapter.checkAnswer()
        val intent= Intent(requireContext(), QuizResultActivity::class.java)

        val sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putInt("score",score )
        editor.apply()
        editor.commit()


        val timer = (activity as? MainActivity)?.findViewById<ProgressBar>(R.id.question_progress)

        startActivity(intent)

    }
    override fun nextQuestionListener(pos:Int){
        if(pos==0)viewPager.currentItem=pos
        else viewPager.currentItem=pos


    }
    override fun onDestroyView() {
        super.onDestroyView()
        val parentViewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        parentViewPager?.isUserInputEnabled = true // Re-enable swipe in the parent ViewPager
    }

    override fun onResume() {
        super.onResume()

        val linearLayout = (activity as? MainActivity)?.findViewById<LinearLayout>(R.id.linear_layout)
        if (linearLayout != null) {
            linearLayout.visibility = View.INVISIBLE
        }

        val progressBar = (activity as? MainActivity)?.findViewById<ProgressBar>(R.id.question_progress)
        val questionNo= (activity as? MainActivity)?.findViewById<TextView>(R.id.question_no)
        val cardBookMark=(activity as? MainActivity)?.findViewById<CardView>(R.id.cd_bookmark)
        if(progressBar!=null && questionNo!=null && cardBookMark !=null){
            progressBar.visibility=View.VISIBLE
            questionNo.visibility=View.VISIBLE
            cardBookMark.visibility=View.VISIBLE
        }



        Handler().post {

            val parentViewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
            parentViewPager?.isUserInputEnabled = false
        }

    }
    private fun updateText(position: Int) {
        val progres: ProgressBar = requireActivity().findViewById(R.id.question_progress)
        progres.max = questionList.size
        progres.progress = position + 1

        val questionNo: TextView = requireActivity().findViewById(R.id.question_no)
        questionNo.text = "Question ${position + 1}/${questionList.size}"
    }


}