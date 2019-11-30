package com.lrm.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lrm.R
import com.lrm.databinding.LoginFragmentBinding
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.lrm.dashboard.DashboardFragment
import com.lrm.model.LoginUser
import com.lrm.model.User
import com.lrm.retrofit.ApiInterface
import javax.inject.Inject

class LoginFragment : Fragment() {


    lateinit var binding : LoginFragmentBinding
    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this);

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.setLoginViewModel(viewModel)


        viewModel.getUser().observe(this, object : Observer<User> {
            override fun onChanged(@Nullable loginUser: User) {
                if (TextUtils.isEmpty(loginUser.id)) {
                    binding.userTextInputLayout.setError("Please enter user name")
                    binding.passwordEditText.requestFocus()
                } else if (TextUtils.isEmpty(loginUser.password)) {
                    binding.passwordTextInputLayout.setError("Please enter password")
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.login()
                }
            }
        })

        viewModel.getLoginUser().observe(this, object : Observer<LoginUser> {
            override fun onChanged(t: LoginUser?) {
                binding.progressBar.visibility = View.GONE
                if(t == null) {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
                } else{

                    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                    sharedPreferences.edit().putString("token", t.data.get(0).access_token)

                    activity?.supportFragmentManager?.beginTransaction()!!
                        .replace(R.id.container, DashboardFragment.newInstance(t.data.get(0).access_token))
                        .commitNow()
                }
            }
        })
    }
}
