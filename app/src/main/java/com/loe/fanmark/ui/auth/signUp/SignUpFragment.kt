package com.loe.fanmark.ui.auth.signUp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.loe.fanmark.R
import com.loe.fanmark.databinding.FragmentSignUpBinding
import com.loe.fanmark.network.AuthTransferObjects
import com.loe.fanmark.network.AuthTransferObjects.SignUpObject
import com.loe.fanmark.ui.MainActivity
import com.loe.fanmark.ui.auth.login.AuthAction
import com.loe.fanmark.ui.auth.login.AuthApiStatus
import com.loe.fanmark.ui.auth.login.AuthViewModel
import com.loe.fanmark.util.Tools
import www.sanju.motiontoast.MotionToast

class SignUpFragment : Fragment() {


    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AuthViewModel.Factory(activity.application))[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false)

//        Observe on status

        viewModel.status.observe(viewLifecycleOwner, {
            if (it == AuthApiStatus.DONE) {
                viewModel.displayToast("account successfully created...", MotionToast.TOAST_SUCCESS)
                viewModel.completeAuth()
                startActivity(Intent(requireContext() , MainActivity::class.java))
                activity?.finish()
            }
        })

//        Observe on toast

        viewModel.showToast.observe(viewLifecycleOwner, {
            it?.let {
                Tools.displayMT(requireActivity(), it[0], it[1])
                viewModel.displayToastComplete()
            }
        })


        binding.submitButton.setOnClickListener {

//            TODO complete validation

            val body = SignUpObject(
                FullName = binding.editTextFullName.text.toString(),
                MobileNumber = binding.ediTextMobileNumber.text.toString(),
                Username = binding.ediTextUsername.text.toString(),
                Password = binding.editTextPassword.text.toString()
            )

            viewModel.submitAuthData(AuthAction.SIGN_UP, body)


        }

        return binding.root
    }


}