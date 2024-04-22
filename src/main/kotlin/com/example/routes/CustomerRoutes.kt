package com.example.routes

import com.example.dao.dao
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            call.respond(mapOf("customer" to dao.allCustomers()))
        }
        get("{id?}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(mapOf("customer" to dao.customer(id)))
        }
        post {
            val formParameters = call.receiveParameters()
            val firstName = formParameters.getOrFail("firstName")
            val lastName = formParameters.getOrFail("lastName")
            val email = formParameters.getOrFail("email")
            val customer = dao.addNewCustomer(firstName, lastName, email)
            call.respond(mapOf("customer" to customer))
        }
        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            when (formParameters.getOrFail("_action")) {
                "update" -> {
                    val firstName = formParameters.getOrFail("firstName")
                    val lastName = formParameters.getOrFail("lastName")
                    val email = formParameters.getOrFail("email")
                    dao.editCustomer(id, firstName, lastName, email)
                    call.respondRedirect("/customer/$id")
                }
                "delete" -> {
                    dao.deleteCustomer(id)
                    call.respondRedirect("/customer")
                }
            }
        }
    }
}