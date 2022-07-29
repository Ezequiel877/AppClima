package com.example.appclima.utils

import java.lang.Exception

sealed class getStatus< out T> {
    class Loading<out T>:getStatus<T>()
    data class Succes<out T>(val data:T):getStatus<T>()
    data class Failure(val exception: Exception):getStatus<Nothing>()

}