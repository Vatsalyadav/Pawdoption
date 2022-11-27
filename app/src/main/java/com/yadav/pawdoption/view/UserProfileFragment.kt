package com.yadav.pawdoption.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yadav.pawdoption.R
import com.yadav.pawdoption.databinding.FragmentLoginBinding
import com.yadav.pawdoption.databinding.FragmentUserProfileBinding
import com.yadav.pawdoption.model.User
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton


class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private lateinit var databaseReference: DatabaseReference
    private var userName = MutableLiveData<HashMap<String, String>>()

    private var user = ""
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

//        if(Firebase.auth.currentUser == null){
//            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
//                .navigate(R.id.loginFragment)
//        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        var uid = firebaseAuth.currentUser?.uid
        if(firebaseAuth.currentUser!=null){
            firebaseAuth.currentUser?.let {
                binding.userEmail.text = "Logged in with " + it.email
            }
        }

        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.loginFragment)
        }
    }



}