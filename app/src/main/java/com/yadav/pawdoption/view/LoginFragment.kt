package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.yadav.pawdoption.R
import com.yadav.pawdoption.databinding.FragmentLoginBinding
import com.yadav.pawdoption.databinding.FragmentRegisterBinding
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import kotlinx.android.synthetic.main.fragment_register.*


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private lateinit var  firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.redirectRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmailEt.text.toString()
            val pass = binding.loginPasswordEt.text.toString()

            firebaseAuth = FirebaseAuth.getInstance()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this.context, "Login Successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_petListFragment2)
                    } else {
                        Toast.makeText(this.context, it.exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(this.context, "Empty field is not allowed", Toast.LENGTH_SHORT)
                    .show()
            }

        }


        binding.forgetPassword.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
        }


    }

    override fun onStart() {
        super.onStart()

        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser!=null){

            val uid = firebaseAuth.currentUser?.uid.toString()

            FirebaseDatabaseSingleton.getUserTypeReference().child(uid).get().addOnSuccessListener {
                if(it.getValue() != null){
                    val type = it.getValue()

                    print(type)
                }
            }
            print(uid)
//            findNavController().navigate(R.id.action_loginFragment_to_petListFragment2)
        }

    }


}