package com.example.lifecycle

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var timeline: TextView
    private lateinit var status: TextView

    companion object {
        // In-memory history spans Activity recreation, but resets with the app process.
        private val events = ArrayDeque<String>()
        private var sequence = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val scroll = ScrollView(this).apply { setBackgroundColor(0xFFF5F3FF.toInt()); isFillViewport = true }
        val body = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val padding = (24 * resources.displayMetrics.density).toInt()
            setPadding(padding, padding, padding, padding)
        }
        scroll.addView(body)
        fun label(value: String, size: Float): TextView = TextView(this).apply {
            text = value; textSize = size; setTextColor(0xFF302747.toInt())
            setPadding(0, 12, 0, 20)
            body.addView(this)
        }
        label(getString(R.string.app_name), 32f)
        label(getString(R.string.subtitle), 18f)
        status = label("", 20f)
        label(getString(R.string.instructions), 16f)
        label(getString(R.string.timeline_heading), 22f)
        timeline = label("", 16f).apply { setTextIsSelectable(true); accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE }
        body.addView(Button(this).apply {
            text = getString(R.string.clear_history)
            setOnClickListener { events.clear(); sequence = 0; timeline.text = getString(R.string.empty_history) }
        })
        label(getString(R.string.history_note), 14f)
        setContentView(scroll)
        ViewCompat.setOnApplyWindowInsetsListener(scroll) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        record("onCreate", R.string.event_create)
    }

    private fun record(callback: String, explanation: Int) {
        sequence++
        events.addLast("$sequence. $callback\n${getString(explanation)}")
        while (events.size > 40) events.removeFirst()
        Log.d("LoopLab", callback)
        if (::timeline.isInitialized) {
            timeline.text = events.joinToString("\n\n")
            status.text = getString(R.string.latest_event, callback)
        }
    }

    override fun onStart() { super.onStart(); record("onStart", R.string.event_start) }
    override fun onResume() { super.onResume(); record("onResume", R.string.event_resume) }
    override fun onPause() { record("onPause", R.string.event_pause); super.onPause() }
    override fun onStop() { record("onStop", R.string.event_stop); super.onStop() }
    override fun onRestart() { super.onRestart(); record("onRestart", R.string.event_restart) }
    override fun onDestroy() { record("onDestroy", R.string.event_destroy); super.onDestroy() }
}