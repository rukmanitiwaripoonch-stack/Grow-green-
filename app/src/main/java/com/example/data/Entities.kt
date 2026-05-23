package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1, // Global single user state
    val name: String,
    val email: String,
    val isVerified: Boolean = true,
    val inviteCode: String = "GREEN77",
    val walletBalance: Double = 180.0,
    val personalGains: Double = 25.0,
    val teamIncome: Double = 0.0,
    val referralIncome: Double = 5.0,
    val multiLevelIncome: Double = 2.50,
    val groupTaskIncome: Double = 0.0,
    val unlockedEcoMaster: Boolean = false
)

@Entity(tableName = "wallet_transactions")
data class WalletTransaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "DEPOSIT", "WITHDRAWAL", "PURCHASE"
    val amount: Double,
    val method: String, // "CARD / BANK", "UPI"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "referrals")
data class Referral(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val level: Int, // 1 or 2
    val incomeAmount: Double,
    val status: String = "Active", // "Active", "Pending"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val category: String, // "SKINCARE", "HERBAL"
    val sizeDetails: String, // "200g", "500g", "1kg"
    val price: Double,
    val isNewArrival: Boolean = false,
    val imageType: String = "leaf" // "neem", "clay_500", "clay_1000" or simple identifier
)

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
