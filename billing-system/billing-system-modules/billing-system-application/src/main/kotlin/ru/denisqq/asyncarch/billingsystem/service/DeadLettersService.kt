package ru.denisqq.asyncarch.billingsystem.service

interface DeadLettersService {
    fun create(message: Any)
}