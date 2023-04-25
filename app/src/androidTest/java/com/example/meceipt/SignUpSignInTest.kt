package com.example.meceipt

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.meceipt.activities.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpSignInTest {
    @get:Rule
    var welcomeActivityRule = ActivityScenarioRule(WelcomeActivity::class.java)

    @Test
    fun signInTest() {
        onView(withId(R.id.btnSignIn)).perform(click())
        onView(withId(R.id.tfEmailSignIn)).check(matches(isDisplayed()))

        val email = "test@email.com"
        val password = "123456"
        val confPassword = "123456"

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            assert(task.isSuccessful)
        }
    }


}