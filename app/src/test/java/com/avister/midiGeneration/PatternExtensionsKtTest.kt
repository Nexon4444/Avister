package com.avister.midiGeneration

import org.junit.Test


class PatternExtensionsKtTest {
    @Test
    fun createMidiMapTest() {
        val map = midiMap
        assert(map.count() == 88)
    }

    @Test
    fun extractDurationTest(){
        assert(extractDuration("C#6w") == "w")
    }
}