package com.multibank.foodordersimulation.data.models

import androidx.annotation.StringRes
import com.multibank.foodordersimulation.R
import java.time.LocalDateTime

data class Order(val id: String, val status: Status, val createdAt: LocalDateTime) {
  companion object {
    fun empty() = Order("-1", Status.New, LocalDateTime.now())
  }

  enum class Status(@StringRes val text: Int) {
    New(R.string.order_status_new),
    Preparing(R.string.order_status_preparation),
    Ready(R.string.order_status_ready),
    Delivered(R.string.order_status_delivered)
  }
}
