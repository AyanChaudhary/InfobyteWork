package com.example.infobyte.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.infobyte.R
import com.example.infobyte.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.math.sign

class FragmentLogin: Fragment(R.layout.fragment_login) {
    private lateinit var binding : FragmentLoginBinding
    companion object {
        private const val RC_SIGN_IN = 9001
    }
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentLoginBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        phonelogin()

        if (currentUser != null) {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentSignInWithGoogle)
        }
        binding.buttonLoginWithGoogle.setOnClickListener {
            signIn()
        }
        binding.btnDontHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentSignUp)
        }
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email=binding.etEmail.text.toString()
        val password=binding.etPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            val user=auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentSignInWithGoogle)
                }
                else{
                    Snackbar.make(requireView(),"SignIn failed : ${it.toString()}", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, FragmentLogin.RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FragmentLogin.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentSignInWithGoogle)
                } else {
                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun phonelogin(){
        binding.phoneverify.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentphoneotp)
        }
    }
}