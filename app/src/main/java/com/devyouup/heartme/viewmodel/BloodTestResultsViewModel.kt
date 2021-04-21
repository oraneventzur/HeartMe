package com.devyouup.heartme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devyouup.heartme.model.*
import com.devyouup.heartme.repository.BloodTestRepository
import com.devyouup.heartme.utils.extractCategoryWithName
import java.util.*

class BloodTestResultsViewModel : ViewModel() {

    private var bloodTests = MutableLiveData<BloodTestConfig>()
    var currentTestCategory: TestCategory? = null
    var testResultsEvaluation = MutableLiveData<BloodTestEvaluation>()

    fun fetchBloodTestConfig()
    : LiveData<BloodTestConfig> {
        bloodTests = BloodTestRepository.fetchBloodTestConfig()
        return bloodTests
    }

    fun onSubmitButtonClicked(
        name: String?,
        value: String?
    ) {
        if (name.isNullOrEmpty() || value.isNullOrEmpty()){
            testResultsEvaluation.value = BloodTestEvaluation(
                Result.UNKNOWN, null, null
            )
            return
        }
        val currentTest = extractBloodTestWithName(name.toLowerCase(Locale.ROOT))
        testResultsEvaluation.value = evaluateTestResults(currentTest, value.toInt())
    }

    private fun extractBloodTestWithName(
        name: CharSequence
    ): BloodTest? {
        currentTestCategory = extractCategoryWithName(name)

        if (null != currentTestCategory){
            for (bloodTest in bloodTests.value?.bloodTestConfig!!){
                if (bloodTest.category == currentTestCategory)
                    return bloodTest
            }
        }
        return null
    }

    private fun evaluateTestResults(
        currentTest: BloodTest?,
        value: Int
    ): BloodTestEvaluation {
        if (null == currentTest)
            return BloodTestEvaluation(Result.UNKNOWN, null, null)

        return when {
            currentTest.threshold < value -> {
                BloodTestEvaluation(Result.BAD, currentTest.name, currentTest.threshold)
            }
            currentTest.threshold >= value -> {
                BloodTestEvaluation(Result.GOOD, currentTest.name, currentTest.threshold)
            }
            else -> BloodTestEvaluation(Result.UNKNOWN, currentTest.name, currentTest.threshold)
        }
    }

}

