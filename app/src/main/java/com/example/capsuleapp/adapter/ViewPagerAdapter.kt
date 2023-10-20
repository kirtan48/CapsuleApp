package com.example.capsuleapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.capsuleapp.activity.MainActivity
import com.example.capsuleapp.fragments.NotesFragment
import com.example.capsuleapp.fragments.QuizFragment
import com.example.capsuleapp.fragments.VideoFragment

class ViewPagerAdapter(fragment: MainActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount():Int =3
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VideoFragment()
            1 -> NotesFragment()
            2 -> QuizFragment()
            else -> VideoFragment()
        }
    }
}