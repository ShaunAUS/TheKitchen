package com.example.kotlinPractice.utils

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.stereotype.Component


@Component
class ModelMapper {
    companion object {
        fun getMapper() : ModelMapper {
            val modelMapper = ModelMapper()
            modelMapper.configuration
                    .setMatchingStrategy(MatchingStrategies.STRICT).isSkipNullEnabled = true
            return modelMapper
        }
    }
}