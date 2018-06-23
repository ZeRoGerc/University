package visitor

import token.BraceToken
import token.NumberToken
import token.OperationToken
import token.Token

class PrintVisitor: TokenVisitor() {

    var output: StringBuilder = StringBuilder()

    override fun visit(numberToken: NumberToken) {
        output.append(numberToken.toString() + " ")
    }

    override fun visit(braceToken: BraceToken) {
        output.append(braceToken.toString() + " ")
    }

    override fun visit(operationToken: OperationToken) {
        output.append(operationToken.toString() + " ")
    }

    fun print(tokens: List<Token>): String {
        output = StringBuilder()

        tokens.forEach { it.accept(this) }

        return output.toString()
    }

}