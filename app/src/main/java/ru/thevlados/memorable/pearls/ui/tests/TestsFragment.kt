package ru.thevlados.memorable.pearls.ui.tests

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.thevlados.memorable.pearls.R

class TestsFragment : Fragment() {

    companion object {
        fun newInstance() = TestsFragment()
    }

    private lateinit var viewModel: TestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tests_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TestsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
