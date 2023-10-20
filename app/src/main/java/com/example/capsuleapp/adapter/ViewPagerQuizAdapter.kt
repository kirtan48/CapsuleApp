package com.example.capsuleapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.capsuleapp.R
import com.example.capsuleapp.activity.MainActivity
import com.example.capsuleapp.dataclass.QuizQuestion


class ViewPagerQuizAdapter(private var Question:List<QuizQuestion>, private val finishButtonClickListener: QuizResultClickListener, private val nextqu: nextQuestionListener, private val mainActivity: MainActivity):

    RecyclerView.Adapter<ViewPagerQuizAdapter.Pager2ViewHolder>() {
    private val answerList: MutableList<Int> = MutableList(Question.size) { -1 }
    private val checkedList: MutableList<Int> = MutableList(Question.size) { -1 }
    inner class Pager2ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val question: TextView =itemView.findViewById(R.id.tv_question)
        val option1: TextView =itemView.findViewById(R.id.tv_option1)
        val option1cd1:CardView=itemView.findViewById(R.id.option1_cd_1)
        val result:CardView=itemView.findViewById(R.id.quiz_result)
        val option2: TextView =itemView.findViewById(R.id.tv_option2)
        val option2cd1:CardView=itemView.findViewById(R.id.option2_cd_1)
        val option3: TextView =itemView.findViewById(R.id.tv_option3)
        val option3cd1:CardView=itemView.findViewById(R.id.option3_cd_1)
        val option4: TextView =itemView.findViewById(R.id.tv_option4)
        val option4cd1:CardView=itemView.findViewById(R.id.option4_cd_1)
       val tv_correct:TextView=itemView.findViewById(R.id.tv_check_answer)
        val next:CardView=itemView.findViewById(R.id.cd_next_que)
        val back:CardView=itemView.findViewById(R.id.cd_prev_que)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val viewHolder=Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quiz_question,parent,false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return Question.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.question.text="Q${position+1}. ${Question[position].question}"
        holder.option1.text=Question[position].answerChoices[0]
        holder.option2.text=Question[position].answerChoices[1]
        holder.option3.text=Question[position].answerChoices[2]
        holder.option4.text=Question[position].answerChoices[3]
        if(position==Question.size-1){
            holder.result.visibility=View.VISIBLE
        }
        else{
            holder.result.visibility=View.INVISIBLE
        }
        holder.next.setOnClickListener{
            nextqu.nextQuestionListener(position+1)
        }
        holder.back.setOnClickListener{
            nextqu.nextQuestionListener(position-1)
        }
        holder.result.setOnClickListener{
                finishButtonClickListener.QuizResultClickListener()
        }

        holder.option1.setOnClickListener {
            if(checkedList[position]==-1){

            answerList[position]=1

            holder.option1cd1.setBackgroundResource(R.drawable.selected_option)
            holder.option3cd1.setBackgroundResource(R.drawable.unselected_option)
            holder.option4cd1.setBackgroundResource(R.drawable.unselected_option)
            holder.option2cd1.setBackgroundResource(R.drawable.unselected_option)}

        }
        holder.option2.setOnClickListener {

            if(checkedList[position]==-1){
                answerList[position]=2
                holder.option2cd1.setBackgroundResource(R.drawable.selected_option)
                holder.option1cd1.setBackgroundResource(R.drawable.unselected_option)
                holder.option3cd1.setBackgroundResource(R.drawable.unselected_option)
                holder.option4cd1.setBackgroundResource(R.drawable.unselected_option)

            }

        }
        holder.option3.setOnClickListener {
            if(checkedList[position]==-1){

            answerList[position]=3
            holder.option3cd1.setBackgroundResource(R.drawable.selected_option)
            holder.option1cd1.setBackgroundResource(R.drawable.unselected_option)
            holder.option4cd1.setBackgroundResource(R.drawable.unselected_option)
            holder.option2cd1.setBackgroundResource(R.drawable.unselected_option)}
        }
        holder.option4.setOnClickListener {
            if(checkedList[position]==-1) {

                answerList[position] = 4
                holder.option4cd1.setBackgroundResource(R.drawable.selected_option)
                holder.option3cd1.setBackgroundResource(R.drawable.unselected_option)
                holder.option1cd1.setBackgroundResource(R.drawable.unselected_option)
                holder.option2cd1.setBackgroundResource(R.drawable.unselected_option)
            }
        }
        if(position==Question.size-1){
            val ans=checkAnswer()

        }

        holder.tv_correct.setOnClickListener {
            if(answerList[position]!=-1){
                checkedList[position]=0
                if(answerList[position]==Question[position].correctAnswerIndex){
                    correctAnswer(answerList[position],holder)
                }
                else{
                    wrongAnswer(answerList[position],Question[position].correctAnswerIndex,holder)
                }
            }
            else{
                Toast.makeText(mainActivity,"Please select option",Toast.LENGTH_SHORT).show()
            }
        }

    }

    interface QuizResultClickListener {
        fun QuizResultClickListener()
    }
    interface nextQuestionListener{
        fun nextQuestionListener(position: Int)
    }
    fun checkAnswer():Int{
        var cnt:Int=0
        for(i in 0 until Question.size){
            if(answerList[i]==Question[i].correctAnswerIndex){
                cnt++;
            }
        }

        return cnt
    }

private fun correctAnswer(answer:Int,holder: Pager2ViewHolder){
    if(answer==1)  holder.option1cd1.setBackgroundResource(R.drawable.correct_answer)
    else if(answer==2) holder.option2cd1.setBackgroundResource(R.drawable.correct_answer)
    else if(answer==3) holder.option3cd1.setBackgroundResource(R.drawable.correct_answer)
    else if(answer==4) holder.option4cd1.setBackgroundResource(R.drawable.correct_answer)
}
    private fun wrongAnswer(incorrect:Int,answer:Int,holder: Pager2ViewHolder){
        if(answer==1)  holder.option1cd1.setBackgroundResource(R.drawable.correct_answer)
        else if(answer==2) holder.option2cd1.setBackgroundResource(R.drawable.correct_answer)
        else if(answer==3) holder.option3cd1.setBackgroundResource(R.drawable.correct_answer)
        else if(answer==4) holder.option4cd1.setBackgroundResource(R.drawable.correct_answer)

        if(incorrect==1)  holder.option1cd1.setBackgroundResource(R.drawable.wrong_answer)
        else if(incorrect==2) holder.option2cd1.setBackgroundResource(R.drawable.wrong_answer)
        else if(incorrect==3) holder.option3cd1.setBackgroundResource(R.drawable.wrong_answer)
        else if(incorrect==4) holder.option4cd1.setBackgroundResource(R.drawable.wrong_answer)

    }



}