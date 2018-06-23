package visitor

import token.BraceToken
import token.NumberToken
import token.OperationToken
import token.Token
import java.util.*

class CalcVisitor : TokenVisitor() {

    var st = Stack<Int>()

    override fun visit(numberToken: NumberToken) {
        st.add(numberToken.number)
    }

    override fun visit(braceToken: BraceToken) {
        throw IllegalArgumentException("Braces are not allowed in polish notation!")
    }

    override fun visit(operationToken: OperationToken) {
        val second = st.peek()
        st.pop()
        val first = st.peek()
        st.pop()

        st.push(operationToken.type.op(first, second))
    }

    fun calc(tokens: List<Token>): Int {
        st = Stack()

        tokens.forEach { it.accept(this) }
        assert(st.size == 1)

        return st.peek()
    }
}