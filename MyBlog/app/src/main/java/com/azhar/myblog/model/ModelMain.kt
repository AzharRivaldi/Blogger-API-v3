package com.azhar.myblog.model

import java.io.Serializable

/**
 * Created by Azhar Rivaldi on 22-12-2019.
 */

class ModelMain : Serializable {

    var published: String? = null
    var url: String? = null
    var title: String? = null
    var content: String? = null
    var labels: String? = null
    var author: String? = null
    var authorImage: String? = null

}