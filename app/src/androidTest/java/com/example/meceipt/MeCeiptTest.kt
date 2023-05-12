package com.example.meceipt

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.meceipt.activities.HomeActivity
import com.example.meceipt.activities.HomeFragment
import com.example.meceipt.activities.SignInActivity
import com.example.meceipt.activities.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MeCeiptTest {
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

    @get:Rule
    var signInActivityRule = ActivityScenarioRule(SignInActivity::class.java)

    @Test
    fun homeActivityTest() {
        val email = "test@email.com"
        val password = "123456"

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                onView(withId(R.id.btnSignIn)).perform(click())
                onView(withId(R.layout.fragment_home)).check(matches(isDisplayed()))
            }
        }
    }

    @get:Rule
    var homeActivityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun homeNavigationTest() {
        onView(withId(R.id.qrCode)).check(matches(isDisplayed()))
        onView(withId(R.id.env)).perform(click())
        onView(withId(R.id.tfReceiptAmount)).check(matches(isDisplayed()))
        onView(withId(R.id.receipt)).perform(click())
        onView(withId(R.id.userReceiptRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.qrCode)).check(matches(isDisplayed()))

    }

    @Test
    fun viewReceiptsTest() {
        onView(withId(R.id.qrCode)).check(matches(isDisplayed()))
        onView(withId(R.id.receipt)).perform(click())
        onView(withId(R.id.userReceiptRecyclerView)).check(matches(isDisplayed()))
        onView(withText("Test")).check(matches(isDisplayed()))
    }



}