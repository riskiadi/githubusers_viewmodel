package com.alkalynx.consumerapp.utils

import com.alkalynx.consumerapp.BuildConfig

class Constant {

    val token = BuildConfig.GITHUB_TOKEN

    private val baseURL = "https://api.github.com"
    val followerURL = "$baseURL/users/{username}/followers"
    val followingURL = "$baseURL/users/{username}/following"

}

