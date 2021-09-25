package com.uhacorp.notes.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:custom.properties")
class PropertiesConfig {
    @Autowired
    private lateinit var env: Environment

    fun getConfigValue(configKey: String): String? {
        return env.getProperty(configKey)
    }
}