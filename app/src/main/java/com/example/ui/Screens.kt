package com.example.ui

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.Notification
import com.example.data.Product
import com.example.data.Referral
import com.example.data.UserProfile
import com.example.data.WalletTransaction
import kotlinx.coroutines.delay
import com.example.ui.theme.*

// ==========================================
// 1. SPLASH SCREEN (Image 5)
// ==========================================
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrowGreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Soft rounded green plate logo (Image 5)
            Box(
                modifier = Modifier
                    .size(175.dp)
                    .clip(RoundedCornerShape(48.dp))
                    .background(GrowGreenMintBackground)
                    .padding(36.dp),
                contentAlignment = Alignment.Center
            ) {
                // Drawing organic Leaf Outline dynamically on canvas (Image 5 logo)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    val leafPath = Path().apply {
                        // Start point bottom left of leaf
                        moveTo(width * 0.15f, height * 0.85f)
                        // Bottom curve to top-right tip
                        quadraticTo(width * 0.5f, height * 0.95f, width * 0.85f, height * 0.45f)
                        quadraticTo(width * 0.95f, height * 0.15f, width * 0.85f, height * 0.15f)
                        // Top curve back to bottom left
                        quadraticTo(width * 0.55f, height * 0.05f, width * 0.15f, height * 0.45f)
                        quadraticTo(width * 0.05f, height * 0.75f, width * 0.15f, height * 0.85f)
                        close()
                    }

                    // Draw leaf contour
                    drawPath(
                        path = leafPath,
                        color = Color.White,
                        style = Stroke(width = 12f)
                    )

                    // Draw leaf center vein (Image 5)
                    val veinPath = Path().apply {
                        moveTo(width * 0.15f, height * 0.85f)
                        quadraticTo(width * 0.45f, height * 0.55f, width * 0.85f, height * 0.15f)
                        // branch 1
                        moveTo(width * 0.45f, height * 0.55f)
                        quadraticTo(width * 0.65f, height * 0.45f, width * 0.75f, height * 0.42f)
                        // branch 2
                        moveTo(width * 0.35f, height * 0.65f)
                        quadraticTo(width * 0.55f, height * 0.60f, width * 0.60f, height * 0.58f)
                    }

                    drawPath(
                        path = veinPath,
                        color = Color.White,
                        style = Stroke(width = 8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // GROWGREEN Styled Text: Deep Slate + Emerald Green segments (Image 5)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "GROW",
                    color = GrowGreenDarkSlate,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = (-1).sp
                )
                Text(
                    text = "GREEN",
                    color = GrowGreenPrimary,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = (-1).sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "THE FUTURE OF ROI",
                color = GrowGreenTextGray,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // TRUSTED BY 2 LAKH+ PEOPLE Badge (Image 5)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .border(1.dp, GrowGreenMintBackground, RoundedCornerShape(30.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .testTag("trusted_badge")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Small vibrant green bullet point
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(GrowGreenPrimary)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "TRUSTED BY 2 LAKH+ PEOPLE",
                        color = GrowGreenPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // Bottom accent green line resembling standard progress bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .width(220.dp)
                .height(6.dp)
                .themeProgressStyle()
        )
    }
}

// ==========================================
// 2. LOGIN / CREDENTIAL SCREEN (Image 7)
// ==========================================
@Composable
fun LoginScreen(
    viewModel: GrowGreenViewModel,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrowGreenBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .verticalScrollEnabled()
        ) {
            // Leaf icon inside circle (Image 7)
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(GrowGreenPrimary)
                    .padding(18.dp),
                contentAlignment = Alignment.Center
            ) {
                // Inline organic Vector drawing
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val leafPath = Path().apply {
                        moveTo(size.width * 0.15f, size.height * 0.85f)
                        quadraticTo(size.width * 0.5f, size.height * 0.95f, size.width * 0.85f, size.height * 0.45f)
                        quadraticTo(size.width * 0.95f, size.height * 0.15f, size.width * 0.85f, size.height * 0.15f)
                        quadraticTo(size.width * 0.55f, size.height * 0.05f, size.width * 0.15f, size.height * 0.45f)
                        quadraticTo(size.width * 0.05f, size.height * 0.75f, size.width * 0.15f, size.height * 0.85f)
                        close()
                    }
                    drawPath(path = leafPath, color = Color.White, style = Stroke(width = 8f))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Welcome Back",
                color = GrowGreenDarkSlate,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "SUSTAINABLE INVESTMENTS",
                color = GrowGreenTextGray,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(26.dp))

            // Firebase Error panel replica (Image 7)
            val loginErr = viewModel.loginError
            if (loginErr != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFF2F2))
                        .border(1.dp, Color(0xFFFFD1D1), RoundedCornerShape(16.dp))
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                        .testTag("login_error_box")
                ) {
                    Text(
                        text = loginErr,
                        color = Color(0xFFD61C1C),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Email input field (Image 7)
            OutlinedTextField(
                value = viewModel.loginEmail,
                onValueChange = { viewModel.loginEmail = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("username_input"),
                placeholder = { Text("E-mail Address", color = GrowGreenTextGray) },
                singleLine = true,
                colors = textFieldColors(),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password input field (Image 7)
            OutlinedTextField(
                value = viewModel.loginPassword,
                onValueChange = { viewModel.loginPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("password_input"),
                placeholder = { Text("Password", color = GrowGreenTextGray) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = GrowGreenPrimary
                        )
                    }
                },
                colors = textFieldColors(),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // LOGIN ACCOUNT heavy navy button (Image 7)
            Button(
                onClick = { viewModel.onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(8.dp, RoundedCornerShape(30.dp), ambientColor = GrowGreenDarkSlate)
                    .testTag("submit_button"),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GrowGreenDarkSlate)
            ) {
                Text(
                    text = "LOGIN ACCOUNT",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Or register prompt
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Divider(modifier = Modifier.weight(1f), color = GrowGreenLightBorder)
                Text(
                    text = " OR ",
                    color = GrowGreenTextGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
                Divider(modifier = Modifier.weight(1f), color = GrowGreenLightBorder)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quick autofill buttons to facilitate fast user review
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        viewModel.loginEmail = "ved60236@gmail.com"
                        viewModel.loginPassword = "password"
                        viewModel.onLoginClick()
                    }
                ) {
                    Text("Auto-login: John Doe", color = GrowGreenPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                TextButton(
                    onClick = {
                        viewModel.loginEmail = "ved60236@gmail.com"
                        viewModel.loginPassword = "wrongpassword"
                        viewModel.onLoginClick()
                    }
                ) {
                    Text("Fail login", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

// ==========================================
// 3. HOME SCREEN PANEL (Image 8 & 9)
// ==========================================
@Composable
fun HomeScreen(
    profile: UserProfile,
    viewModel: GrowGreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScrollEnabled()
            .padding(18.dp)
    ) {
        // Welcome and Notification Bell Header (Image 8 & Image 9)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (profile.name == "John Doe") "GREEN MEMBER" else "WELCOME BACK",
                    color = GrowGreenPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = profile.name,
                    color = GrowGreenDarkSlate,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            // Notification Bell with Badge (Image 8 & 9)
            val unreadCount by viewModel.unreadNotificationsCount.collectAsState()
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .clickable { viewModel.showNotificationsPanel = true }
                    .testTag("notification_bell")
            ) {
                IconButton(
                    onClick = { viewModel.showNotificationsPanel = true },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications list",
                        tint = GrowGreenDarkSlate,
                        modifier = Modifier.size(24.dp)
                    )
                }
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // YOUR GREEN BALANCE Big Card (Image 9)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(GrowGreenDarkSlate)
                .padding(24.dp)
                .testTag("green_balance_card")
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "YOUR GREEN BALANCE",
                    color = GrowGreenTextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Balance currency formatting "180,00 €"
                    Text(
                        text = String.format("%.2f", profile.walletBalance).replace(".", ",") + " €",
                        color = Color.White,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black
                    )

                    // Rounded Diagonal arrow button (Image 8)
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(GrowGreenPrimary)
                            .clickable { viewModel.onQuickAddFunds(100.0) }
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = "Quick add investments",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Interactive Add and Withdraw Actions inside the card (Image 9)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { viewModel.onQuickAddFunds(45.0) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GrowGreenPrimary),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text("+ ADD", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    Button(
                        onClick = { viewModel.onQuickWithdrawFunds(45.0) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2D3D)),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text("— WITHDRAW", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Split boxes showing "PERSONAL GAINS" & "TEAM INCOME" side by side (Image 9)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoSplitBox(
                title = "PERSONAL GAINS",
                value = String.format("%.2f", profile.personalGains).replace(".", ",") + " €",
                modifier = Modifier.weight(1f)
            )

            InfoSplitBox(
                title = "TEAM INCOME",
                value = String.format("%.2f", profile.teamIncome).replace(".", ",") + " €",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // CAMPAIGN BANNER: "Wear the Eco-Mission" (Image 8)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF121F2F), Color(0xFF0C141E))
                    )
                )
                .padding(24.dp)
                .testTag("campaign_banner")
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // T-Shirt badge icon (Image 8)
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1E2D3D))
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Checkroom,
                            contentDescription = "Cotton eco t-shirt campaign",
                            tint = GrowGreenPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Green "EXCLUSIVE" badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(GrowGreenPrimary)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "EXCLUSIVE",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Heading "Wear the Eco-Mission"
                Row {
                    Text(
                        text = "Wear the ",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Eco-Mission",
                        color = GrowGreenPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Refer 10 friends and claim your limited edition organic cotton brand apparel.",
                    color = GrowGreenTextGray,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun InfoSplitBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(26.dp))
            .background(Color.White)
            .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(26.dp))
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = title,
                color = GrowGreenTextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                color = GrowGreenDarkSlate,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

// ==========================================
// 4. WALLET SCREEN (Image 3)
// ==========================================
@Composable
fun WalletScreen(
    profile: UserProfile,
    viewModel: GrowGreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScrollEnabled()
            .padding(18.dp)
    ) {
        // Top row with Title and Balance green chip (Image 3)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Eco Wallet",
                color = GrowGreenDarkSlate,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif
            )

            // Current balance badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(GrowGreenPrimary)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = String.format("%.2f", profile.walletBalance).replace(".", ",") + " €",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Dual Actions below: ADD BALANCE and WITHDRAW (Image 3)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /* Set mode */ },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "+ ADD BALANCE",
                    color = GrowGreenPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Button(
                onClick = { /* Set mode */ },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "— WITHDRAW",
                    color = GrowGreenTextGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // White deposit container card (Image 3)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White)
                .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(28.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Tab Selection Bar (Image 3)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(GrowGreenBackground)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val tabs = listOf("CARD / BANK", "UPI")
                    tabs.forEach { tab ->
                        val isActive = tab == viewModel.activeDepositTab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isActive) Color.White else Color.Transparent)
                                .shadow(if (isActive) 1.dp else 0.dp)
                                .clickable { viewModel.activeDepositTab = tab }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tab,
                                color = if (isActive) GrowGreenPrimary else GrowGreenTextGray,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "ENTER AMOUNT (MIN €45)",
                    color = GrowGreenTextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Big Amount text and currency indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "€",
                        color = GrowGreenPrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    TextField(
                        value = viewModel.depositAmount,
                        onValueChange = { input ->
                            // Allow only numeric decimals
                            if (input.all { it.isDigit() || it == '.' }) {
                                viewModel.depositAmount = input
                            }
                        },
                        placeholder = { Text("0.00", color = Color(0xFFD1D8E0)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            color = GrowGreenPrimary,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Start
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier
                            .width(160.dp)
                            .testTag("deposit_input_field")
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // CONFIRM DEPOSIT Green Button (Image 3)
                Button(
                    onClick = { viewModel.onConfirmDeposit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .testTag("confirm_deposit_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = GrowGreenPrimary),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = "CONFIRM DEPOSIT",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

         // Balance Success Dialog
         val walletToast = viewModel.showWalletSuccessToast
         if (walletToast != null) {
             LaunchedEffect(walletToast) {
                 delay(3000)
                 viewModel.clearWalletToast()
             }
             Box(
                 modifier = Modifier
                     .fillMaxWidth()
                     .animateContentSize()
                     .clip(RoundedCornerShape(16.dp))
                     .background(Color(0xFFE3FCEF))
                     .border(1.dp, GrowGreenPrimary, RoundedCornerShape(16.dp))
                     .padding(14.dp)
             ) {
                 Text(
                     text = walletToast,
                     color = GrowGreenPrimary,
                     fontSize = 12.sp,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Center,
                     modifier = Modifier.fillMaxWidth()
                 )
             }
         }

        Spacer(modifier = Modifier.height(20.dp))

        // Transaction logs history header
        Text(
            text = "Transaction History",
            color = GrowGreenDarkSlate,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        val actualHistoryState by viewModel.allTransactions.collectAsState()

        if (actualHistoryState.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No transactions logged yet.", color = GrowGreenTextGray, fontSize = 12.sp)
            }
        } else {
            actualHistoryState.take(5).forEach { tx ->
                TransactionRowItem(tx)
            }
        }
    }
}

@Composable
fun TransactionRowItem(tx: WalletTransaction) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = tx.type + " via " + tx.method,
                    color = GrowGreenDarkSlate,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Processed Successfully",
                    color = GrowGreenTextGray,
                    fontSize = 11.sp
                )
            }

            Text(
                text = (if (tx.type == "WITHDRAWAL" || tx.type == "PURCHASE") "- " else "+ ") + String.format("%.2f", tx.amount) + " €",
                color = if (tx.type == "WITHDRAWAL" || tx.type == "PURCHASE") Color(0xFFD61C1C) else GrowGreenPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

// ==========================================
// 5. EARNINGS SCREEN (Image 2 & 6)
// ==========================================
@Composable
fun EarningsScreen(
    profile: UserProfile,
    viewModel: GrowGreenViewModel,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScrollEnabled()
            .padding(18.dp)
    ) {
        // Team Network Header Titles (Image 2)
        Text(
            text = "Team Network",
            color = GrowGreenDarkSlate,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Build your team, grow together.",
            color = GrowGreenTextGray,
            fontSize = 13.sp,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Emerald Invite Green Box Panel (Image 2)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(GrowGreenPrimary)
                .padding(20.dp)
                .testTag("invite_code_card")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Info block
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Small circular share icon with accent background
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF0D9551))
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share invite code",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "YOUR INVITE CODE",
                            color = Color(0xFFA5F3C2),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = profile.inviteCode,
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }

                // Inline round floating share button (Image 2)
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF038244))
                        .clickable { ClipboardHelper.shareText(context, "Join GrowGreen with code " + profile.inviteCode) }
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Quick share",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // COPY CODE heavy active button inside (Image 2)
            Button(
                onClick = {
                    clipboardManager.setText(AnnotatedString(profile.inviteCode))
                    Toast.makeText(context, "Invite Code Copied to Clipboard!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = "Copy code",
                        tint = GrowGreenPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "COPY CODE",
                        color = GrowGreenPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Income Breakdown title (Image 2)
        Text(
            text = "Income Breakdown",
            color = GrowGreenDarkSlate,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Income rows layout (Image 6)
        IncomeLineItem(
            title = "Referral Income",
            value = String.format("%.2f", profile.referralIncome).replace(".", ",") + " €",
            icon = Icons.Filled.Group,
            iconBg = Color(0xFFF1F3FE),
            iconColor = Color(0xFF3864F5)
        )

        IncomeLineItem(
            title = "Multi-Level Income",
            value = String.format("%.2f", profile.multiLevelIncome).replace(".", ",") + " €",
            icon = Icons.Filled.AccountTree,
            iconBg = Color(0xFFE8FAF1),
            iconColor = GrowGreenPrimary
        )

        IncomeLineItem(
            title = "Group Task Income",
            value = String.format("%.2f", profile.groupTaskIncome).replace(".", ",") + " €",
            icon = Icons.Filled.PlaylistAddCheck,
            iconBg = Color(0xFFFBF4FE),
            iconColor = Color(0xFF8B36F0)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Refer 10 more friends unlock target card (Image 6)
        val listRef by viewModel.allReferrals.collectAsState()
        val countRemaining = (10 - listRef.size).coerceAtLeast(0)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .background(Color.White)
                .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(26.dp))
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Trophy icon cup in round border (Image 6)
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, GrowGreenLightBorder, CircleShape)
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EmojiEvents,
                        contentDescription = "Eco-master bonus achievement",
                        tint = GrowGreenPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(18.dp))

                Column {
                    Text(
                    text = "Refer $countRemaining more friends",
                    color = GrowGreenDarkSlate,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "TO UNLOCK \"ECO-MASTER\" BONUS",
                    color = GrowGreenTextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Add Mock Referral floating simulator button to let the user test calculations
        Button(
            onClick = { viewModel.showAddReferralDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("add_referral_button"),
            colors = ButtonDefaults.buttonColors(containerColor = GrowGreenDarkSlate),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add referral", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Simulate New Referral", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun IncomeLineItem(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(iconBg)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = title,
                    color = GrowGreenDarkSlate,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Text(
                text = value,
                color = GrowGreenDarkSlate,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

// ==========================================
// 6. SHOP MARKET SCREEN (Image 4)
// ==========================================
@Composable
fun ShopScreen(
    viewModel: GrowGreenViewModel,
    modifier: Modifier = Modifier
) {
    val products by viewModel.allProducts.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScrollEnabled()
            .padding(18.dp)
    ) {
        Text(
            text = "Eco Market",
            color = GrowGreenDarkSlate,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(18.dp))

        // NEW ARRIVAL Main Banner card (Image 4)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF111E2E))
                .padding(22.dp)
                .testTag("featured_product_banner")
        ) {
            Column {
                Text(
                    text = "NEW ARRIVAL",
                    color = GrowGreenPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Dry Neem Powder (200g)",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "ONLY €5.00",
                    color = GrowGreenPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val neem = products.firstOrNull { it.isNewArrival }
                        if (neem != null) {
                            viewModel.onBuyProductClick(neem)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GrowGreenPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("SHOP NOW", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }

            // Simple organic Leaf contour vector elements inside corner background representation
            Canvas(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
            ) {
                // Background watermarked aesthetic element (Image 4)
                val paintBrush = Brush.linearGradient(
                    colors = listOf(Color(0x3300A859), Color(0x0500A859))
                )
                drawCircle(
                    brush = paintBrush,
                    radius = size.width * 0.45f
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Success dialog panel
        val shopToast = viewModel.showShopSuccessToast
        if (shopToast != null) {
            LaunchedEffect(shopToast) {
                delay(3000)
                viewModel.clearShopToast()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE8FAF1))
                    .border(1.dp, GrowGreenPrimary, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = shopToast,
                    color = GrowGreenPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Subgrid headers
        Text(
            text = "Herbal Selections",
            color = GrowGreenDarkSlate,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Product Catalog mapping list in rows (Clean and robust layout to prevent grid scrolling bugs in dialogs)
        products.filter { !it.isNewArrival }.forEach { product ->
            ProductCatalogCardItem(product = product, onBuyClick = { viewModel.onBuyProductClick(product) })
        }
    }
}

@Composable
fun ProductCatalogCardItem(
    product: Product,
    onBuyClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(24.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Soft mint icon (Image 4)
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(GrowGreenMintBackground)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Eco,
                        contentDescription = "Eco product",
                        tint = GrowGreenPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = product.category,
                        color = GrowGreenPrimary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = product.name,
                        color = GrowGreenDarkSlate,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Size: " + product.sizeDetails,
                        color = GrowGreenTextGray,
                        fontSize = 11.sp
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = String.format("%.2f", product.price).replace(".", ",") + " €",
                    color = GrowGreenDarkSlate,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                // Lightning Buy round Button with high contrast (Image 4 bottom right)
                IconButton(
                    onClick = onBuyClick,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(GrowGreenDarkSlate)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bolt,
                        contentDescription = "Quick Buy with lightning",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// ==========================================
// 7. PROFILE SETTINGS SCREEN (Image 1)
// ==========================================
@Composable
fun ProfileScreen(
    profile: UserProfile,
    viewModel: GrowGreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScrollEnabled()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Avatar component stack (Image 1)
        Box(
            modifier = Modifier.size(130.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // Soft mint square box with avatar (Image 1)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(Color(0xFFE1F8ED))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "User profile picture placeholder",
                    tint = GrowGreenPrimary,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Floating dark settings cog round wheel overlap (Image 1)
            Box(
                modifier = Modifier
                    .offset(x = 6.dp, y = 6.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(GrowGreenDarkSlate)
                    .clickable {
                        viewModel.editNameInput = profile.name
                        viewModel.editEmailInput = profile.email
                        viewModel.showEditProfileDialog = true
                    }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Edit profile information",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Names (Image 1)
        Text(
            text = profile.name,
            color = GrowGreenDarkSlate,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = profile.email,
            color = GrowGreenTextGray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Badge pill 1: VERIFIED ECO MEMBER (Image 1)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE8FAF1))
                .border(1.dp, GrowGreenMintBackground, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "VERIFIED ECO MEMBER",
                color = GrowGreenPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Badge pill 2: MY CODE: #GREEN77 (Image 1)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFEDF2F7))
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MY CODE: ",
                    color = GrowGreenTextGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "#" + profile.inviteCode,
                    color = GrowGreenPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Account details card menu launcher (Image 1)
        ProfileMenuItemCard(
            title = "Account Details",
            icon = Icons.Filled.Person,
            onRowClick = {
                viewModel.editNameInput = profile.name
                viewModel.editEmailInput = profile.email
                viewModel.showEditProfileDialog = true
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuItemCard(
            title = "Sign Out Session",
            icon = Icons.Filled.Logout,
            onRowClick = { viewModel.onLogout() }
        )
    }
}

@Composable
fun ProfileMenuItemCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onRowClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(Color.White)
            .border(1.dp, GrowGreenLightBorder, RoundedCornerShape(26.dp))
            .clickable { onRowClick() }
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GrowGreenBackground)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Menu icon",
                        tint = GrowGreenTextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = title,
                    color = GrowGreenDarkSlate,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Navigate detail menu",
                tint = Color(0xFFCCD1D9),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ==========================================
// 8. GLOBAL UTILS, MODALS & OVERLAYS
// ==========================================

@Composable
fun NotificationsDialogOverlay(
    viewModel: GrowGreenViewModel
) {
    val list by viewModel.allNotifications.collectAsState()
    LaunchedEffect(viewModel.showNotificationsPanel) {
        if (viewModel.showNotificationsPanel) {
            viewModel.markNotificationsAsRead()
        }
    }

    Dialog(onDismissRequest = { viewModel.showNotificationsPanel = false }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Eco Logs Notifications",
                        color = GrowGreenDarkSlate,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    IconButton(onClick = { viewModel.showNotificationsPanel = false }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close notifications dialog")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (list.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No notifications yet.", color = GrowGreenTextGray, fontSize = 13.sp)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 350.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(list) { notification ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(GrowGreenBackground)
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = notification.title,
                                        color = GrowGreenPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = notification.message,
                                        color = GrowGreenDarkSlate,
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddMockReferralDialogOverlay(
    viewModel: GrowGreenViewModel
) {
    Dialog(onDismissRequest = { viewModel.showAddReferralDialog = false }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Invite Referral Simulation",
                        color = GrowGreenDarkSlate,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    IconButton(onClick = { viewModel.showAddReferralDialog = false }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close insert referral overlay")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.newReferralName,
                    onValueChange = { viewModel.newReferralName = it },
                    label = { Text("Friend's Full Name", color = GrowGreenTextGray) },
                    singleLine = true,
                    colors = textFieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = viewModel.newReferralEmail,
                    onValueChange = { viewModel.newReferralEmail = it },
                    label = { Text("E-mail Address", color = GrowGreenTextGray) },
                    singleLine = true,
                    colors = textFieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "SELECT LEVEL OF REPRICING",
                    color = GrowGreenTextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        1 to "Level 1 (+€5.00)",
                        2 to "Level 2 (+€2.50)"
                    ).forEach { (lvl, desc) ->
                        val isSelected = viewModel.newReferralLevel == lvl
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) GrowGreenPrimary else GrowGreenBackground)
                                .clickable { viewModel.newReferralLevel = lvl }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = desc,
                                color = if (isSelected) Color.White else GrowGreenTextGray,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = { viewModel.onAddReferralClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = GrowGreenPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("ADD TO TEAM NETWORK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun EditProfileDialogOverlay(
    viewModel: GrowGreenViewModel
) {
    Dialog(onDismissRequest = { viewModel.showEditProfileDialog = false }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit Account Details",
                        color = GrowGreenDarkSlate,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    IconButton(onClick = { viewModel.showEditProfileDialog = false }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close edit profile details")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = viewModel.editNameInput,
                    onValueChange = { viewModel.editNameInput = it },
                    label = { Text("Display Name", color = GrowGreenTextGray) },
                    singleLine = true,
                    colors = textFieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = viewModel.editEmailInput,
                    onValueChange = { viewModel.editEmailInput = it },
                    label = { Text("Email Address", color = GrowGreenTextGray) },
                    singleLine = true,
                    colors = textFieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Prepopulate tips to match screenshots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            viewModel.editNameInput = "Adhyaksh ji yash"
                            viewModel.editEmailInput = "ved60236@gmail.com"
                        }
                    ) {
                        Text("Role: Adhyaksh ji yash (Image 8)", color = GrowGreenPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    TextButton(
                        onClick = {
                            viewModel.editNameInput = "John Doe"
                            viewModel.editEmailInput = "ved60236@gmail.com"
                        }
                    ) {
                        Text("Role: John Doe (Image 1/9)", color = GrowGreenPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { viewModel.updateProfile(viewModel.editNameInput, viewModel.editEmailInput) },
                    colors = ButtonDefaults.buttonColors(containerColor = GrowGreenPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("SAVE CHANGES", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ==========================================
// AESTHETIC COMPOSABLE STYLES EXTENSIONS & DRAWABLES
// ==========================================

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = GrowGreenDarkSlate,
    unfocusedTextColor = GrowGreenDarkSlate,
    focusedBorderColor = GrowGreenPrimary,
    unfocusedBorderColor = GrowGreenLightBorder,
    focusedLabelColor = GrowGreenPrimary,
    unfocusedLabelColor = GrowGreenTextGray,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White
)

fun Modifier.themeProgressStyle(): Modifier = this.drawBehind {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF00A859), Color(0xFF0F9F5B), Color(0xFFCEF5E4))
    )
    drawRoundRect(
        brush = gradient,
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
    )
}

// Utility extension to enable dynamic vertical scrolling gracefully
@Composable
fun Modifier.verticalScrollEnabled(): Modifier {
    val scrollState = androidx.compose.foundation.rememberScrollState()
    return this.verticalScroll(scrollState)
}

object ClipboardHelper {
    fun shareText(context: android.content.Context, text: String) {
        val sendIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            putExtra(android.content.Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = android.content.Intent.createChooser(sendIntent, null).apply {
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            android.util.Log.e("GrowGreen", "Native share chooser failed, falling back to clipboard", e)
            try {
                val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("GrowGreen Share", text)
                clipboard.setPrimaryClip(clip)
                android.widget.Toast.makeText(context, "Copied share text to clipboard!", android.widget.Toast.LENGTH_LONG).show()
            } catch (ex: Exception) {
                android.util.Log.e("GrowGreen", "Sharing clipboard fallback also failed", ex)
            }
        }
    }
}
