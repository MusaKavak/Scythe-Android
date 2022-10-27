package com.musa_kavak.scythe.views.login.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.musa_kavak.scythe.R
import com.musa_kavak.scythe.databinding.FragmentLoginWithUserNameBinding
import com.musa_kavak.scythe.models.user.UserLoginBody
import com.musa_kavak.scythe.models.user.UserLoginResponse
import com.musa_kavak.scythe.retrofit.DataAccess
import com.musa_kavak.scythe.views.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginWithUserNameFragment : Fragment() {
    private var _bd : FragmentLoginWithUserNameBinding? = null
    private val bd get() = _bd!!
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bd = FragmentLoginWithUserNameBinding.inflate(inflater,container,false)
        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        sharedPreferences = requireActivity().getSharedPreferences("UserInfo",MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        setListeners()
    }

    private fun setListeners() {
        bd.btnLogin.setOnClickListener {
            val userLoginBody = UserLoginBody(bd.etUserName.text.toString(),bd.etPassword.text.toString())
            login(userLoginBody)
            bd.cvLoading.visibility = View.VISIBLE
            bd.cvLoading.visibility = View.VISIBLE
        }
        bd.txtBtnCreateAccount.setOnClickListener{
            navController.navigate(R.id.action_loginWithUserNameFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bd =null
    }

    private fun  showToast(message:String?){
        Toast.makeText(activity,    message, Toast.LENGTH_SHORT).show()
    }

    private fun login(data: UserLoginBody){
        DataAccess().loginWithUserName(data).enqueue(object :Callback<UserLoginResponse>{

            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                bd.cvLoading.visibility = View.INVISIBLE
                bd.cvLoading.visibility = View.INVISIBLE
                val userLoginResponse: UserLoginResponse? = response.body()
                if(userLoginResponse==null){
                    showToast("Some Error Occurred")
                }else{
                    checkUserStatus(userLoginResponse)
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                showToast(t.message)
            }

        })
    }

    private fun checkUserStatus(response: UserLoginResponse){
        when(response.status){
            "User Not Found" -> showToast("User Not Found")
            "Wrong Password" -> showToast("Wrong Password")
            "Login Successful" -> {
                sharedPreferencesEditor.apply {
                    this.putInt("UserId",response.user.id)
                    this.putString("UserFirstName",response.user.firstName)
                    this.putString("UserLastName",response.user.lastName)
                    this.putString("UserUserName",response.user.userName)
                    this.putString("UserEmail",response.user.email)
                    this.commit()
                }
                val intent = Intent(activity,HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}