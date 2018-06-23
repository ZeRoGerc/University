package visitor

import token.BraceToken
import token.NumberToken
import token.OperationToken

abstract class TokenVisitor {

    abstract fun visit(numberToken: NumberToken)

    abstract fun visit(braceToken: BraceToken)

    abstract fun visit(operationToken: OperationToken)
}