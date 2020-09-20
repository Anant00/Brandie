package com.bradie.app.utilstest

import com.bradie.app.utils.Status
import com.bradie.app.utils.ViewStatus
import org.junit.Assert
import org.junit.Test

class ViewStatusTest {

    private var status = Status.LOADING
    private val message = "Ok"
    private val data = 1

    @Test
    fun `test resource status`() {
        Assert.assertEquals(ViewStatus.loading("loading", null).status, status)
    }

    @Test
    fun `test resource message`() {
        Assert.assertEquals(ViewStatus.loading("Ok", null).message, message)
    }

    @Test
    fun `test resource data`() {
        Assert.assertEquals(ViewStatus.loading("Ok", 1).data, data)
    }
}