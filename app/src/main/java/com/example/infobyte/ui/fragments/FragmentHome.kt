package com.example.infobyte.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.infobyte.R
import com.example.infobyte.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentHome: Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)

        mAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)



        val textView = binding.name

        val auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
            val userName = user.displayName
            textView.text = "Welcome, " + userName
        } else {
        }

        val sign_out_button = binding.logoutButton
        sign_out_button.setOnClickListener {
            signOutAndStartSignInActivity()
        }
    }
    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            findNavController().navigate(R.id.action_fragmentSignInWithGoogle_to_fragmentLogin)
        }
    }
}