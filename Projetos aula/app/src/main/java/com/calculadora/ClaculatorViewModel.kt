package com.calculadora

import androidx.compose.foundation.text.input.rememberTextFieldState
import kotlin.math.sqrt

class CalculatorViewModel {
    enum class Operation(op: String) {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("×"),
        DIVIDE("÷"),
        EQUAL("="),
        SQRT("√"),
        PERCENTAGE("%"),
        CLEAR("AC"),
        CLEAR_ENTRY("C");


        companion object {
            fun parseOperation(op: String): Operation {
                return when (op) {
                    "+" -> ADD
                    "-" -> SUBTRACT
                    "×" -> MULTIPLY
                    "÷" -> DIVIDE
                    "=" -> EQUAL
                    "√" -> SQRT
                    "%" -> PERCENTAGE
                    "AC" -> CLEAR
                    "C" -> CLEAR_ENTRY
                    else -> EQUAL
                }
            }
        }
    }

    var num = 0.0
    var  operation : Operation? = null

    fun calculate(newNum: Double, op: Operation): Double {
        if (op == Operation.ADD) num += newNum
        if (op == Operation.SUBTRACT) num -= newNum
        if (op == Operation.MULTIPLY) num *= newNum
        if (op == Operation.DIVIDE) num /= newNum
        return num
    }

    fun setOperation(currentNum: Double, op: Operation): Double {
        if (operation == null) {
            num = currentNum
        } else {
            calculate(currentNum, operation!!)
        }
        if (op == Operation.EQUAL) {
            operation = null
            return num
        }
        operation = op
        return num
    }

    fun clear(op: Operation): Double {
        if (op == Operation.CLEAR_ENTRY) {
            return 0.0
        }
        if (op == Operation.CLEAR) {
            num = 0.0
            operation = null
            return num
        }
        return num
    }

    fun calculateAgents(currentNum: Double ,op: Operation): Double {
        num = currentNum
        if (op == Operation.PERCENTAGE) num /= 100
        if (op == Operation.SQRT) num = sqrt(num)
        return num
    }
}
