package com.azhar.myblog.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.myblog.R
import com.azhar.myblog.adapter.MainAdapter
import com.azhar.myblog.adapter.MainAdapter.onSelectData
import com.azhar.myblog.model.ModelMain
import com.azhar.myblog.networking.BloggerApi
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), onSelectData {

    var mainAdapter: MainAdapter? = null
    var mProgressBar: ProgressDialog? = null
    var modelMain: MutableList<ModelMain> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mProgressBar = ProgressDialog(this)
        mProgressBar!!.setTitle("Mohon Tunggu")
        mProgressBar!!.setCancelable(false)
        mProgressBar!!.setMessage("Sedang menampilkan data...")

        llAbout.setOnClickListener {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java)) }

        llPP.setOnClickListener {
            startActivity(Intent(this@MainActivity, PrivacyPolicyActivity::class.java)) }

        llDisclaimer.setOnClickListener {
            startActivity(Intent(this@MainActivity, DisclaimerActivity::class.java)) }

        fabSource.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/AzharRivaldi")
            startActivity(intent)
        }

        rvListArticles.setHasFixedSize(true)
        rvListArticles.setLayoutManager(LinearLayoutManager(this))

        //get data
        listArticle
    }

    private val listArticle: Unit
        private get() {
            mProgressBar!!.show()
            AndroidNetworking.get(BloggerApi.ListPost)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            try {
                                mProgressBar!!.dismiss()
                                val playerArray = response.getJSONArray("items")
                                for (i in 0 until playerArray.length()) {
                                    val jsonObject1 = playerArray.getJSONObject(i)
                                    val dataApi = ModelMain()

                                    dataApi.title = jsonObject1.getString("title")
                                    dataApi.content = jsonObject1.getString("content")
                                    dataApi.labels = jsonObject1.getString("labels")
                                    dataApi.url = jsonObject1.getString("url")

                                    val datePost = jsonObject1.getString("published")
                                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    val outputFormat = SimpleDateFormat("dd-MM-yyyy")
                                    val date = inputFormat.parse(datePost)
                                    val datePostConvert = outputFormat.format(date)
                                    dataApi.published = datePostConvert

                                    val jsonObject2 = jsonObject1.getJSONObject("author")
                                    val authorPost = jsonObject2.getString("displayName")
                                    dataApi.author = authorPost

                                    val jsonObject3 = jsonObject2.getJSONObject("image")
                                    val authorImage = jsonObject3.getString("url")
                                    dataApi.authorImage = Uri.parse("http:$authorImage").toString()
                                    modelMain.add(dataApi)
                                    showListArticle()
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(this@MainActivity,
                                        "Gagal menampilkan data!", Toast.LENGTH_SHORT).show()
                            } catch (e: ParseException) {
                                e.printStackTrace()
                                Toast.makeText(this@MainActivity,
                                        "Gagal menampilkan data!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(anError: ANError) {
                            mProgressBar!!.dismiss()
                            Toast.makeText(this@MainActivity,
                                    "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show()
                        }
                    })
        }

    private fun showListArticle() {
        mainAdapter = MainAdapter(this@MainActivity, modelMain, this)
        rvListArticles!!.adapter = mainAdapter
    }

    override fun onSelected(modelMain: ModelMain) {
        val intent = Intent(this@MainActivity, DetailArtikelActivity::class.java)
        intent.putExtra("detailArtikel", modelMain)
        startActivity(intent)
    }
}