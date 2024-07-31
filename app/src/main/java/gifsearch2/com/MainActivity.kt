package gifsearch2.com


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Timer
import java.util.TimerTask
import java.util.logging.Handler
import java.util.logging.LogRecord

const val BASE_URL = "https://api.giphy.com/v1/"
const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        //Progress bar
        val progressBar = findViewById<View>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE


        Timer().schedule(object : TimerTask() {
            override fun run() {
                progressBar.visibility = View.INVISIBLE
            }
        }, 2000)

//        progressBar.visibility = View.VISIBLE
//        var count = 0
//        while(count<10000){
//            count ++
//
//        }
//        if(count>=10000){
//        }





        //SEARCH
//        val searchView: SearchView = findViewById(R.id.search_bar)
//        searchView.clearFocus()
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean{
//                filterList(newText)
//                return false
//            }
//        })


        //horizontal and vertical support
        val landscape = findViewById<View>(R.id.landscape)
        val portrait = findViewById<View>(R.id.portrait)
        landscape.setOnClickListener{
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        }
        portrait.setOnClickListener {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        //gif display
        val recView = findViewById<RecyclerView>(R.id.recyclerView)
        val gif = mutableListOf<DataObject>()
        val adapter = GifAdapter(this, gif)
        recView.adapter = adapter
        recView.setHasFixedSize(true)
        recView.layoutManager = GridLayoutManager(this, 2)

        //making gif appear on separate page (didnt work because of the error in acticity2.kt file. By debugging it, the problem is in opening activity_2.xml)
        adapter.setOnItemClickListener(object : GifAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, Activity2::class.java)

                intent.putExtra("url", gif[position].images.ogImage.url)
                startActivity(intent)
            }

        })

        //retrofit
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val retroService = retrofit.create(Service::class.java)
        retroService.getGif().enqueue(object : Callback<DataResult?>{
           @SuppressLint("NotifyDataSetChanged")
           override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>) {
                val body = response.body()
                if(body == null){
                    Log.d(TAG, "onResponse: No response")
                }
               //add to list
                gif.addAll(body!!.res)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Some problems has occured", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //tried to make a search from a gif which contains a list of all gifs and get their title. If title is equal to entered text the gifs will open

//    @SuppressLint("NotifyDataSetChanged")
//    private fun filterList(query: String?){
//        if(query != null){
//            val filteredList = ArrayList<DataObject>()
//            for(i in gif){
//                if(i.name.toLowerCase(java.util.Locale.ROOT).contains(query)){
//                    filteredList.add(i)
//                }
//            }
//            if(filteredList.isEmpty()){
//                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
//            } else{
//                val adapter= GifAdapter(this, filteredList)
//                adapter.notifyDataSetChanged()
//            }
//        }
//    }

}

