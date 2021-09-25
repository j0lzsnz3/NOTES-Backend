package com.uhacorp.notes.exception

import com.uhacorp.notes.config.PropertiesConfig
import com.uhacorp.notes.model.EntityType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.text.MessageFormat
import java.util.*

@Component
class AppException(
    @Autowired
    private val propertiesConfig: PropertiesConfig
) {
    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    fun throwException(messageTemplate: String, vararg args: String): RuntimeException {
        return RuntimeException(format(messageTemplate, *args))
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    fun throwException(
        entityType: EntityType,
        exceptionType: ExceptionType,
        vararg args: String
    ): RuntimeException {
        val messageTemplate: String = getMessageTemplate(entityType, exceptionType)
        return throwException(exceptionType, messageTemplate, *args)
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    fun throwExceptionWithId(
        entityType: EntityType,
        exceptionType: ExceptionType,
        id: Int,
        vararg args: String
    ): RuntimeException {
        val messageTemplate: String = getMessageTemplate(
            entityType,
            exceptionType
        ) + "." + id.toString()
        return throwException(exceptionType, messageTemplate, *args)
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType, messageTemplate and args
     *
     * @param entityType
     * @param exceptionType
     * @param messageTemplate
     * @param args
     * @return
     */
    fun throwExceptionWithTemplate(
        entityType: EntityType,
        exceptionType: ExceptionType,
        messageTemplate: String,
        vararg args: String
    ): RuntimeException {
        return throwException(exceptionType, messageTemplate, *args)
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    private fun throwException(
        exceptionType: ExceptionType,
        messageTemplate: String,
        vararg args: String
    ): RuntimeException {
        if (ExceptionType.ENTITY_NOT_FOUND == exceptionType) {
            return EntityNotFoundException(format(messageTemplate, *args))
        } else if (ExceptionType.DUPLICATE_ENTITY == exceptionType) {
            return DuplicateEntityException(
                format(messageTemplate, *args)
            )
        }
        return RuntimeException(format(messageTemplate, *args))
    }

    private fun getMessageTemplate(entityType: EntityType, exceptionType: ExceptionType): String {
        return entityType.name.plus(".").plus(exceptionType.value).toLowerCase()
    }

    private fun format(template: String, vararg args: String): String {
        val templateContent = Optional.ofNullable(propertiesConfig.getConfigValue(template))
        return if (templateContent.isPresent) {
            MessageFormat.format(templateContent.get(), args as Any)
        } else {
            String.format(template, args as Any)
        }
    }

    class EntityNotFoundException(message: String) : RuntimeException(message)

    class DuplicateEntityException(message: String) : RuntimeException(message)
}

enum class ExceptionType(var value: String) {
    ENTITY_NOT_FOUND("not.found"),
    DUPLICATE_ENTITY("duplicate"),
    ENTITY_EXCEPTION("exception")
}