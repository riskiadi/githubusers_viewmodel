package com.alkalynx.githubusers.utils

class Constant {

    val token = "82a8666861d9dd5ae69af0330ba10c4d0469f0ca"

    private val baseURL = "https://api.github.com"
    val searchURL = "$baseURL/search/users?q={username}"
    val followerURL = "$baseURL/users/{username}/followers"
    val followingURL = "$baseURL/users/{username}/following"
}