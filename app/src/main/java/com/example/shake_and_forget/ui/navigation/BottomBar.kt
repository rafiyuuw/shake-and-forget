package com.example.shake_and_forget.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.example.shake_and_forget.ui.theme.ElectricGrape
import com.example.shake_and_forget.ui.theme.PureWhite
import com.example.shake_and_forget.ui.theme.SecondaryInk

@Composable
fun CustomBottomNavigationBar(
    currentRoute: String?,
    onNavigate: (Screen) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Bottom bar container with 5 equal-weight columns for perfect symmetry
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = PureWhite,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Slot 0: Home
                NavItemSlot(
                    screen = Screen.Home,
                    isSelected = currentRoute == Screen.Home.route,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onNavigate(Screen.Home)
                    },
                    modifier = Modifier.weight(1f)
                )

                // Slot 1: Quick Pick
                NavItemSlot(
                    screen = Screen.QuickDecision,
                    isSelected = currentRoute == Screen.QuickDecision.route,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onNavigate(Screen.QuickDecision)
                    },
                    modifier = Modifier.weight(1f)
                )

                // Slot 2: Center gap for Shake FAB
                Box(modifier = Modifier.weight(1f))

                // Slot 3: My Lists
                NavItemSlot(
                    screen = Screen.MyLists,
                    isSelected = currentRoute == Screen.MyLists.route,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onNavigate(Screen.MyLists)
                    },
                    modifier = Modifier.weight(1f)
                )

                // Slot 4: History
                NavItemSlot(
                    screen = Screen.History,
                    isSelected = currentRoute == Screen.History.route,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onNavigate(Screen.History)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Center Floating Shake Action Button - Perfectly aligned over Slot 2
        Surface(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onNavigate(Screen.ShakeToDecide)
            },
            modifier = Modifier
                .offset(y = (-20).dp)
                .size(60.dp)
                .shadow(elevation = 8.dp, shape = CircleShape),
            shape = CircleShape,
            color = ElectricGrape
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Vibration,
                    contentDescription = "Shake Now",
                    tint = PureWhite,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
private fun NavItemSlot(
    screen: Screen,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) ElectricGrape else Color.Transparent)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            screen.icon?.let { iconVector ->
                Icon(
                    imageVector = iconVector,
                    contentDescription = screen.title,
                    tint = if (isSelected) PureWhite else SecondaryInk,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
