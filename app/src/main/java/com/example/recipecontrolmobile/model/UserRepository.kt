package com.example.recipecontrolmobile.model

object UserRepository {
    private val users = mutableListOf<User>()

    init {
        // Inicializar con los 4 registros demo
        users.add(User("demo1@demo.cl", "123456", "Demo Usuario 1"))
        users.add(User("demo2@demo.cl", "123456", "Demo Usuario 2"))
        users.add(User("demo3@demo.cl", "123456", "Demo Usuario 3"))
        users.add(User("demo4@demo.cl", "123456", "Demo Usuario 4"))
    }

    fun addUser(user: User) {
        if (users.none { it.email == user.email }) {
            users.add(user)
        }
    }

    fun validateUser(email: String, password: String): User? {
        return users.find { it.email == email && it.password == password }
    }
}
