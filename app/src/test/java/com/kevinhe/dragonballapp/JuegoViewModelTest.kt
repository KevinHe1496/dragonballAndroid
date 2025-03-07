package com.kevinhe.dragonballapp

import app.cash.turbine.test
import com.kevinhe.dragonballapp.juego.JuegoViewModel
import com.kevinhe.dragonballapp.juego.JuegoViewModel.State
import com.kevinhe.dragonballapp.model.Personaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val viewModel = JuegoViewModel()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    val personajeInicial = Personaje(
        id = "1",
        nombre = "Nombre",
        imagenUrl = "----",
        vidaTotal = 100,
        vidaActual = 100,
        vecesSelecionado = 0,
    )

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `golpearPersonaje baja la vida del personaje`() {

        val personajeEsperado = personajeInicial.copy(
            vidaActual = 90
        )

        viewModel.golpearPersonaje(personajeInicial)
        assertEquals(personajeEsperado, personajeInicial)

    }

    @Test
    fun `cuando se selecciona un personaje, el state se actualiza a PersonajeSeleccionado con ese personaje`() = runTest {
        viewModel.uiState.test {
            assertEquals(State.Loading, awaitItem())
            viewModel.personajeSeleccionado(personajeInicial)
            assertEquals(State.PersonajeSeleccionado(personajeInicial) , awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `cuando se deselecciona un personaje, el state se actualiza a Success`() = runTest {
        viewModel.uiState.test {
            viewModel.userRepository.setToken("eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJrZXZpbl9oZXJlZGlhMTBAaG90bWFpbC5jb20iLCJpZGVudGlmeSI6IkY2QTMyREQ5LTEwREYtNDEzMi1BQTI2LUFENTZEMURGN0U2RSJ9.DjTM9QRu5zFtFftxeti07olNxtBLCJqikI1RE7XlGIU")
            assertEquals(State.Loading, awaitItem())
            viewModel.personajeDeseleccionado()
            assertTrue(awaitItem() is State.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }
}