package com.pasteleria.data.model

data class Credential (val username:String, val password:String){
    companion object{
        val admin= Credential(username="admin", password = "123")
    }//fin objeto
}//fin data