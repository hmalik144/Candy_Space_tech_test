package com.example.h_mal.candyspace.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.candyspace.R
import com.example.h_mal.candyspace.ui.main.CompletionListener
import com.example.h_mal.candyspace.ui.main.MainFragment
import com.example.h_mal.candyspace.ui.main.MainViewModel
import com.example.h_mal.candyspace.ui.main.MainViewModelFactory
import com.example.h_mal.candyspace.utils.displayToast
import com.example.h_mal.candyspace.utils.hide
import com.example.h_mal.candyspace.utils.show
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        //retrieve viewmodel from viewmodel factory
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.completionListener = this

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val name = when (supportFragmentManager.fragments[0]::class.java.simpleName) {
                "UserProfileFragment" -> "User"
                else -> "Candy space"
            }
            title = name
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }else{
            super.onBackPressed()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStarted() {
        progress_circular.show()
    }

    override fun onSuccess() {
        progress_circular.hide()
    }

    override fun onFailure(message: String) {
        progress_circular.hide()
        displayToast(message)
    }
}
