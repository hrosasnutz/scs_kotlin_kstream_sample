package io.spring.scs_kotlin_kstream_sample.producer

import io.github.serpro69.kfaker.Faker
import io.spring.scs_kotlin_kstream_sample.dto.customer.Customer
import io.spring.scs_kotlin_kstream_sample.dto.customer.Type
import mu.KotlinLogging
import org.springframework.boot.ApplicationRunner
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Configuration
class CustomerProducer(
    private val streamBridge: StreamBridge
) {
    
    private val faker = Faker()
    
    private val logger = KotlinLogging.logger {  }
    
    fun randomCustomer(): Customer {
        return Customer(
            uuid = UUID.randomUUID(),
            name = faker.name.name(), 
            type = Type.getByNumber(faker.random.nextInt(7)),
            active = faker.random.nextBoolean(),
            country = faker.address.countryCode()
        )
    }
    
    @Bean
    fun customerGenerator(): ApplicationRunner {
        return ApplicationRunner {
            val customer = randomCustomer()
            Executors.newSingleThreadScheduledExecutor().schedule({
                logger.debug { "customer created: $customer" }
                streamBridge.send("customerGenerator-out-0", MessageBuilder.withPayload(customer)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, customer.uuid.toString().toByteArray())
                    .build()
                )},
                10L,
                TimeUnit.SECONDS
            )
            
        }
    }
}