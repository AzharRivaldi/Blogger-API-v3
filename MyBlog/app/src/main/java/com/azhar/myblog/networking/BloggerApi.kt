package com.azhar.myblog.networking

/**
 * Created by Azhar Rivaldi on 22-12-2019.
 */

object BloggerApi {

    var BASE_URL = "https://www.googleapis.com/blogger/v3/blogs/"
    var API_KEY = "AIzaSyBP7_LCltLov1TZNwBGwgcFqpYjw2IzQCE"
    var BLOGGER_ID = "1449217210161387390"

    var ListPost = "$BASE_URL$BLOGGER_ID/posts?key=$API_KEY"

    var About = "$BASE_URL$BLOGGER_ID/pages/2888231457086622707?key=$API_KEY"

    var PrivacyPolicy = "$BASE_URL$BLOGGER_ID/pages/3139506612234691650?key=$API_KEY"

    var Disclaimer = "$BASE_URL$BLOGGER_ID/pages/6885628343544704308?key=$API_KEY"

}