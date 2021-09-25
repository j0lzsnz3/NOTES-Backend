package com.uhacorp.notes.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.uhacorp.notes.util.DateUtils
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.experimental.Accessors

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Response<T> {

    private var status: Status? = null
    var payload: T? = null
    var error: Any? = null
    var metadata: Any? = null

    fun <T> badRequest(): Response<T> {
        val response = Response<T>()
        response.status = Status.BAD_REQUEST
        return response
    }

    fun <T> ok(payload: T): Response<T> {
        val response = Response<T>()
        response.status = Status.OK
        response.payload = payload
        return response
    }

    fun <T> unauthorized(): Response<T> {
        val response = Response<T>()
        response.status = Status.UNAUTHORIZED
        return response
    }

    fun <T> validationException(): Response<T> {
        val response = Response<T>()
        response.status = Status.VALIDATION_EXCEPTION
        return response
    }

    fun <T> wrongCredentials(): Response<T> {
        val response = Response<T>()
        response.status = Status.WRONG_CREDENTIALS
        return response
    }

    fun <T> accessDenied(): Response<T> {
        val response = Response<T>()
        response.status = Status.ACCESS_DENIED
        return response
    }

    fun <T> exception(error: Exception): Response<T> {
        val response = Response<T>()
        response.status = Status.EXCEPTION
        response.error = error
        return response
    }

    fun <T> notFound(): Response<T> {
        val response = Response<T>()
        response.status = Status.NOT_FOUND
        return response
    }

    fun <T> duplicateEntity(): Response<T> {
        val response = Response<T>()
        response.status = Status.DUPLICATE_ENTITY
        return response
    }

    fun addErrorMsgToResponse(errorMessage: String, ex: Exception) {
        val error = ResponseError(
            detail = errorMessage,
            message = ex.message.toString(),
            timestamp = DateUtils.today()
        )
    }

    enum class Status {
        OK, BAD_REQUEST, UNAUTHORIZED, VALIDATION_EXCEPTION, EXCEPTION, WRONG_CREDENTIALS, ACCESS_DENIED, NOT_FOUND, DUPLICATE_ENTITY
    }

}