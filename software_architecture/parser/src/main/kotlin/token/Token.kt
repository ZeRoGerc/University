package token

import visitor.TokenVisitor

interface Token {
    fun accept(tokenVisitor: TokenVisitor)
}