package com.devyouup.heartme.utils

import androidx.lifecycle.MutableLiveData
import com.devyouup.heartme.model.BloodTestConfig
import com.devyouup.heartme.model.TestCategory

fun enrichDataSet(bloodTestConfig: MutableLiveData<BloodTestConfig>) {
    if (null != bloodTestConfig.value?.bloodTestConfig) {
        for (test in bloodTestConfig.value?.bloodTestConfig!!){
            test.category = extractCategoryWithName(test.name.toLowerCase())!!
        }
    }
}

fun extractCategoryWithName(name: CharSequence): TestCategory? {
    val words = name.split(" ", ",")
    for (word in words) {
        when (word) {
            TestCategory.LDL.name.toLowerCase() -> {
                return TestCategory.LDL
            }
            TestCategory.HDL.name.toLowerCase() -> {
                return TestCategory.HDL
            }
            TestCategory.A1C.name.toLowerCase() -> {
                return TestCategory.A1C
            }
        }
    }
    return null
}