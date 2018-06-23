package token

import visitor.TokenVisitor

data class BraceToken(val type: Type): Token {

    override fun accept(tokenVisitor: TokenVisitor) {
        tokenVisitor.visit(this)
    }

    override fun toString(): String {
        return "BRACE($type)"
    }

    enum class Type {
        LEFT, RIGHT
    }
}
