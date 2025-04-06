package com.example.movieapp.utils.navBackStackEntryExt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

object NavBackStackEntryExt {
    @Composable
    inline fun <reified T: ViewModel>NavBackStackEntry.scopedViewModel(navController: NavController):T{
        val parentBackStackEntry = remember(this) {
            navController.getBackStackEntry(this.destination?.parent?.route?.toString()?:"")
        }
        return koinViewModel(viewModelStoreOwner = parentBackStackEntry)
    }
}