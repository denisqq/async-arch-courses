package ru.denisqq.asyncarch.tasktracker.repository

import org.springframework.data.repository.CrudRepository

fun <T, ID> CrudRepository<T, ID>.findOne(id: ID): T = findById(id!!).orElseThrow()
