package visitor

import token.BraceToken
import token.BraceToken.Type.LEFT
import token.NumberToken
import token.OperationToken
import token.Token
import java.util.*

class ParserVisitor: TokenVisitor() {

    var output = mutableListOf<Token>()

    var opBrStack = Stack<Token>()

    override fun visit(numberToken: NumberToken) {
        output.add(numberToken)
    }

    override fun visit(braceToken: BraceToken) {
        if (braceToken.type == LEFT) {
            opBrStack.push(braceToken)
            return
        }

        var hasLeft = false
        loop@ while (!opBrStack.isEmpty()) {
            val peek = opBrStack.peek()

            when (peek) {
                BraceToken(LEFT) -> {
                    hasLeft = true
                    opBrStack.pop()
                    break@loop
                }
                is OperationToken -> {
                    opBrStack.pop()
                    output.add(peek)
                }
                else -> {
                    throw IllegalArgumentException("Expected LEFT brace. But got $peek")
                }
            }
        }

        if (!hasLeft) {
            throw IllegalArgumentException("Expected LEFT brace")
        }
    }

    override fun visit(operationToken: OperationToken) {
        while (!opBrStack.isEmpty()) {
            val top = opBrStack.peek()

            if (top is BraceToken) {
                break
            }

            if (operationToken.type.priority > (top as OperationToken).type.priority) {
                break
            }

            output.add(top)
            opBrStack.pop()
        }

        opBrStack.push(operationToken)
    }

    fun convertToPolish(tokens: List<Token>): List<Token> {
        output = mutableListOf<Token>()
        opBrStack = Stack<Token>()

        tokens.forEach { it.accept(this) }

        while (!opBrStack.isEmpty()) {
            output.add(opBrStack.peek())
            opBrStack.pop()
        }

        return output
    }
}