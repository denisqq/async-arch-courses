package ru.denisqq.asyncarch.billingsystem.analytics.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TopManagementDashboardRepository(
    private val jdbcTemplate: JdbcTemplate
) {


//    sum(assigned task fee) - sum(completed task amount)
    fun mostExpensiveTask() {
        //TODO не успел
//        sum(assigned task fee) - sum(completed task amount)
//        jdbcTemplate.query("""
//            select sum(t.assign_task_cost) from task t
//
//
//        """.trimIndent())
    }
}