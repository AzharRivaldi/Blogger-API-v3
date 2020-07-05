package com.azhar.myblog.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.myblog.R
import com.azhar.myblog.networking.BloggerApi
import kotlinx.android.synthetic.main.activity_header.*
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat

class AboutActivity : AppCompatActivity() {

    var mProgressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = null

        mProgressBar = ProgressDialog(this)
        mProgressBar!!.setTitle("Mohon Tunggu")
        mProgressBar!!.setCancelable(false)
        mProgressBar!!.setMessage("Sedang menampilkan data...")

        //get data
        about
    }

    private val about: Unit
        private get() {
            mProgressBar!!.show()
            AndroidNetworking.get(BloggerApi.About)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            try {
                                mProgressBar!!.dismiss()
                                for (i in 0 until response.length()) {

                                    tvTitle!!.text = response.getString("title")

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                                        tvKetDetail!!.text = Html.fromHtml(response.getString("content"),
                                                Html.FROM_HTML_MODE_LEGACY) else {
                                        tvKetDetail!!.text = Html.fromHtml(response.getString("content"))
                                    }

                                    val datePost = response.getString("published")
                                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    val outputFormat = SimpleDateFormat("dd-MM-yyyy")
                                    val date = inputFormat.parse(datePost)
                                    val datePostConvert = outputFormat.format(date)
                                    tvDate!!.text = datePostConvert

                                    val source = response.getString("url")
                                    fabSource!!.setOnClickListener { v: View? ->
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse(source)
                                        startActivity(intent)
                                    }
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(this@AboutActivity,
                                        "Gagal menampilkan data!", Toast.LENGTH_SHORT).show()
                            } catch (e: ParseException) {
                                e.printStackTrace()
                                Toast.makeText(this@AboutActivity,
                                        "Gagal menampilkan data!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(anError: ANError) {
                            mProgressBar!!.dismiss()
                            Toast.makeText(this@AboutActivity,
                                    "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show()
                        }
                    })
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}