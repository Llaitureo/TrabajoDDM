package com.pasteleria.data.repository;
import com.pasteleria.data.model.Credential;

class AuthRepository (
    private val validCredential:Credential = Credential.admin
){
    fun login(username:String, password:String):Boolean {
        return username == validCredential.username && password == validCredential.password
    }
}