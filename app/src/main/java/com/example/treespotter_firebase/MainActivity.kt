package com.example.treespotter_firebase

import android.app.DownloadManager.Query
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Direction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

private const val TAG = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore

//        val tree = mapOf("name" to "pine", "dateSpotter" to Date())
//        db.collection("tree").add(tree)
//        val tree2 = mapOf("name" to "oak", "dateSpotter" to Date())
//        db.collection("tree").add(tree2)


//        val tree = Tree("Pine", Date())
//        db.collection("trees").add(tree)

        db.collection("trees")
            .whereEqualTo("name","Pine") // Filter string with Pine trees
            .whereEqualTo("favorite", true)
            .orderBy("dateSpotted", Query.Direction.DESCENDING) // Sorting data and composite index -Video 3/10 and 24:19
            .limit(3)
            .addSnapshotListener{  treeDocuments, error ->

            if (error != null) {
                Log.e(TAG, "Error getting all trees", error)

            }
            if ( treeDocuments != null) {
                for (treeDoc in treeDocuments) {

                 val treeFromFirebase = treeDoc.toObject(Tree::class.java)
//                    val name = treeDoc["name"]
//                    val dateSpotted = treeDoc["dateSpotted"]
//                    val favorite = treeDoc["favorite"]
                    val path = treeDoc.reference.path
                    Log.d(TAG, "$treeFromFirebase, $path")
                }
            }
        }
    }
}