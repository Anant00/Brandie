package com.bradie.app.utils

import com.bradie.app.apiservice.Hit

object FakeModel {

    fun createImagesData(count: Int): List<Hit> {
        return (0 until count).map {
            Hit(
                webformatURL = "https://dummy.com$it",
                downloads = 12 + it,
                likes = 1000 + it,
                user = "foo$it"
            )
        }
    }

    fun createSingleImageData(): Hit {
        return Hit(
            webformatURL = "https://dummy.com",
            downloads = 12,
            likes = 1000,
            user = "foo"
        )
    }
}