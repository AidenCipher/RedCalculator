package com.darkuros.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var lastNumeric = false
    var lastDot = false

    fun onDigit(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        var tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        var tvInput = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length-2)
        }
        return value
    }

    fun onOperator(view: View){
        var tvInput = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString()))
            tvInput.append((view as Button).text)
        lastNumeric = false
        lastDot  =false
    }

    fun onEquals(view: View){
        var tvInput = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")

                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")

                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")

                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")

                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}