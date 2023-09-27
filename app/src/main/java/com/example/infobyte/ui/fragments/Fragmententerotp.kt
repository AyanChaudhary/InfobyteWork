package com.example.infobyte.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.infobyte.R
import com.example.infobyte.databinding.FragmentFragmententerotpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class Fragmententerotp : Fragment() {
  private val binding by lazy {
FragmentFragmententerotpBinding.inflate(layoutInflater)
  }
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mobileNumber = requireArguments().getString("mobilnumber").toString()
        verificationId = requireArguments().getString("mainotp").toString()
//       resendToken= requireArguments().getParcelable<PhoneAuthCredential>("resendotp").toString()

        binding.mobilenumber.text = mobileNumber

        addTextChangeListeners()

        binding.verifybutton.setOnClickListener {
            val verifyOtp =
                binding.editTextText8.text.toString() +
                        binding.editTextText9.text.toString() +
                        binding.editTextText10.text.toString() +
                        binding.editTextText11.text.toString() +
                        binding.editTextText12.text.toString() +
                        binding.editTextText13.text.toString()

            if (verifyOtp.isNotEmpty()) {
                if (verifyOtp.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, verifyOtp)
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(requireContext(), "Please enter the entire OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textView12.setOnClickListener {
            if(mobileNumber!=null){
                resendVerificationCode(mobileNumber)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Verification successful", Toast.LENGTH_SHORT).show()

                } else {
                    // Verification failed
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "Invalid OTP", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Verification failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun addTextChangeListeners() {
        binding.editTextText8.addTextChangedListener(Edittextwatcher(binding.editTextText8))
        binding.editTextText9.addTextChangedListener(Edittextwatcher(binding.editTextText9))
        binding.editTextText10.addTextChangedListener(Edittextwatcher(binding.editTextText10))
        binding.editTextText11.addTextChangedListener(Edittextwatcher(binding.editTextText11))
        binding.editTextText12.addTextChangedListener(Edittextwatcher(binding.editTextText12))
        binding.editTextText13.addTextChangedListener(Edittextwatcher(binding.editTextText13))
    }

    private fun resendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken) // Specify the token obtained from previous verification
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases, the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices, Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.

            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("TAG", "onVerificationFailed: $e")
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d("TAG", "onVerificationFailed: $e")
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            this@Fragmententerotp.verificationId = verificationId
            resendToken = token
            Toast.makeText(requireContext(), "OTP has been resent", Toast.LENGTH_SHORT).show()
        }
    }

    inner class Edittextwatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when (view.id) {
                R.id.editTextText8 -> if (text.length == 1) binding.editTextText9.requestFocus()
                R.id.editTextText9 -> {
                    if (text.length == 1) binding.editTextText10.requestFocus()
                    else if (text.isEmpty()) binding.editTextText8.requestFocus()
                }
                R.id.editTextText10 -> {
                    if (text.length == 1) binding.editTextText11.requestFocus()
                    else if (text.isEmpty()) binding.editTextText9.requestFocus()
                }
                R.id.editTextText11 -> {
                    if (text.length == 1) binding.editTextText12.requestFocus()
                    else if (text.isEmpty()) binding.editTextText10.requestFocus()
                }
                R.id.editTextText12 -> {
                    if (text.length == 1) binding.editTextText13.requestFocus()
                    else if (text.isEmpty()) binding.editTextText11.requestFocus()
                }
                R.id.editTextText13 -> if (text.isEmpty()) binding.editTextText12.requestFocus()
            }
        }
    }


}










