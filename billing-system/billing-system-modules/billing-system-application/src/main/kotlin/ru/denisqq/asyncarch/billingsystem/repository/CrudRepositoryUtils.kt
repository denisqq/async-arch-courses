package ru.denisqq.asyncarch.billingsystem.repository

import org.springframework.data.repository.CrudRepository

fun <T, ID> CrudRepository<T, ID>.findOne(id: ID): T = findById(id!!).orElseThrow()
