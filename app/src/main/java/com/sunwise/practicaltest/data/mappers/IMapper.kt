package com.sunwise.practicaltest.data.mappers

interface IMapper<From, To> {
    fun map(from: From?): To
}