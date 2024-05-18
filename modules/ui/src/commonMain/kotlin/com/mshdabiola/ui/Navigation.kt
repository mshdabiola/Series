package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.icon.cbtIcons
import com.mshdabiola.designsystem.icon.cbtNavigator
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.designsystem.icon.settingIcons
import com.mshdabiola.designsystem.icon.settingNavigator
import com.mshdabiola.designsystem.icon.settingRoute

@Composable
fun CommonNavigation(
    modifier: Modifier = Modifier,
    currentNavigation: String = mainRoute[0],
    onCreate: () -> Unit = {},
    showLong: Boolean = true,
    onNavigate: (String) -> Unit = {},

    ) {
    val color = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface, // LocalBackgroundTheme.current.color,
        shape = RoundedCornerShape(0.dp),

        ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(8.dp)
                .verticalScroll(state = rememberScrollState()),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocalLibrary, "Logo")
                Text(
                    "Cbt",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            Spacer(Modifier.height(32.dp))
            if (showLong) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Main")
                    cbtNavigator
                        .forEachIndexed { index, navigator ->
                            NavigationDrawerItem(
                                selected = currentNavigation.contains(cbtRoute[index]),
                                label = { Text(navigator) },
                                onClick = { onNavigate(cbtRoute[index]) },
                                colors = color,
                                icon = { Icon(cbtIcons[index], navigator) },
                            )
                        }
                }
            }

            Spacer(Modifier.height(64.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                settingNavigator
                    .forEachIndexed { index, navigator ->
                        NavigationDrawerItem(
                            selected = currentNavigation.contains(settingRoute[index]),
                            label = { Text(navigator) },
                            onClick = { onNavigate(settingRoute[index]) },
                            colors = color,
                            icon = { Icon(settingIcons[index], navigator) },
                        )
                    }
            }
            Spacer(Modifier.height(8.dp))

            HorizontalDivider()
            Spacer(Modifier.height(8.dp))

            ProfileCard()
        }
    }
}

@Composable
fun CommonRail(
    modifier: Modifier = Modifier,
    currentNavigation: String = mainRoute[0],
    onCreate: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
) {
    NavigationRail(modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(state = rememberScrollState()),
        ) {
            Icon(Icons.Default.LocalLibrary, "Logo")

            Spacer(Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text("Main")
                cbtNavigator
                    .forEachIndexed { index, navigator ->
                        NavigationRailItem(
                            selected = currentNavigation.contains(cbtRoute[index]),
//                            label = { Text(navigator) },
                            onClick = { onNavigate(cbtRoute[index]) },
                            alwaysShowLabel = false,
                            icon = { Icon(cbtIcons[index], navigator) },
                        )
                    }
            }

            Spacer(Modifier.height(64.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                settingNavigator
                    .forEachIndexed { index, navigator ->
                        NavigationRailItem(
                            selected = currentNavigation.contains(settingRoute[index]),
                            // label = { Text(navigator) },
                            onClick = { onNavigate(settingRoute[index]) },
                            alwaysShowLabel = false,
                            icon = { Icon(settingIcons[index], navigator) },
                        )
                    }
            }

            HorizontalDivider()

            // ProfileCard()
        }
    }
}

@Composable
fun CommonBar(
    modifier: Modifier = Modifier,
    currentNavigation: String = mainRoute[0],
    onCreate: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
) {
    NavigationBar(modifier) {
        cbtNavigator
            .forEachIndexed { index, navigator ->
                NavigationBarItem(
                    selected = currentNavigation.contains(cbtRoute[index]),
                    // label = { Text(navigator) },
                    onClick = { onNavigate(cbtRoute[index]) },
                    alwaysShowLabel = false,
                    icon = { Icon(cbtIcons[index], navigator) },
                )
            }
    }
}

@Composable
expect fun NavigationPreview()
