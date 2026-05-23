package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull

class GrowGreenRepository(private val dao: GrowGreenDao) {

    val userProfile: Flow<UserProfile?> = dao.getUserProfile()
    val allTransactions: Flow<WalletTransaction?> = dao.getAllTransactions().map { list -> list.firstOrNull() }
    val allTransactionsList: Flow<List<WalletTransaction>> = dao.getAllTransactions()
    val allReferrals: Flow<List<Referral>> = dao.getAllReferrals()
    val allProducts: Flow<List<Product>> = dao.getAllProducts()
    val allNotifications: Flow<List<Notification>> = dao.getAllNotifications()
    val unreadNotificationsCount: Flow<Int> = dao.getUnreadNotificationsCount()

    suspend fun initDefaultData() {
        // 1. Prepopulate products if they don't exist
        if (dao.getProductsCount() == 0) {
            dao.insertProduct(
                Product(
                    name = "Dry Neem Powder (200g)",
                    description = "100% natural, premium skin purifier and hair treatment made from hand-picked organic neem leaves.",
                    category = "SKINCARE",
                    sizeDetails = "200g",
                    price = 5.0,
                    isNewArrival = true,
                    imageType = "neem"
                )
            )
            dao.insertProduct(
                Product(
                    name = "Multani Mitti",
                    description = "Pure organic Indian healing bentonite clay powder for glowing skin, deep cleansing, and tan removal.",
                    category = "SKINCARE",
                    sizeDetails = "500g",
                    price = 5.0,
                    isNewArrival = false,
                    imageType = "clay_500"
                )
            )
            dao.insertProduct(
                Product(
                    name = "Multani Mitti (1kg)",
                    description = "Bulky family-pack of premium organic fuller's earth clay for consistent weekly skincare treatments.",
                    category = "SKINCARE",
                    sizeDetails = "1kg",
                    price = 9.0,
                    isNewArrival = false,
                    imageType = "clay_1000"
                )
            )
            dao.insertProduct(
                Product(
                    name = "Pure Organic Aloe Gel (150g)",
                    description = "Soothes skin blemishes, treats mild sunburns, and moisturizes dry skin without any sticky feeling.",
                    category = "SKINCARE",
                    sizeDetails = "150g",
                    price = 6.0,
                    isNewArrival = false,
                    imageType = "aloe"
                )
            )
        }

        // 2. Prepopulate default user if not exists
        val currentProfile = dao.getUserProfileSynchronously()
        if (currentProfile == null) {
            dao.insertUserProfile(
                UserProfile(
                    id = 1,
                    name = "John Doe",
                    email = "ved60236@gmail.com",
                    isVerified = true,
                    inviteCode = "GREEN77",
                    walletBalance = 180.0,
                    personalGains = 25.0,
                    teamIncome = 0.0,
                    referralIncome = 5.0,
                    multiLevelIncome = 2.50,
                    groupTaskIncome = 0.0,
                    unlockedEcoMaster = false
                )
            )

            // Pre-add some default sample transactions and referrals to match screenshots
            dao.insertTransaction(
                WalletTransaction(
                    type = "DEPOSIT",
                    amount = 180.0,
                    method = "CARD / BANK"
                )
            )

            // Let's prepopulate a level 1 and level 2 referral
            dao.insertReferral(
                Referral(
                    name = "Yash Sharma",
                    email = "yash.sharma@eco.com",
                    level = 1,
                    incomeAmount = 5.0,
                    status = "Active"
                )
            )
            dao.insertReferral(
                Referral(
                    name = "Dev Kumar",
                    email = "dev.kumar@eco.com",
                    level = 2,
                    incomeAmount = 2.50,
                    status = "Active"
                )
            )

            // Pre-add some notifications
            dao.insertNotification(
                Notification(
                    title = "Welcome to GrowGreen!",
                    message = "Earn high ROI through sustainable investments and referring Eco-conscious members."
                )
            )
        }
    }

    suspend fun addFunds(amount: Double, method: String): Boolean {
        if (amount <= 0.0) return false
        val profile = dao.getUserProfileSynchronously() ?: return false
        val updated = profile.copy(walletBalance = profile.walletBalance + amount)
        dao.insertUserProfile(updated)

        dao.insertTransaction(
            WalletTransaction(type = "DEPOSIT", amount = amount, method = method)
        )

        dao.insertNotification(
            Notification(
                title = "Deposit Successful",
                message = "deposit of €" + String.format("%.2f", amount) + " added successfully via " + method
            )
        )
        return true
    }

    suspend fun withdrawFunds(amount: Double): Boolean {
        if (amount <= 0.0) return false
        val profile = dao.getUserProfileSynchronously() ?: return false
        if (profile.walletBalance < amount) return false

        val updated = profile.copy(walletBalance = profile.walletBalance - amount)
        dao.insertUserProfile(updated)

        dao.insertTransaction(
            WalletTransaction(type = "WITHDRAWAL", amount = amount, method = "BANK")
        )

        dao.insertNotification(
            Notification(
                title = "Withdrawal Requested",
                message = "Withdrawal of €" + String.format("%.2f", amount) + " is being processed to your bank."
            )
        )
        return true
    }

    suspend fun buyProduct(product: Product): Boolean {
        val profile = dao.getUserProfileSynchronously() ?: return false
        if (profile.walletBalance < product.price) return false

        val updatedProfile = profile.copy(walletBalance = profile.walletBalance - product.price)
        dao.insertUserProfile(updatedProfile)

        dao.insertTransaction(
            WalletTransaction(type = "PURCHASE", amount = product.price, method = "WALLET_SHOPS")
        )

        dao.insertNotification(
            Notification(
                title = "Order Placed",
                message = "Purchased ${product.name} for €" + String.format("%.2f", product.price) + " from Eco Market."
            )
        )
        return true
    }

    suspend fun addReferral(name: String, email: String, level: Int): Boolean {
        if (name.isBlank() || email.isBlank()) return false
        val profile = dao.getUserProfileSynchronously() ?: return false

        val incomeAmount = if (level == 1) 5.0 else 2.50
        val updatedRefIncome = if (level == 1) profile.referralIncome + 5.0 else profile.referralIncome
        val updatedMultiIncome = if (level == 2) profile.multiLevelIncome + 2.50 else profile.multiLevelIncome
        val newPersonalGains = profile.personalGains + incomeAmount

        val referralsCount = (dao.getAllReferrals().firstOrNull()?.size ?: 0) + 1
        val isEcoMaster = referralsCount >= 10

        val updatedProfile = profile.copy(
            referralIncome = updatedRefIncome,
            multiLevelIncome = updatedMultiIncome,
            personalGains = newPersonalGains,
            unlockedEcoMaster = isEcoMaster
        )
        dao.insertUserProfile(updatedProfile)

        dao.insertReferral(
            Referral(name = name, email = email, level = level, incomeAmount = incomeAmount)
        )

        dao.insertNotification(
            Notification(
                title = "New Referral Joined",
                message = "$name joined as a Level $level Eco Member. You gained €" + String.format("%.2f", incomeAmount)
            )
        )
        return true
    }

    suspend fun markAllNotificationsAsRead() {
        dao.markAllAsRead()
    }

    suspend fun resetUserSession(name: String, email: String) {
        val defaultProfile = UserProfile(
            id = 1,
            name = name,
            email = email,
            isVerified = true,
            inviteCode = "GREEN77",
            walletBalance = 180.00,
            personalGains = 25.00,
            teamIncome = 0.00,
            referralIncome = 5.0,
            multiLevelIncome = 2.50,
            groupTaskIncome = 0.00,
            unlockedEcoMaster = false
        )
        dao.insertUserProfile(defaultProfile)
        dao.insertNotification(
            Notification(
                title = "Session Initialized",
                message = "Switched user profile to $name successfully."
            )
        )
    }
}
