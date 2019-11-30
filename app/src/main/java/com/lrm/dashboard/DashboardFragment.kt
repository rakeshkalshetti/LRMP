package com.lrm.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lrm.databinding.DashboardFragmentBinding
import com.lrm.ui.login.DashBoardAdapter
import com.lrm.util.State
import kotlinx.android.synthetic.main.list_footer_layout.*

class DashboardFragment : Fragment() {

    companion object {
        fun newInstance(t: String) : DashboardFragment {
            val dashboardFragment = DashboardFragment()
            token = "Bearer "+t;
            return dashboardFragment
        }

        var token: String = ""
    }

    private lateinit var dashBoardAdapter: DashBoardAdapter

    lateinit var binding: DashboardFragmentBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DashboardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        Log.e("TextNS", "Token in dashed :" + token)

        initAdapter()
        initState()
        viewModel.dashList.observe(this, Observer {
            dashBoardAdapter.submitList(it)
        })
    }

    private fun initAdapter() {
        dashBoardAdapter = DashBoardAdapter { viewModel.retry() }
        binding.dashList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.dashList.adapter = dashBoardAdapter
        viewModel.dashList.observe(this, Observer {
            dashBoardAdapter.submitList(it)
            dashBoardAdapter.notifyDataSetChanged()
        })
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }

        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                dashBoardAdapter.setState(state ?: State.DONE)
            }
        })
    }
}
