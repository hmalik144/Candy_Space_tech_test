package com.example.h_mal.candyspace.data.repositories

import android.util.Log
import com.example.h_mal.candyspace.data.api.ApiClass
import com.example.h_mal.candyspace.data.api.User
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RepositoryTest {

    lateinit var repository: Repository

    @Mock
    lateinit var api: ApiClass

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = Repository(api)
    }

    @Test
    fun fetchUserFromApi_positiveResponse() = runBlocking {
        val mockUser = mock(User::class.java)
        val mockResponse = Response.success(mockUser)

        Mockito.`when`(api.getUsersFromApi("12345")).thenReturn(
            mockResponse
        )

        val getUser = repository.getUsers("12345")

        assertNotNull(getUser)
        assertEquals(mockUser, getUser)
    }

    @Test
    fun fetchUserFromApi_negativeResponse() = runBlocking {
        val mockResponse = Response.error<User>(403,
            ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"key\":[\"somestuff\"]}"
            ))


        Mockito.`when`(api.getUsersFromApi("12345")).thenReturn(
            mockResponse
        )

        val getUser = repository.getUsers("12345")
        assertNotNull(getUser)
    }
}