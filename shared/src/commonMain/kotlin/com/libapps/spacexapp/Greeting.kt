package com.libapps.spacexapp

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}