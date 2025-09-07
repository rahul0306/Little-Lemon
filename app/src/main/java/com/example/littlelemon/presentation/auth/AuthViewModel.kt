package com.example.littlelemon.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _nav = MutableSharedFlow<AuthNavEvent>()
    val nav = _nav.asSharedFlow()

    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.UsernameChange -> _state.value = _state.value.copy(username = event.v, message = null)
            is AuthEvent.EmailChange -> _state.value = _state.value.copy(email = event.v, message = null)
            is AuthEvent.PasswordChange -> _state.value = _state.value.copy(password = event.v, message = null)
            AuthEvent.ToggleMode -> {
                _state.value = _state.value.copy(
                    isSignUp = !_state.value.isSignUp,
                    email = "", password = "", message = null
                )
            }
            AuthEvent.Submit -> submit()
        }
    }

    fun currentUser() = repository.currentUser()
    fun signOut() = repository.signOut()

    private fun submit() = viewModelScope.launch {
        val s = _state.value
        if (s.username.isBlank() || s.password.isBlank() || (s.isSignUp && s.email.isBlank())){
            flash("Please fill required fields")
            return@launch
        }
        _state.value = s.copy(loading = true, message = null)
        try {
            if (s.isSignUp){
                repository.signUp(s.username.trim(),s.email.trim(),s.password)
                flash("Verification link sent to ${s.email}")
                _state.value = _state.value.copy(isSignUp = false, loading = false, password = "")
            }else{
                repository.signInWithUsername(username = s.username.trim(), password = s.password)
                _state.value = _state.value.copy(loading = false)
                _nav.emit(AuthNavEvent.GoHome)
            }
        }catch (t: Throwable){
            _state.value = _state.value.copy(loading = false)
            flash(t.message ?:"Something went wrong")
        }
    }

    private fun flash(msg: String) {
        _state.value = _state.value.copy(message = msg)
    }

}