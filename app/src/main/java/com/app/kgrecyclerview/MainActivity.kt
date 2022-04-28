package com.app.kgrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.libarary.KgRecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<KgRecyclerView>(R.id.recycle)

        val list = mutableListOf<String>()

        for (i in 0..100) {
            list.add("item $i")
        }

        rv.setHasFixedSize(true)
        rv.setLayoutManager()
        rv.setNestedScrolling(true)
        val adapter = Adapter()
        adapter.insert(list)
        rv.setAdapter(adapter)

        rv.enableEndlessRecyclerView {
            val newList = mutableListOf<String>()
            for (i in 0..100){
                newList.add("item $i")
            }
            adapter.insert(newList)
        }

    }
}