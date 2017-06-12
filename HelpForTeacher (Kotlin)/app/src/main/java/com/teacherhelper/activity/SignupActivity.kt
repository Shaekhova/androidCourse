package com.teacherhelper.activity

import com.kinvey.java.User

import android.Manifest.permission.READ_CONTACTS

/**
 * A sign up  screen that offers sign up via email/password.
 */
class SignupActivity : android.support.v7.app.AppCompatActivity() {

    // UI references.
    private var mEmailView: android.widget.AutoCompleteTextView? = null
    private var mPasswordView: android.widget.EditText? = null
    private var mProgressView: android.view.View? = null
    private var mLoginFormView: android.view.View? = null
    private var client: com.teacherhelper.KniveyClient? = null

    @android.annotation.TargetApi(android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
    @android.support.annotation.RequiresApi(android.os.Build.VERSION_CODES.CUPCAKE)
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.lili.teacherhelper.R.layout.activity_signup)
        // Set up the login form.
        client = (application as com.teacherhelper.TeacherApp).sharedClient
        mEmailView = findViewById(com.example.lili.teacherhelper.R.id.login_sign_up) as android.widget.AutoCompleteTextView

        mPasswordView = findViewById(com.example.lili.teacherhelper.R.id.password_sign_up) as android.widget.EditText
        mPasswordView!!.setOnEditorActionListener(android.widget.TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == com.example.lili.teacherhelper.R.id.login_sign_up || id == android.view.inputmethod.EditorInfo.IME_NULL) {
                attemptSignup()
                return@OnEditorActionListener true
            }
            false
        })

        val mEmailSignInButton = findViewById(com.example.lili.teacherhelper.R.id.sign_up) as android.widget.Button
        mEmailSignInButton.setOnClickListener { attemptSignup() }

        mLoginFormView = findViewById(com.example.lili.teacherhelper.R.id.sign_form)
        mProgressView = findViewById(com.example.lili.teacherhelper.R.id.signUpProgress)
    }


    private fun mayRequestContacts(): Boolean {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(READ_CONTACTS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            android.support.design.widget.Snackbar.make(mEmailView!!, com.example.lili.teacherhelper.R.string.permission_rationale, android.support.design.widget.Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) { requestPermissions(arrayOf(READ_CONTACTS), com.teacherhelper.activity.SignupActivity.Companion.REQUEST_READ_CONTACTS) }
        } else {
            requestPermissions(arrayOf(READ_CONTACTS), com.teacherhelper.activity.SignupActivity.Companion.REQUEST_READ_CONTACTS)
        }
        return false
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @android.support.annotation.RequiresApi(android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun attemptSignup() {

        // Reset errors.
        mEmailView!!.error = null
        mPasswordView!!.error = null

        // Store values at the time of the login attempt.
        val email = mEmailView!!.text.toString()
        val password = mPasswordView!!.text.toString()

        var cancel = false
        var focusView: android.view.View? = null

        // Check for a valid password, if the user entered one.
        if (!android.text.TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView!!.error = getString(com.example.lili.teacherhelper.R.string.error_invalid_password)
            focusView = mPasswordView
            cancel = true
        }

        // Check for a valid email address.
        if (android.text.TextUtils.isEmpty(email)) {
            mEmailView!!.error = getString(com.example.lili.teacherhelper.R.string.error_field_required)
            focusView = mEmailView
            cancel = true
        } else if (!isEmailValid(email)) {
            mEmailView!!.error = getString(com.example.lili.teacherhelper.R.string.error_invalid_email)
            focusView = mEmailView
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            if (!client!!.user<com.kinvey.java.User<*>>().isUserLoggedIn()) {
                client!!.user<com.kinvey.java.User<*>>().create(email, password, object : com.kinvey.java.core.KinveyClientCallback<User<*>> {
                    override fun onSuccess(result: com.kinvey.java.User<*>) {
                        //successfully sign up in
                        result.set("isTeacher",true)
                        client?.user<com.kinvey.java.User<*>>()?.update(result, object: com.kinvey.java.core.KinveyClientCallback<User<*>> {
                            @android.support.annotation.RequiresApi(android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
                            override fun onSuccess(result: com.kinvey.java.User<*>) {

                            }
                            override fun onFailure(error: Throwable) {

                            }
                        })
                        showProgress(false)
                        val intent = android.content.Intent(this@SignupActivity, ProfileActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(error: Throwable) {
                        showProgress(false)
                        mPasswordView!!.error = getString(com.example.lili.teacherhelper.R.string.error_incorrect_password)
                        mPasswordView!!.requestFocus()
                    }
                })
            } else {
                showProgress(false)
                val intent = android.content.Intent(this@SignupActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @android.annotation.TargetApi(android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

            mLoginFormView!!.visibility = if (show) android.view.View.GONE else android.view.View.VISIBLE
            mLoginFormView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 0 else 1).toFloat()).setListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    mLoginFormView!!.visibility = if (show) android.view.View.GONE else android.view.View.VISIBLE
                }
            })

            mProgressView!!.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
            mProgressView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 1 else 0).toFloat()).setListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    mProgressView!!.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
                }
            })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView!!.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
            mLoginFormView!!.visibility = if (show) android.view.View.GONE else android.view.View.VISIBLE
        }
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}

