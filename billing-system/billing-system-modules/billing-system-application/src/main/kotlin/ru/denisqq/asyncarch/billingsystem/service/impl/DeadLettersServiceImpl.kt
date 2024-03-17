package ru.denisqq.asyncarch.billingsystem.service.impl

import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.billingsystem.service.DeadLettersService

@Service
class DeadLettersServiceImpl : DeadLettersService {


    override fun create(message: Any) {
        //TODO вероятно для реально создания события понадобиться больше мета информации
    }
}