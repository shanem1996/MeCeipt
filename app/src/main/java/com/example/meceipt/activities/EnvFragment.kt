package com.example.meceipt.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.meceipt.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnvFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnvFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_env, container, false)

        val firestore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser!!.uid
        val userDocRef = firestore.collection("User").document(userId)
        val receiptCollectionRef = userDocRef.collection("Receipt")

        userDocRef.get().addOnSuccessListener { documentSnapshot ->
            val fName = documentSnapshot.getString("fName")
            val welcomeTf = view.findViewById<TextView>(R.id.tfWelcome)
            welcomeTf.text = "Hi $fName!, you can find your Environmental Impact Statistics below!"

        }

        receiptCollectionRef.get().addOnSuccessListener { snapshot ->
            val receiptAmount = snapshot.size().toString()
            val tfAmount = view.findViewById<TextView>(R.id.tfReceiptAmount)
            tfAmount.text = "You have accumulated $receiptAmount receipts!"


        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EnvFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EnvFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}