package com.azhar.myblog.networking

/**
 * Created by Azhar Rivaldi on 22-12-2019.
 */

object BloggerApi {

    var BASE_URL = "https://www.googleapis.com/blogger/v3/blogs/"
    var API_KEY = "API_KEY"
    var BLOGGER_ID = "ID_BLOG"

    var ListPost = "$BASE_URL$BLOGGER_ID/posts?key=$API_KEY"

    var About = "$BASE_URL$BLOGGER_ID/pages/ID_PAGES?key=$API_KEY"

    var PrivacyPolicy = "$BASE_URL$BLOGGER_ID/pages/ID_PAGE?key=$API_KEY"

    var Disclaimer = "$BASE_URL$BLOGGER_ID/pages/ID_PAGE?key=$API_KEY"

}
