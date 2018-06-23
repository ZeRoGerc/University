package token

import visitor.TokenVisitor

data class NumberToken(val number: Int): Token {

    override fun accept(tokenVisitor: TokenVisitor) {
        tokenVisitor.visit(this)
    }

    override fun toString(): String {
        return "NUMBER($number)"
    }
}