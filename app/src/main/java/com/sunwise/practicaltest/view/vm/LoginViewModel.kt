package com.sunwise.practicaltest.view.vm

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunwise.practicaltest.domain.models.User
import com.sunwise.practicaltest.domain.usecases.UserUseCase
import com.sunwise.practicaltest.view.utils.validWithRegex
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class LoginViewModel: ViewModel() {

    private val userUseCase: UserUseCase by lazy { UserUseCase() }

    val loadingLiveData = MutableLiveData(false)

    var onErrorOccurred: ((property: KProperty<*>, oldValue: String?, newValue: String?) -> Unit)? = null
    private var emailError: String? by Delegates.observable(null) { property: KProperty<*>, oldValue: String?, newValue: String? ->
        onErrorOccurred?.invoke(property, oldValue, newValue)
    }
    private var passwordError: String? by Delegates.observable(null) { property: KProperty<*>, oldValue: String?, newValue: String? ->
        onErrorOccurred?.invoke(property, oldValue, newValue)
    }

    fun login(user: User, isValidLogin: (success: Boolean) -> Unit){
        if(validateFields(user.email, user.password)){
            credentialsAreOk(user, isValidLogin)
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        var isValidated = true
        if (!email.validWithRegex(Patterns.EMAIL_ADDRESS.pattern())) {
            isValidated = false
            emailError = "The email is not in the correct format"
        }else emailError = null

        if (password.isEmpty()) {
            isValidated = false
            passwordError = "The password could not be empty"
        }else passwordError = null
        return isValidated
    }

    private fun credentialsAreOk(user: User, isValidLogin: (success: Boolean) -> Unit){
        userUseCase.login(user){ res ->
            res?.let {
                res.isActive = true
                userUseCase.update(res){
                    isValidLogin(true)
                }
            } ?: kotlin.run { isValidLogin(false) }
        }
    }

}