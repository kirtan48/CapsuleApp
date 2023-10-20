package com.example.capsuleapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.capsuleapp.viewModel.CapsuleViewModel
import com.example.capsuleapp.activity.MainActivity
import com.example.capsuleapp.R
import com.example.capsuleapp.databinding.FragmentNotesBinding


class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    lateinit var viewModel: CapsuleViewModel
    private var textContent:String=""
    private lateinit var nextButton:CardView

    private val viewPager: ViewPager2 by lazy {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note:TextView=view.findViewById(R.id.tv_textContent)
        note.text=textContent
        val noQuestion:TextView=view.findViewById(R.id.tv_no_question)
        noQuestion.text="Questions : ${viewModel.capsule.quizQuestions.size.toString()}"
        nextButton=view.findViewById(R.id.cd_next)
        nextButton.setOnClickListener {
            // Get the index of the current fragment.
            val currentItem = viewPager.currentItem
            viewPager.setCurrentItem(currentItem+1,true)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNotesBinding.inflate(inflater, container, false)
        val linearLayout = (activity as? MainActivity)?.findViewById<LinearLayout>(R.id.linear_layout)
        Log.d("reached","linearLayout")
        Log.d("reached",viewPager.currentItem.toString())
        viewModel = ViewModelProvider(this).get(CapsuleViewModel::class.java)
        textContent=viewModel.capsule.textContent

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        Log.d("reached","resume++")
        val linearLayout = (activity as? MainActivity)?.findViewById<LinearLayout>(R.id.linear_layout)
        if (linearLayout != null) {
            linearLayout.visibility = View.VISIBLE
        }
        val underline2 = (activity as? MainActivity)?.findViewById<View>(R.id.underline2)
        val underline3 = (activity as? MainActivity)?.findViewById<View>(R.id.underline3)
        if(underline2!=null){
            underline2.setBackgroundResource(R.color.text_underline_selected)
        }
        if(underline3!=null){
            underline3.setBackgroundResource(R.color.text_underline_selected)
        }
        val progressBar = (activity as? MainActivity)?.findViewById<ProgressBar>(R.id.question_progress)
        val questionNo= (activity as? MainActivity)?.findViewById<TextView>(R.id.question_no)
        val cardBookMark=(activity as? MainActivity)?.findViewById<CardView>(R.id.cd_bookmark)
        if(progressBar!=null && questionNo!=null && cardBookMark !=null){
            progressBar.visibility=View.INVISIBLE
            questionNo.visibility=View.INVISIBLE
            cardBookMark.visibility=View.INVISIBLE
        }
        val linesText=(activity as? MainActivity)?.findViewById<TextView>(R.id.ll_tv2)
        val notesText=(activity as? MainActivity)?.findViewById<TextView>(R.id.ll_tv3)
        Log.d("reached","color")
        if(  linesText!=null && notesText!=null) {
            val resolvedColor = ContextCompat.getColor(requireContext(), R.color.black)
            Log.d("reached","color")
            linesText.setTextColor(resolvedColor)
            notesText.setTextColor(resolvedColor)

        }
    }
}