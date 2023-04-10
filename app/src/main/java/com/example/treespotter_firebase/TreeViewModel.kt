package com.example.treespotter_firebase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class TreeViewModel : ViewModel() {

    // connect to firebase

    private  val db = Firebase.firestore
    private val treeCollectReference = db.collection("trees")

    val latestTrees = MutableLiveData<List<Tree>>()

    // get all of the the tree sightings

    private val latestTreesListener = treeCollectReference
        .orderBy("dateSpotted", Query.Direction.DESCENDING)
        .limit( 10)
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
               Log.e(TAG, "Error fetching latest trees", error)
            }
            else if (snapshot != null) {
//                val trees = snapshot.toObjects(Tree::class.java)
                val trees = mutableListOf<Tree>()
                // Looping each tree
                for (treeDocument in snapshot) {
                    val tree = treeDocument.toObject(Tree::class.java)
                    tree.documentReference = treeDocument.reference
                    trees.add(tree) // add tree to list of trees
                }

                        Log.d(TAG, "Trees from firebase: $trees")
                latestTrees.postValue(trees)
            }
        }

    fun setIsFavorite (tree: Tree, favorite: Boolean) {
        Log.d(TAG, "Updating tree $tree to favorite $favorite")
        tree.documentReference?.update("favorite", favorite)
    }


    fun addTree(tree: Tree) {
        treeCollectReference.add(tree)
            .addOnSuccessListener { treeDocumentReference ->
                Log.d(TAG, "New tree added at ${treeDocumentReference.path}")
            }

            .addOnFailureListener { error ->
                Log.e(TAG, "Error adding tree $tree", error)
            }

    }
}
