package com.yadav.pawdoption.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import com.yadav.pawdoption.model.Shelter
import com.yadav.pawdoption.model.User
import com.yadav.pawdoption.model.UserType
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import com.yadav.pawdoption.persistence.UsersDAO
import kotlinx.android.synthetic.main.fragment_register.*


class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private lateinit var databaseReference: DatabaseReference
    private var userName = MutableLiveData<HashMap<String, String>>()
    private lateinit var auth: FirebaseAuth
    private var userType = ""
    private val usersDAO = UsersDAO()
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

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var linearLayout1 = view.findViewById(R.id.linearLayout1) as LinearLayout
        var linearLayout2 = view.findViewById(R.id.linearLayout2) as LinearLayout


        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.loginFragment)
        }



        firebaseAuth = FirebaseAuth.getInstance()
       var uid = firebaseAuth.currentUser?.uid

        auth = Firebase.auth
        firebaseAuth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("UserType")
            databaseReference.child(uid.toString()).addValueEventListener(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    val shelter=dataSnapshot.getValue()
                    if(shelter==null){
                        Log.e("User","User data is null")
                        return
                    }
                       userType = shelter.toString()
                    if(userType == "petAdopter"){
                        linearLayout1.visibility = View.VISIBLE
                        linearLayout2.visibility = View.GONE
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

                        databaseReference!!.child(uid!!).addValueEventListener(object: ValueEventListener{
                            override fun onDataChange(dataSnapshot: DataSnapshot){
                                val user=dataSnapshot.getValue(User::class.java)

                                //Check for null
                                if(user==null){
                                    Log.e("User","User data is null")
                                    return
                                }
                                Log.e("user","User data is changed!"+user.name)

                                binding.userName.setText("Name : " + user.name)
                                binding.userAddress.setText("Address :" + user.address )

                                binding.viewMyAppointments.setOnClickListener() {
                                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                                        .navigate(R.id.myAppointment)
                                }

                                //Display newly updated name and email

                            }
                            override fun onCancelled(error: DatabaseError){
                                //Failed to read value
                                Log.e("User","Failed to read user",error.toException())
                            }
                        })
                    }
                    else{

                        linearLayout1.visibility = View.GONE
                        linearLayout2.visibility = View.VISIBLE
                        databaseReference = FirebaseDatabase.getInstance().getReference("Shelters")
                        databaseReference!!.child(uid!!).addValueEventListener(object: ValueEventListener{
                            override fun onDataChange(dataSnapshot: DataSnapshot){
                                val shelter=dataSnapshot.getValue(Shelter::class.java)

                                //Check for null
                                if(shelter==null){
                                    Log.e("User","User data is null")
                                    return
                                }
                                Log.e("user","User data is changed!"+shelter.name)

                                binding.shelterName.setText("Name : "+shelter.name)
                                binding.shelterAddress.setText("Address : " + shelter.address)
                                binding.shelterDescription.setText("Description : " + shelter.description)

                                binding.viewDonations.setOnClickListener() {
                                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                                        .navigate(R.id.myAppointment)
                                }
                                //Display newly updated name and email

                            }
                            override fun onCancelled(error: DatabaseError){
                                //Failed to read value
                                Log.e("User","Failed to read user",error.toException())
                            }
                        })
                    }
                }
                override fun onCancelled(error: DatabaseError){
                    //Failed to read value
                    Log.e("User","Failed to read user",error.toException())
                }
            })



        }




        //User data change Listener


    }



}