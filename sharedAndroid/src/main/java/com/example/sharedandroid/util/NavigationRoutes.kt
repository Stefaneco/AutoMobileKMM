package com.example.sharedandroid.util

object NavigationRoutes {
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val PROFILE = "profile"
    const val SPLASH = "splash"
    const val FORGOT_PASSWORD = "forgot_password"
    const val CHANGE_PASSWORD = "change_password"
    const val EDIT_PROFILE = "edit_profile/{name}/{surname}/{phone}/{email}"
    const val EDIT_PROFILE_ARGS = "edit_profile/%s/%s/%s/%s"

    const val DOCUMENT_LIST = "document_list"
    const val DOCUMENT_DETAILS = "document_details/{docId}"
    const val DOCUMENT_DETAILS_ARGS = "document_details/%s"

    //MECHANIC
    const val CREATE_DOCUMENT = "create_document"
    const val EDIT_DOCUMENT = "edit_document/{docId}"
    const val EDIT_DOCUMENT_ARGS = "edit_document/%s"

}