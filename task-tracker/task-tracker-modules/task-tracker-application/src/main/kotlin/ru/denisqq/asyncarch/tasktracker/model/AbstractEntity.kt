package ru.denisqq.asyncarch.tasktracker.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.domain.Persistable
import java.time.OffsetDateTime
import java.util.UUID

@MappedSuperclass
abstract class AbstractEntity : Persistable<UUID> {

    @Id
    @UuidGenerator
    private var id: UUID? = null

    @CreationTimestamp
    var createdAt: OffsetDateTime? = null

    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null

    @CreatedBy
    var createdBy: String? = null

    override fun getId(): UUID? {
        return this.id
    }

    override fun isNew(): Boolean {
        return id == null
    }
}