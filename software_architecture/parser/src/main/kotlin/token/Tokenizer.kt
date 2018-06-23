package token

import token.BraceToken.Type.LEFT
import token.BraceToken.Type.RIGHT
import token.OperationToken.Type.*

class Tokenizer {

    var state: State = End()

    var tokens = mutableListOf<Token>()

    fun tokenize(raw: String): List<Token> {
        state = Start()
        tokens = mutableListOf()

        for (c in raw) {
            if (!c.isWhitespace()) {
                processSymbol(c)
            }
        }

        val state = this.state
        when (state) {
            is Number -> tokens.add(NumberToken(state.current))
            is Error -> throw IllegalArgumentException("String {raw} cannot be parsed! Error: ${state.message}")
        }

        this.state = End()
        return tokens
    }

    private fun processSymbol(c: Char) {
        this.state.process(c)
    }

    abstract class State {
        abstract fun process(c: Char)
    }


    inner class Start : State() {
        override fun process(c: Char) {
            when (c) {
                '+' -> tokens.add(OperationToken(ADD))
                '-' -> tokens.add(OperationToken(SUB))
                '*' -> tokens.add(OperationToken(MUL))
                '/' -> tokens.add(OperationToken(DIV))
                '(' -> tokens.add(BraceToken(LEFT))
                ')' -> tokens.add(BraceToken(RIGHT))
                in '0'..'9' -> {
                    state = Number(c.toString().toInt())
                }
                else -> {
                    state = Error("Unknown symbol $c")
                }
            }
        }
    }


    inner class Number(var current: Int) : State() {

        override fun process(c: Char) {
            if (c.isDigit()) {
                current = current * 10 + c.toString().toInt()
            } else {
                tokens.add(NumberToken(current))

                state = Start()
                processSymbol(c)
            }
        }
    }


    inner class Error(val message: String) : State() {
        override fun process(c: Char) {}
    }


    inner class End : State() {
        override fun process(c: Char) {}
    }
}