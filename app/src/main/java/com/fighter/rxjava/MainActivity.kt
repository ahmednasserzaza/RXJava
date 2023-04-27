package com.fighter.rxjava

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fighter.rxjava.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), CharServiceInterface {
    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private lateinit var service: CharService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        charsNotDone()
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonShowChars.setOnClickListener {
            charsNotDone()
            service = CharService(this)
            service.subscribe()
        }
    }

    private fun charsDone() {
        with(binding) {
            placeholderDone.visibility = View.VISIBLE
            textChar.visibility = View.GONE
        }
    }

    private fun charsNotDone() {
        with(binding) {
            placeholderDone.visibility = View.GONE
            textChar.visibility = View.VISIBLE
        }
    }

    override fun onEventReceived(event: Char) {
        binding.textChar.text = event.toString()
    }

    override fun onError(e: Throwable) {
        charsDone()
    }

    override fun onComplete() {
        charsDone()
    }

}