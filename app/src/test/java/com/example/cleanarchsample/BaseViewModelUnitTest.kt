package com.example.cleanarchsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

abstract class BaseViewModelUnitTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
}