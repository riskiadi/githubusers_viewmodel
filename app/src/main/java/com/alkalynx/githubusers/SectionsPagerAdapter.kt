package com.alkalynx.githubusers

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alkalynx.githubusers.fragment.FollowerFragment
import com.alkalynx.githubusers.fragment.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager, username: String) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mUsername = username

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.name_tab_one, R.string.name_tab_two)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {

        val followerFragment = FollowerFragment()
        val followingFragment = FollowingFragment()
        val mBundle = Bundle()
        mBundle.putString(FollowerFragment.EXTRA_NAME, mUsername)
        followerFragment.arguments = mBundle
        followingFragment.arguments = mBundle

        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = followerFragment
            1 -> fragment = followingFragment
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

}