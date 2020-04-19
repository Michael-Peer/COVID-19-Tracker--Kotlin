package com.example.coronaapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.coronaapp.databinding.FragmentSingleCountryBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class SingleCountry : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSingleCountryBinding>(
            inflater, R.layout.fragment_single_country, container, false
        )

        val args = SingleCountryArgs.fromBundle(arguments!!)


        setupSingleCountryChart(
            binding,
            args.countryData!!.deaths.toFloat(),
            args.countryData!!.confirmed.toFloat(),
            args.countryData!!.recovered.toFloat(),
            args.countryName
        )

        binding.apply {
            totalDeathText.text =
                NumberFormat.getNumberInstance(Locale.US).format(args.countryData!!.deaths)

            totalRecoverdText.text =
                NumberFormat.getNumberInstance(Locale.US).format(args.countryData!!.recovered)

            totalSickText.text =
                NumberFormat.getNumberInstance(Locale.US).format(args.countryData!!.confirmed)

        }
        // Inflate the layout for this fragment
        return binding.root
    }



    fun setupSingleCountryChart(
        binding: FragmentSingleCountryBinding,
        death: Float,
        confi: Float,
        rec: Float,
        countryName: String
    ) {
        Log.i("Main", "$death $confi $rec")

        val pieChart = binding.pieChart
        pieChart.apply {
            setUsePercentValues(true)
            setHoleColor(Color.parseColor("#0d0716"))
            setCenterTextColor(Color.WHITE)
            centerText = countryName
            setCenterTextSize(20F)
            setDrawEntryLabels(false)
            description.isEnabled = false
            legend.isEnabled = false
//            legend.apply {
//                formSize = 14f
//                textSize = 14f
//                textColor = Color.WHITE
//                xEntrySpace = 10f
//                isWordWrapEnabled = true
//                form = Legend.LegendForm.CIRCLE
//            }
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


}
