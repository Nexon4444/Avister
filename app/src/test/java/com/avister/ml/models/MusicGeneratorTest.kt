//package com.avister.ml.models
//import android.test.mock.MockContext
//import org.junit.Test
//import org.junit.Before
////import Mockito.`when`
//import android.content.Context
//import org.mockito.kotlin.doReturn
//import org.mockito.kotlin.mock
//
//
//internal class MusicGeneratorTest {
////    private val mockContext = MockContext()
//
////    val mock = mock<Context> {
////        on {  } doReturn "text"
////    }
//    @Before
//    fun setup() {
//
//    }
//
//
//    @Test
//    fun testGenerateRandomSequenceSize() {
//        val generatedSequence = MusicGenerator.generateRandomSequence(100, 10)
//        assert(true) { generatedSequence.size == 100 }
//    }
//
//    @Test
//    fun testGenerateRandomSequenceEachElementBetween0and10() {
//        val generatedSequence = MusicGenerator.generateRandomSequence(100, 10)
//        assert(true) { generatedSequence.all { it in 1..9 }  }
//    }
//}