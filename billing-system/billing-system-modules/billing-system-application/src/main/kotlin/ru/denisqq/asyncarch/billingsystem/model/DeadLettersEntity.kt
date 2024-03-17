package ru.denisqq.asyncarch.billingsystem.model

import jakarta.persistence.Entity

@Entity
class DeadLettersEntity(
    val topic: String,
    val message: ByteArray
): AbstractEntity() {
}