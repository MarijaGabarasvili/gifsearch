package gifsearch2.com

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide



class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //problem here
        setContentView(R.layout.activity_2)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val url = intent.getStringExtra("url")

        if (url != null) {
            Glide.with(this).load(url).into(imageView)

        } else {
            Toast.makeText(this, "There is no such url...", Toast.LENGTH_SHORT).show()
        }
    }
}