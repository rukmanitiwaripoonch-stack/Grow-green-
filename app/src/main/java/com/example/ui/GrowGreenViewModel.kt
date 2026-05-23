package com.example.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.GrowGreenRepository
import com.example.data.Notification
import com.example.data.Product
import com.example.data.Referral
import com.example.data.UserProfile
import com.example.data.WalletTransaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GrowGreenViewModel(private val repository: GrowGreenRepository) : ViewModel() {

    // 1. Observe state flow from DB with lifecycle
    val userProfile: StateFlow<UserProfile?> = repository.userProfile
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val allTransactions: StateFlow<List<WalletTransaction>> = repository.allTransactionsList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allReferrals: StateFlow<List<Referral>> = repository.allReferrals
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allProducts: StateFlow<List<Product>> = repository.allProducts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allNotifications: StateFlow<List<Notification>> = repository.allNotifications
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val unreadNotificationsCount: StateFlow<Int> = repository.unreadNotificationsCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // 2. Navigation & UI state
    var isSplashFinished by mutableStateOf(false)
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    var loginEmail by mutableStateOf("ved60236@gmail.com")
    var loginPassword by mutableStateOf("password")
    var loginError by mutableStateOf<String?>(null)

    // Wallet States
    var depositAmount by mutableStateOf("")
    var activeDepositTab by mutableStateOf("CARD / BANK") // "CARD / BANK" or "UPI"
    var showWalletSuccessToast by mutableStateOf<String?>(null)

    // Earnings Dialogs
    var showAddReferralDialog by mutableStateOf(false)
    var newReferralName by mutableStateOf("")
    var newReferralEmail by mutableStateOf("")
    var newReferralLevel by mutableStateOf(1) // 1 or 2

    // Shop States
    var showShopSuccessToast by mutableStateOf<String?>(null)

    // Profile Settings Dialogs / Screens
    var showEditProfileDialog by mutableStateOf(false)
    var editNameInput by mutableStateOf("")
    var editEmailInput by mutableStateOf("")

    // Notifications Panel
    var showNotificationsPanel by mutableStateOf(false)

    init {
        // Initialize default DB items
        viewModelScope.launch {
            repository.initDefaultData()
            // Set up splash screen delay
            delay(1500)
            isSplashFinished = true
        }
    }

    fun onLoginClick() {
        if (loginEmail.trim() == "ved60236@gmail.com" && loginPassword == "password") {
            isLoggedIn = true
            loginError = null
        } else {
            // Replicate error from Image 7
            loginError = "FIREBASE: ERROR (AUTH/INVALID-CREDENTIAL)."
        }
    }

    fun onLogout() {
        isLoggedIn = false
        loginPassword = ""
        loginError = null
    }

    fun onConfirmDeposit() {
        val amt = depositAmount.toDoubleOrNull()
        if (amt == null || amt < 45.0) {
            showWalletSuccessToast = "Minimum deposit amount is €45"
            return
        }

        viewModelScope.launch {
            val success = repository.addFunds(amt, activeDepositTab)
            if (success) {
                showWalletSuccessToast = "Deposited €" + String.format("%.2f", amt) + " successfully via " + activeDepositTab
                depositAmount = ""
            } else {
                showWalletSuccessToast = "Deposit failed."
            }
        }
    }

    fun onQuickAddFunds(amt: Double) {
        viewModelScope.launch {
            val success = repository.addFunds(amt, "QUICK ADD")
            if (success) {
                showWalletSuccessToast = "Added €" + String.format("%.2f", amt) + " to your Eco balance."
            }
        }
    }

    fun onQuickWithdrawFunds(amt: Double) {
        viewModelScope.launch {
            val success = repository.withdrawFunds(amt)
            if (success) {
                showWalletSuccessToast = "Withdrawal €" + String.format("%.2f", amt) + " requested."
            } else {
                showWalletSuccessToast = "Insufficient balance!"
            }
        }
    }

    fun onBuyProductClick(product: Product) {
        viewModelScope.launch {
            val success = repository.buyProduct(product)
            if (success) {
                showShopSuccessToast = "Purchased ${product.name} successfully!"
            } else {
                showShopSuccessToast = "Order failed. Insufficient Eco Wallet balance!"
            }
        }
    }

    fun onAddReferralClick() {
        if (newReferralName.isBlank() || newReferralEmail.isBlank()) {
            return
        }
        viewModelScope.launch {
            val success = repository.addReferral(newReferralName, newReferralEmail, newReferralLevel)
            if (success) {
                showAddReferralDialog = false
                newReferralName = ""
                newReferralEmail = ""
            }
        }
    }

    fun markNotificationsAsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
        }
    }

    fun updateProfile(name: String, email: String) {
        viewModelScope.launch {
            repository.resetUserSession(name, email)
            showEditProfileDialog = false
        }
    }

    fun clearWalletToast() {
        showWalletSuccessToast = null
    }

    fun clearShopToast() {
        showShopSuccessToast = null
    }
}

class GrowGreenViewModelFactory(private val repository: GrowGreenRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GrowGreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GrowGreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
