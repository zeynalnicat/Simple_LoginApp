package com.matrix.android_104_android.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.matrix.android_104_android.R
import com.matrix.android_104_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.txtName.text = activity?.getSharedPreferences("UserRegistration", Context.MODE_PRIVATE)?.getString("username","N/A")
        logOut()
        return binding.root
    }

    private fun logOut(){
        binding.btn.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences("UserRegistration", Context.MODE_PRIVATE)
            val editor = sharedPreferences!!.edit()
            editor.remove("password")
            editor.remove("username")
            val commit = editor.commit()
            if(commit){
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }else {
                Snackbar.make(requireView(),"There was an error while log out",Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}