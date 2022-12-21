package co.id.theztzt.movieappgli.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.id.theztzt.movieappgli.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}