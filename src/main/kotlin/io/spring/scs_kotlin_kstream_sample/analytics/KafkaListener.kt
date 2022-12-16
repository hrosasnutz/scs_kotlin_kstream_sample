package io.spring.scs_kotlin_kstream_sample.analytics

import io.spring.scs_kotlin_kstream_sample.dto.customer.Customer
import mu.KotlinLogging
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaListener {
    
    private val logger = KotlinLogging.logger {  }
    
    @Bean
    fun countCustomerPerTypes(): (KStream<ByteArray, Customer>) -> KStream<ByteArray, Long> {
        return {
            it.map { _, value ->  KeyValue(value.type.name.toByteArray(), 1L) }
                .groupByKey(Grouped.with(Serdes.ByteArray(), Serdes.Long()))
                .count()
                .toStream()
                .peek { key, value -> logger.info { "Total $value customers for $key type." } }
        }
    }
    
    // @Bean
    // fun countCustomerPerTypes(): java.util.function.Function<KStream<ByteArray, Customer>, KStream<ByteArray, Long>> {
    //     return java.util.function.Function { 
    //             it.map { _, value ->  KeyValue(value.type.name.toByteArray(), 1L) }
    //             .groupByKey(Grouped.with(Serdes.ByteArray(), Serdes.Long()))
    //             .count()
    //             .toStream()
    //             .peek { key, value -> logger.info { "Total $value customers for $key type." } } 
    //     }
    // }
}