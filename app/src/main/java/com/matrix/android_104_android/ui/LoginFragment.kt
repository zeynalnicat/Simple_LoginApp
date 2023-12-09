package com.matrix.android_104_android.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.matrix.android_104_android.R
import com.matrix.android_104_android.databinding.FragmentLoginBinding
import com.matrix.android_104_android.db.RoomDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var db: RoomDb? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentLoginBinding.inflate(inflater)
        db = RoomDb.accessDB(requireContext())
        adaptLayout()
        login()
        setNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSharedPreferences()
    }


    private fun checkSharedPreferences() {
        val sharedPreferences = activity?.getSharedPreferences("UserRegistration", Context.MODE_PRIVATE)

        if (sharedPreferences!!.contains("username")) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun adaptLayout() {
        binding.txtHeader.text = "Login"
        binding.btn.text = "Login"
        binding.txtNav.text = "Have not an account ? Register"
    }

    private fun setNavigation() {
        binding.txtNav.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun login() {
        binding.btn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val username = binding.edtUsername.text.toString()
                val password = binding.edtPassword.text.toString()
                val checkLogin = db?.userDao()?.checkLogin(username, password)
                if (checkLogin != 0) {
                    val preference =
                        activity?.getSharedPreferences("UserRegistration", Context.MODE_PRIVATE)
                    with(preference?.edit()) {
                        this?.putString("password", password)
                        this?.putString("username", username)
                        this?.commit()
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }else{
                    Snackbar.make(requireView(),"Wrong username or password",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}