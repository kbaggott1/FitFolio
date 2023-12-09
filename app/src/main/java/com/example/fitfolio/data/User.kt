package com.example.fitfolio.data

//Class representing a single FitFolio user
class User(var email: String, var password: String) {
    constructor() : this("", "") {
        // Provide default values or leave properties uninitialized based on your requirements
    }
}