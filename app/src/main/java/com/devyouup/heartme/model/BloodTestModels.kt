package com.devyouup.heartme.model

data class BloodTest(var name: String, var threshold: Int, var category: TestCategory?)
data class BloodTestConfig(var bloodTestConfig: MutableList<BloodTest>)
data class BloodTestEvaluation(var result: Result, var testName: String?, var threshold: Int?)
enum class Result(val result: String) {
    GOOD("Good!"),
    BAD("Bad!"),
    UNKNOWN("Unknown")
}
enum class TestCategory(category: String) {
    HDL("hdl"),
    LDL("ldl"),
    A1C("a1c"),
    UNKNOWN("unknown")
}

