package com.alkalynx.githubusers.utils

import com.alkalynx.githubusers.BuildConfig

class Constant {

    val token = BuildConfig.GITHUB_TOKEN

    private val baseURL = "https://api.github.com"
    val searchURL = "$baseURL/search/users?q={username}"
    val followerURL = "$baseURL/users/{username}/followers"
    val followingURL = "$baseURL/users/{username}/following"

}

