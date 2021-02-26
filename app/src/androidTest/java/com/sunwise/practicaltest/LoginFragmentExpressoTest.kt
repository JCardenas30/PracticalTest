package com.sunwise.practicaltest

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunwise.practicaltest.view.ui.LoginFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentExpressoTest {

    @Test
    fun testLoginCorrectFragment(){
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        //navController.setGraph(R.navigation.nav_graph)

        // Create a graphical FragmentScenario for the TitleScreen
        val loginScenario = launchFragmentInContainer<LoginFragment>()

        // Set the NavController property on the fragment
        loginScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.etEmail))
            .perform(typeText("admin0@admin.com"), closeSoftKeyboard())
        onView(withId(R.id.etPassword))
            .perform(typeText("Admin0#"), closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(click())
        assert(navController.currentDestination?.id?.equals(R.id.mainFragment) == true)
    }
}