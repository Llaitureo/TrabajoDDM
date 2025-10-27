package com.pasteleria.data.model

data class Credential (val username:String, val password:String){
    companion object{
        val admin= Credential(username="admin", password = "123")
        val cliente1= Credential(username = "sopapo", password = "1234")
    }//fin objeto
}//fin data