package com.yadav.pawdoption.view
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.hbb20.CountryCodePicker
import com.yadav.pawdoption.R
import com.yadav.pawdoption.databinding.FragmentRegisterBinding
import com.yadav.pawdoption.model.Shelter
import com.yadav.pawdoption.model.User
import com.yadav.pawdoption.model.UserType
import com.yadav.pawdoption.persistence.SheltersDAO
import com.yadav.pawdoption.persistence.UsersDAO
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private lateinit var  firebaseAuth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private val usersDAO = UsersDAO()
    private val sheltersDAO = SheltersDAO()
    var ccp: CountryCodePicker? = null




    //for validation




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

        binding.redirectLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

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



            if(linearLayout1.visibility == View.VISIBLE){  //pet owner
                val email = binding.emailEt.text.toString()
                var pass = binding.passwordEt.text.toString()
                val confirmPasswor = binding.repasswordEt.text.toString()
                val Name = binding.firstNameEt.text.toString() + " " + binding.lastNameEt.text.toString()
                val phoNumber = binding.phoneNumberEt.text.toString()
                val address = binding.addressEt.text.toString()
                val userType = "petAdopter"

//                binding.passwordEt.setOnFocusChangeListener{
//                        _,focused->
//                    if(!focused){
//                        pass = binding.passwordEt.text.toString()
//
//                        if(pass.length<6){
//                            binding.password.helperText = "Minimum 6 characters length password"
//                        }
//
//                        if(!pass.matches(".*[A-Z].*".toRegex())){
//                            binding.password.helperText = "Must contain 1 Upper case charcter"
//                        }
//
//                        if(!pass.matches(".*[a-z].*".toRegex())){
//                            binding.password.helperText = "Must contain 1 Upper case charcter"
//                        }
//                        if(!pass.matches(".*[@#\$%^&+=].*".toRegex())){
//                            binding.password.helperText = "Must contain 1 special character"
//                        }
//                    }
//                }

                if(email.isNotEmpty() && pass.isNotEmpty() && confirmPasswor.isNotEmpty()){

                    if(pass == confirmPasswor){
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                            if(it.isSuccessful){

                                //enetr user data into the database

                                val uid = firebaseAuth.currentUser?.uid
                                databaseReference = FirebaseDatabase.getInstance().getReference("Users")

                                if(uid!=null){
                                    val user = User(null,Name,address,phoNumber)

                                    databaseReference.child(uid).setValue(user).addOnCompleteListener{
                                        if(it.isSuccessful){
                                          //  databaseReference.child(uid).setValue(userType)
                                            Toast.makeText(this.context,"Registered",Toast.LENGTH_SHORT).show()
                                        }
                                        else{
                                            Toast.makeText(this.context,"was not able to create profile",Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    databaseReference = FirebaseDatabase.getInstance().getReference("UserType")

                                    databaseReference.child(uid).setValue(userType).addOnCompleteListener {
                                        if(it.isSuccessful){
                                            //  databaseReference.child(uid).setValue(userType)
                                            Log.d(userType, "User Type succesfully added ");
                                        }
                                        else{
                                            Log.d(userType, "User Type not succesfully added ");
                                        }

                                    }
                                }
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
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

            else{
                val email = binding.shelterEmailEt.text.toString()
                val pass = binding.shelterPasswordEt.text.toString()
                val confirmPasswor = binding.shelterRepasswordEt.text.toString()

                val Name = binding.shelterNameEt.text.toString()
                val phoNumber = binding.phoneNumberEt.text.toString()
                val address1 = binding.addressEt.text.toString()
                val address2 = binding.address2Et.text.toString()
                val shelterDesc = binding.shelterDescriptionEt.text.toString()
                val userType = "shelterOwner"



                if(email.isNotEmpty() && pass.isNotEmpty() && confirmPasswor.isNotEmpty()){
                    if(pass == confirmPasswor){
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                            if(it.isSuccessful){

                                val uid = firebaseAuth.currentUser?.uid
                                databaseReference = FirebaseDatabase.getInstance().getReference("Shelters")

                                if(uid!=null){
                                    val shelter = Shelter(uid,Name,shelterDesc,address2,null,null)
                                  //  var hashMap : HashMap<String, String> = HashMap<String, String> ()
                                    //hashMap.put(uid,UserType)
                                    //val userType = UserType(hashMap)

                                    databaseReference.child(uid).setValue(shelter).addOnCompleteListener{
                                        if(it.isSuccessful){
                                            Toast.makeText(this.context,"Registered",Toast.LENGTH_SHORT).show()
                                        }
                                        else{
                                            Toast.makeText(this.context,"was not able to create profile",Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    databaseReference = FirebaseDatabase.getInstance().getReference("UserType")

                                    databaseReference.child(uid).setValue(userType).addOnCompleteListener {
                                        if(it.isSuccessful){
                                            //  databaseReference.child(uid).setValue(userType)
                                            Log.d(userType, "User Type succesfully added ");
                                        }
                                        else{
                                            Log.d(userType, "User Type not succesfully added ");
                                        }

                                    }
                                }
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
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





//    private fun emailFocusListener(){
//        binding.emailEt.setOnFocusChangeListener{
//            _,focused->
//            if(!focused){
//                binding.email.helperText= validEmail()
//            }
//        }
//    }
//
//
//    private fun validEmail(): String?{
//        val emailText = binding.emailEt.text.toString()
//        if(Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
//            return "Invalid Email Address"
//        }
//        return "valid"
//
//    }
//
//
//    private fun passwordFocusListener(){
//        binding.passwordEt.setOnFocusChangeListener{
//                _,focused->
//            if(!focused){
//                password.helperText= validPassword()
//            }
//        }
//    }
//
//
//    private fun validPassword(): String?{
//        val passwordText = binding.passwordEt.text.toString()
//
//        if(passwordText.length<6){
//            return "Minimum 6 characters length password"
//        }
//
//        if(!passwordText.matches(".*[A-Z].*".toRegex())){
//            return "Must contain 1 Upper case charcter"
//        }
//
//        if(!passwordText.matches(".*[a-z].*".toRegex())){
//            return "Must contain 1 Upper case charcter"
//        }
//        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex())){
//            return "Must contain 1 special character"
//        }
//        return null
//
//    }



}