package token

import visitor.TokenVisitor

data class OperationToken(val type: Type) : Token {

    override fun accept(tokenVisitor: TokenVisitor) {
        tokenVisitor.visit(this)
    }

    override fun toString(): String {
        return type.toString()
    }

    enum class Type(val priority: Int, val op: (Int, Int) -> Int) {
        ADD(1, { x, y -> x + y }),
        SUB(1, { x, y -> x + y }),
        MUL(2, { x, y -> x * y }),
        DIV(2, { x, y -> x / y })
    }
}