package io.estevanfick.marvelapp.data.api

import org.junit.Assert
import org.junit.Test

class HashGeneratorTest{

    @Test
    fun testMD5(){
        val hash = HashGenerator.md5("1","abcd", "1234" )
        Assert.assertEquals("ffd275c5130566a2916217b101f26150", hash)
    }
}