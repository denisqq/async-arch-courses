---
title: Первое ДЗ
---

[ExcaliDraw - Ссылка](https://excalidraw.com/#json=I5Dd3gZO58pUV1fnkfj5q,djDJjCn7oD6bnGiMakKcbQ)

Домены:
- (1) Домен тасков
- (2) Система авторизации
- (3) Домен платежей и счетов
- (4) Аналитика

CUD события:
- Регистрация пользователя
  - Producer: (2)
  - Consumer: (1,3)
- Создание задачи
  - Producer: (1)
  - Consumer: (3,4)
- Закрытие задачи
  - Producer: (1)
  - Consumer: (3,4)
- Списание денег
  - Producer: (3)
  - Consumer: (4)
- Начисление денег
  - Producer: (3)
  - Consumer: (4)


![Event Storming](./assets/first-home-work/async-arch-2.svg)
