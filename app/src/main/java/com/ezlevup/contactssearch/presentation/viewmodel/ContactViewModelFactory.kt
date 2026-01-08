package com.ezlevup.contactssearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.ezlevup.contactssearch.ContactsSearchApplication

/** ViewModel Factory (수동 주입용) */
object ContactViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application =
                checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as
                        ContactsSearchApplication
        return ContactViewModel(application.container.contactRepository) as T
    }
}
