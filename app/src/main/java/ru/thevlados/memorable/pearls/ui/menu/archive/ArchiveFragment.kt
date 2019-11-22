package ru.thevlados.memorable.pearls.ui.menu.archive

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.thevlados.memorable.pearls.R

class ArchiveFragment : Fragment() {

    companion object {
        fun newInstance() = ArchiveFragment()
    }

    private lateinit var viewModel: ArchiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.archive_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
