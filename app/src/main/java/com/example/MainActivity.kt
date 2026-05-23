package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.DonutLarge
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalMall
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.data.AppDatabase
import com.example.data.GrowGreenRepository
import com.example.ui.*
import com.example.ui.theme.GrowGreenBackground
import com.example.ui.theme.GrowGreenDarkSlate
import com.example.ui.theme.GrowGreenPrimary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        try {
            // Configure system bars for an immersive, premium full-screen experience
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Immersive system bar configuration skipped in onCreate", e)
        }

        // Initialize SQLite Room DB
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = GrowGreenRepository(database.dao())
        val viewModelFactory = GrowGreenViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[GrowGreenViewModel::class.java]

        setContent {
            MyApplicationTheme {
                GrowGreenAppContainer(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ConfigureImmersiveSystemBars() {
    val view = androidx.compose.ui.platform.LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? android.app.Activity)?.window
            if (window != null) {
                try {
                    val controller = WindowCompat.getInsetsController(window, view)
                    controller.hide(WindowInsetsCompat.Type.systemBars())
                    controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                } catch (e: Exception) {
                    android.util.Log.e("GrowGreen", "Immersive system bar SideEffect configuration skipped", e)
                }
            }
        }
    }
}

@Composable
fun GrowGreenAppContainer(viewModel: GrowGreenViewModel) {
    // Collect reactive database state
    val userProfileState by viewModel.userProfile.collectAsState()

    // Attach robust immersive bar configuration
    ConfigureImmersiveSystemBars()

    if (!viewModel.isSplashFinished) {
        SplashScreen()
    } else if (!viewModel.isLoggedIn) {
        LoginScreen(viewModel = viewModel)
    } else {
        // Logged in shell container
        val profile = userProfileState
        if (profile == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrowGreenBackground),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator(color = GrowGreenPrimary)
            }
            return
        }

        var activeTab by remember { mutableStateOf(0) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars
                ) {
                    val tabs = listOf(
                        NavigationItemData("HOME", Icons.Filled.Home, Icons.Outlined.Home),
                        NavigationItemData("WALLET", Icons.Filled.AccountBalanceWallet, Icons.Outlined.AccountBalanceWallet),
                        NavigationItemData("EARNINGS", Icons.Filled.DonutLarge, Icons.Outlined.DonutLarge),
                        NavigationItemData("SHOP", Icons.Filled.LocalMall, Icons.Outlined.LocalMall),
                        NavigationItemData("PROFILE", Icons.Filled.Person, Icons.Outlined.Person)
                    )

                    tabs.forEachIndexed { index, tab ->
                        val isSelected = activeTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { activeTab = index },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.activeIcon else tab.inactiveIcon,
                                    contentDescription = "${tab.label} tab",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = GrowGreenPrimary,
                                unselectedIconColor = Color(0xFF8C96A3),
                                selectedTextColor = GrowGreenPrimary,
                                unselectedTextColor = Color(0xFF8C96A3),
                                indicatorColor = Color(0xFFE8FAF1)
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrowGreenBackground)
                    .padding(innerPadding)
            ) {
                when (activeTab) {
                    0 -> HomeScreen(profile = profile, viewModel = viewModel)
                    1 -> WalletScreen(profile = profile, viewModel = viewModel)
                    2 -> EarningsScreen(profile = profile, viewModel = viewModel)
                    3 -> ShopScreen(viewModel = viewModel)
                    4 -> ProfileScreen(profile = profile, viewModel = viewModel)
                }

                // Global dialogs overlays floating on top of any active screen (Image 8, 2, 1 overlays)
                if (viewModel.showNotificationsPanel) {
                    NotificationsDialogOverlay(viewModel = viewModel)
                }
                if (viewModel.showAddReferralDialog) {
                    AddMockReferralDialogOverlay(viewModel = viewModel)
                }
                if (viewModel.showEditProfileDialog) {
                    EditProfileDialogOverlay(viewModel = viewModel)
                }
            }
        }
    }
}

data class NavigationItemData(
    val label: String,
    val activeIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val inactiveIcon: androidx.compose.ui.graphics.vector.ImageVector
)
