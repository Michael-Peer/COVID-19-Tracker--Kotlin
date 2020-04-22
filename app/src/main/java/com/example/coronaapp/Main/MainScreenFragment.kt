package com.example.coronaapp.Main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.coronaapp.MainScreenFragmentDirections
import com.example.coronaapp.R
import com.example.coronaapp.databinding.FragmentMainScreenBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class MainScreenFragment : Fragment() {

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var navController: NavController


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainScreenBinding>(
            inflater,
            R.layout.fragment_main_screen, container, false
        )


        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner

        viewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)

        binding.viewModel = viewModel

        binding.searchButton.setOnClickListener {
            val countryData = viewModel.getSingleCountryData(binding.editText.text.toString())
            if (countryData == null) {
                Toast.makeText(context, "No such country, try again", Toast.LENGTH_LONG).show()
            } else {
                it.findNavController().navigate(
                    MainScreenFragmentDirections.actionMainScreenFragmentToSingleCountry(
                        binding.editText.text.toString().toLowerCase().capitalize(),
                        countryData
                    )
                )
            }
        }

        //observe edit text value
        viewModel.editTextContent.observe(viewLifecycleOwner, Observer {
            //            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            Log.i("MainScreenFragment", "death ${it[0]}")
            setupChart(binding, viewModel, it[0], it[1], it[2])
        })


        setHasOptionsMenu(true)

        return binding.root
    }


    fun setupChart(
        binding: FragmentMainScreenBinding,
        viewModel: MainScreenViewModel,
        death: Float,
        rec: Float,
        confi: Float
    ) {
        Log.i("Main", "$death $confi $rec")

        val pieChart = binding.pieChart
        pieChart.apply {
            setUsePercentValues(true)
            setHoleColor(Color.parseColor("#0d0716"))
            setCenterTextColor(Color.WHITE)
            centerText = "World Total"
            setCenterTextSize(20F)
            setDrawEntryLabels(false)
            description.isEnabled = false
            legend.apply {
                formSize = 18f
                textSize = 18f
                textColor = Color.WHITE
                xEntrySpace = 10f
                form = Legend.LegendForm.CIRCLE
                isWordWrapEnabled = true
            }
        }





        val yVals = ArrayList<PieEntry>()
        yVals.apply {
            add(PieEntry(death, "Deaths"))
            add(PieEntry(confi, "Cases"))
            add(PieEntry(rec, "Recovered"))
        }

        val dataSet = PieDataSet(yVals, "")

        val colors = java.util.ArrayList<Int>()
        colors.apply {
            add(Color.parseColor("#FF6B45"))
            add(Color.parseColor("#FFAB05"))
            add(Color.parseColor("#47B39C"))
        }

        dataSet.setColors(colors)
//        dataSet.sliceSpace = 4.5f

        val data = PieData(dataSet)
        data.apply {
            setValueFormatter(PercentFormatter(pieChart))
            setValueTextColor(Color.WHITE)
            setValueTextSize(16f)
        }

        pieChart.data = data
        pieChart.invalidate()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, view!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

}
