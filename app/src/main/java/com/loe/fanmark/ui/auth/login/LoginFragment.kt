package com.loe.fanmark.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loe.fanmark.R
import com.loe.fanmark.databinding.FragmentLoginBinding
import com.loe.fanmark.network.AuthTransferObjects.LoginBodyObject
import com.loe.fanmark.util.Tools
import www.sanju.motiontoast.MotionToast

class LoginFragment : Fragment() {


    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AuthViewModel.Factory(activity.application))[AuthViewModel::class.java]
    }

    private var isUsernameActive: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)


        binding.viewmodel = viewModel

//        Observe on Username/MobileNumber editText

        viewModel.isUsernameActive.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it) {
                    binding.apply {
                        textUsernameOrMobile.text = resources.getString(R.string.username)
                        editTextUsernameOrMobile.hint = resources.getString(R.string.username)
                        wrapFiled.setImageResource(R.drawable.ic_person)
                    }
                    isUsernameActive = true
                }
                else {
                    binding.apply {
                        textUsernameOrMobile.text = resources.getString(R.string.mobile)
                        editTextUsernameOrMobile.hint = resources.getString(R.string.mobile)
                        wrapFiled.setImageResource(R.drawable.ic_mobile)
                    }
                    isUsernameActive = false
                }
            }
        })

//        Observe on toast

        viewModel.showToast.observe(viewLifecycleOwner, {
            it?.let {
                Tools.displayMT(requireActivity(), it[0], it[1])
                viewModel.displayToastComplete()
            }
        })

//        Observe on navigation

        viewModel.navigateSignUp.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                viewModel.navigateSignUpFragmentComplete()
            }
        })

//        Observe on complete

        viewModel.status.observe(viewLifecycleOwner, {
            if (it == AuthApiStatus.DONE){
                viewModel.displayToast("successfully logged in...", MotionToast.TOAST_SUCCESS)
                viewModel.completeAuth()
            }
        })

        binding.submitButton.setOnClickListener {

            when (isUsernameActive) {
                true -> {
                    binding.apply {

                        when (editTextUsernameOrMobile.text.length) {
                            in -1 until 4 -> {
                                viewmodel?.displayToast(
                                    "Username is too short...",
                                    MotionToast.TOAST_WARNING)
                                return@setOnClickListener
                            }
                            in 21 until Int.MAX_VALUE -> {
                                viewmodel?.displayToast(
                                    "Username is too long...",
                                    MotionToast.TOAST_WARNING)
                                return@setOnClickListener
                            }
                            else -> {
                                when (editTextPassword.text.length) {
                                    in -1 until 7 -> {
                                        viewmodel?.displayToast(
                                            "Password too short...",
                                            MotionToast.TOAST_WARNING)
                                        return@setOnClickListener
                                    }
                                    in 31 until Int.MAX_VALUE -> {
                                        viewmodel?.displayToast(
                                            "Password too long...",
                                            MotionToast.TOAST_WARNING)
                                        return@setOnClickListener
                                    }
                                }
                            }
                        }
                    }
                }
                false -> {
                    binding.apply {
                        when (binding.editTextUsernameOrMobile.text.length) {
                            11 -> {
                                when (editTextPassword.text.length) {
                                    in -1 until 7 -> {
                                        Tools.displayMT(requireActivity(),
                                            "Password too short...",
                                            MotionToast.TOAST_WARNING)
                                        return@setOnClickListener
                                    }
                                    in 31 until Int.MAX_VALUE -> {
                                        Tools.displayMT(requireActivity(),
                                            "Password too long...",
                                            MotionToast.TOAST_WARNING)
                                        return@setOnClickListener
                                    }
                                }
                            }
                            else -> {
                                Tools.displayMT(requireActivity(),
                                    "Mobile number should only contain 11 characters...",
                                    MotionToast.TOAST_WARNING)
                                return@setOnClickListener
                            }
                        }
                    }
                }
            }

            val body = LoginBodyObject(
                MobileNumber = if (isUsernameActive) null else binding.editTextUsernameOrMobile.text.toString(),
                Username = if (isUsernameActive) binding.editTextUsernameOrMobile.text.toString() else null,
                Password = binding.editTextPassword.text.toString()
            )
            Tools.l(body.toString())
            viewModel.submitAuthData(AuthAction.LOGIN, body)
        }

        return binding.root
    }

}
/* TODO
* create sign up page
* complete viewmodel class
* show loading
* save info in shared preferences
* finally navigate to main activity
* try to make the file more beautiful
*/