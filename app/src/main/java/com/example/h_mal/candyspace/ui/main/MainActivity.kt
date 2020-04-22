package com.example.h_mal.candyspace.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.ui.home.MainFragment
import com.example.h_mal.candyspace.utils.displayToast
import com.example.h_mal.candyspace.utils.hide
import com.example.h_mal.candyspace.utils.show
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/**
 *    [MainActivity] hosting the fragments and controlling a lot of the UI
 */
class MainActivity : AppCompatActivity(), KodeinAware, CompletionListener {

    //retrieve the viewmodel factory from the kodein dependency injection
    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()

    companion object{
        //View model to be used by the fragments hosted by MainActivity
        lateinit var viewModel: MainViewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //setup home button for back navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //retrieve viewmodel from viewmodel factory
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.completionListener = this

        if (savedInstanceState == null) {
            //display first fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    MainFragment()
                )
                .commit()
        }

        //fragment change listener to display title based on current fragment
        supportFragmentManager.addOnBackStackChangedListener {
            val name = when (supportFragmentManager.fragments[0]::class.java.simpleName) {
                "UserProfileFragment" -> "User"
                else -> "Candy space"
            }
            title = name
        }
    }

    /*
    * Back button over override
    *  - close app if home fragment
    *  - return to previous fragment if User fragment
     */
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }else{
            super.onBackPressed()
        }

    }

    //When home button in toolbar is pressed
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /*
     * completion listener methods
     */
    override fun onStarted() {
        //show loading
        progress_circular.show()
    }

    override fun onSuccess() {
        //loading completed - hide loading
        progress_circular.hide()
    }

    override fun onFailure(message: String) {
        //loading failed - hide loading and display toast of error
        progress_circular.hide()
        displayToast(message)
    }
}
