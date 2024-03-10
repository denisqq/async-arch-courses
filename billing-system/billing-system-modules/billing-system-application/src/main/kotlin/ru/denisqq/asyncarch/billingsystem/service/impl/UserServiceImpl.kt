package ru.denisqq.asyncarch.billingsystem.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.billingsystem.model.DAILY_BILLING_CYCLES_MACROS
import ru.denisqq.asyncarch.billingsystem.model.User
import ru.denisqq.asyncarch.billingsystem.repository.BillingCycleRepository
import ru.denisqq.asyncarch.billingsystem.repository.UserRepository
import ru.denisqq.asyncarch.billingsystem.service.UserService
import java.math.BigDecimal

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val billingCycleRepository: BillingCycleRepository
) : UserService {

    @Transactional
    override fun createUser(userRegisteredEvent: UserRegistered) {
        val billingCycle = billingCycleRepository.findByMacros(DAILY_BILLING_CYCLES_MACROS)
        userRepository.save(
            User(
                role = userRegisteredEvent.role,
                userSsoId = userRegisteredEvent.userSsoId,
                billingCycle = billingCycle,
                balance = BigDecimal.ZERO
            )
        )
    }

    override fun findBySsoId(userSsoId: String): User {
        return userRepository.findByUserSsoId(userSsoId)
    }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun updateBalance(userSsoId: String, balance: BigDecimal) {

    }
}