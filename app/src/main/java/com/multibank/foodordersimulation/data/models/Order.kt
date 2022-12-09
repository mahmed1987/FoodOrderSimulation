package com.multibank.foodordersimulation.data.models

import java.time.LocalDateTime

data class Order(val id: Long, val status: Status, val createdAt: LocalDateTime) {
  companion object {
    fun empty() = Order(-1, Status.New, LocalDateTime.now())
  }

  enum class Status {
    New,
    Preparing,
    Ready,
    Delivered
  }
}
