package com.devyouup.heartme.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.devyouup.heartme.R
import com.devyouup.heartme.model.BloodTestEvaluation
import com.devyouup.heartme.model.Result
import com.devyouup.heartme.viewmodel.BloodTestResultsViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var bloodTestViewModel: BloodTestResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initUiElements()
        bindData()
    }

    private fun initUiElements() {
        userInputSubmitBtn.setOnClickListener {
            bloodTestViewModel.onSubmitButtonClicked(
                userInputTestNameEt.text.toString(),
                userInputTestResValueEt.text.toString()
            )
        }
    }

    private fun bindData() {
        bloodTestViewModel.fetchBloodTestConfig().observe(
            this, { bloodTestConfig ->
                if (null == bloodTestConfig) {
                    disableUIElements()
                    presentOfflineMessage()
                }
            })

        bloodTestViewModel.testResultsEvaluation.observe(
            this, { bloodTestEvaluation ->
                presentTestEvaluationResults(bloodTestEvaluation)
            }
        )
    }

    private fun initViewModel() {
        bloodTestViewModel = ViewModelProvider(this)
            .get(BloodTestResultsViewModel::class.java)
    }

    private fun presentTestEvaluationResults(
        bloodTestEvaluation: BloodTestEvaluation
    ) {
        if (bloodTestEvaluation.result == Result.UNKNOWN) {
            testResultsEvaluationTv.text = getString(
                R.string.test_evaluation_fail,
                bloodTestEvaluation.result.name
            )
        } else {
            testResultsEvaluationTv.text = getString(
                R.string.test_evaluation_success,
                bloodTestEvaluation.result.name,
                bloodTestEvaluation.testName
            )
        }
    }

    private fun disableUIElements() {
        userInputTestResValueEt.isEnabled = false
        userInputTestNameEt.isEnabled = false
        userInputSubmitBtn.isEnabled = false
    }

    private fun presentOfflineMessage() {
        screenTitleTv.text = getString(R.string.offline_msg_title)
        onBoardingMessageTv.text = getString(R.string.offline_msg_hint)
    }
}