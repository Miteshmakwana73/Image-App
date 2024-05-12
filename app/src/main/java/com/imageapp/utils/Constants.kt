package com.imageapp.utils

object Constants {

    const val BASE_URL = "https://acharyaprashant.org/api/v2/"

    const val VALIDATION_ERROR = "Oops Something went wrong.Please try again later"
    const val MSG_NO_INTERNET_CONNECTION = "The internet connection appears to be offline"
    const val MSG_SOMETHING_WENT_WRONG = "Something went wrong"

    const val ABOUT = """
•	Developed an Android task management app in android studio using Kotlin and MVVM, Dagger Hilt.
    
•	Utilized Retrofit for seamless Network request integration.
    
•	Features:
    o	View images on listing with minimum time
    
•	Continuously refining user experience, optimizing performance, and ensuring robust security measures.
    
•	Code is available in my repo: https://github.com/Miteshmakwana73/Image-App
    """

    fun checkValidation(title:String): Boolean {
        return title.isBlank()
    }

}