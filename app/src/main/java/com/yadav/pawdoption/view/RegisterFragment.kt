package com.yadav.pawdoption.view
import android.content.Intent
import com.yadav.pawdoption.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.yadav.pawdoption.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById(R.id.radio) as RadioGroup
        var linearLayout1 = view.findViewById(R.id.linearLayout1) as LinearLayout
        var linearLayout2 = view.findViewById(R.id.linearLayout2) as LinearLayout
        radioGroup.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

                if (shelter_btn.isChecked) {
                    linearLayout1.visibility = View.GONE
                    linearLayout2.visibility = View.VISIBLE
                } else {
                    linearLayout1.visibility = View.VISIBLE
                    linearLayout2.visibility = View.GONE
                }
            }
        })

    firebaseAuth = FirebaseAuth.getInstance()

        binding.registerBtn.setOnClickListener() {
            val email = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()
            val confirmPasswor = binding.passwordEt.text.toString()


            if(email.isNotEmpty() && pass.isNotEmpty() && confirmPasswor.isNotEmpty()){
                if(pass == confirmPasswor){
                   firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                                  if(it.isSuccessful){
                                      Toast.makeText(this.context,"Registered",Toast.LENGTH_SHORT).show()
                                      findNavController().navigate(R.id.action_registerFragment_to_petListFragment)
                                  }
                       else{
                           Toast.makeText(this.context,it.exception.toString(),Toast.LENGTH_SHORT).show()
                       }
                   }
                }
                else{
                    Toast.makeText(this.context,"Password not matching",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this.context,"Empty field is not allowed",Toast.LENGTH_SHORT).show()
            }

        }


    }



}