package io.spring.scs_kotlin_kstream_sample.dto.customer

enum class Type {
    
    INTERNAL, EXTERNAL, LOYAL, POTENTIAL, INTERMEDIATE, FORMER, UNKNOWN;
    
    companion object {
        
        fun getByNumber(int: Int): Type {
            return when(int) {
                1 -> INTERNAL
                2 -> EXTERNAL
                3 -> LOYAL
                4 -> POTENTIAL
                5 -> INTERMEDIATE
                6 -> FORMER
                else -> UNKNOWN
            }
        }
        
    }
}