package com.find.domain.mapper

interface Mapper<Input, Output> {
  fun map(input: Input): Output
}