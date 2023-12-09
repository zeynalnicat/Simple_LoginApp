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
import com.matrix.android_104_android.db.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var db: RoomDb? = null
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        db = RoomDb.accessDB(requireContext())
        adaptLayout()
        setNavigation()
        register()
        return binding.root
    }


    private fun adaptLayout() {
        binding.txtHeader.text = "Register"
        binding.btn.text = "Register"
        binding.txtNav.text = "Already have an account? Login"
    }


    private fun setNavigation() {
        binding.txtNav.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun register() {
        binding.btn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val insert = db?.userDao()?.insert(UserEntity(userName = binding.edtUsername.text.toString(), password = binding.edtPassword.text.toString()))
                if(insert!=-1L){
                    Snackbar.make(requireView(),"Successfully registered ",Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()

                }
            }
        }
    }
}