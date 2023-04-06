package com.example.treespotter_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore

//        val tree = mapOf("name" to "pine", "dateSpotter" to Date())
        val tree = mapOf("name" to "oak", "dateSpotter" to Date())

        db.collection("tree").add(tree)
    }
}