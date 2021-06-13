package com.avister

fun (() -> Any?).shouldNotThrow()
        = try { invoke() } catch (ex : Exception){ throw Error("expected not to throw!", ex) }