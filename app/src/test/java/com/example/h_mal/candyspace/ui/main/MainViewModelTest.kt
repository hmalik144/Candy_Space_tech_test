package com.example.h_mal.candyspace.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.h_mal.candyspace.data.api.ApiClass
import com.example.h_mal.candyspace.data.api.model.ApiResponse
import com.example.h_mal.candyspace.data.api.model.User
import com.example.h_mal.candyspace.data.repositories.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.IOException

class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun getApiFromRepository_SuccessfulReturn() = runBlocking{
        val user = mock(User::class.java)
        val mockApiResponse = ApiResponse(listOf(user),null,null,null)

        Mockito.`when`(repository.getUsers("12345")).thenReturn(mockApiResponse)

        viewModel.submit(null)

        assertEquals(mockApiResponse.items, viewModel.usersLiveData.value)
    }

    @Test
    fun getApiFromRepository_unsuccessfulReturn() = runBlocking{
        Mockito.`when`(repository.getUsers("12345")).thenAnswer{ throw IOException() }

        viewModel.submit(null)

        assertEquals(null, viewModel.usersLiveData.value)
    }
}