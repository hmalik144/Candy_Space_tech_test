package com.example.h_mal.candyspace.data.repositories

import com.example.h_mal.candyspace.data.api.ApiClass
import com.example.h_mal.candyspace.data.api.ApiResponse
import com.example.h_mal.candyspace.data.api.User
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
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
        val mockApiResponse = mock(ApiResponse::class.java)
        val mockResponse = Response.success(mockApiResponse)

        Mockito.`when`(api.getUsersFromApi("12345")).thenReturn(
            mockResponse
        )

        val getUser = repository.getUsers("12345")

        assertNotNull(getUser)
        assertEquals(mockApiResponse, getUser)
    }

    @Test
    fun fetchUserFromApi_negativeResponse() = runBlocking {
        val mockResponse = Response.error<ApiResponse>(403,
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