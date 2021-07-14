package com.avister.midiGeneration

import org.jfugue.pattern.Pattern
import org.junit.Test


class PatternExtensionsKtTest {
    @Test
    fun createMidiMapTest() {
        val map = midiMap
        assert(map.count() == 88)
    }

    @Test
    fun extractDurationTest(){
        val music = "C#6w"
        assert(music.extractDuration() == "w")
    }

    @Test
    fun testPattern() {
        val x = Pattern("B3q B4q B5q B6q")
        val str = x.toString()
        val wait = 0
    }

}
