package com.example.infobyte.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.infobyte.R
import com.example.infobyte.databinding.FragmentFragmentphoneotpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class Fragmentphoneotp : Fragment() {
    private val binding by lazy {
        FragmentFragmentphoneotpBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button.setOnClickListener {
            if(binding.editTextText2.text.toString().isNotEmpty()){
                if(binding.editTextText2.text.toString().length==10){
                    var number=binding.editTextText2.text.trim().toString()
                    number=binding.textView5.text.toString()+number
                    val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                        .setPhoneNumber(number) // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity()) // Activity (for callback binding)
                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)



                }else{
                    Toast.makeText(requireContext(), "Please enter the correct  number", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), "Please enter the number", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {


                    val user = task.result?.user
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    }

                }
            }
    }


    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.

            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.


            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("TAG","onverficationsent : ${e.toString()}")

            } else if (e is FirebaseTooManyRequestsException) {
                Log.d("TAG","onverficationsent : ${e.toString()}")
            }


        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            var number1=binding.editTextText2.text.trim().toString()
            number1=binding.textView5.text.toString()+number1
            findNavController().navigate(R.id.action_fragmentphoneotp_to_fragmententerotp,Bundle().apply {

                putString("mainotp",verificationId)
                putParcelable("resendotp",token)
                putString("mobilnumber",number1)


            })

            Toast.makeText(requireContext(), "Otp sent", Toast.LENGTH_SHORT).show()


        }
    }
    }
