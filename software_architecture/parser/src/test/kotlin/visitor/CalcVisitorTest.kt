package visitor

import org.junit.Assert.assertEquals
import org.junit.Test
import token.NumberToken
import token.OperationToken
import token.OperationToken.Type.ADD
import token.OperationToken.Type.MUL

class CalcVisitorTest {

    val calcVisitor = CalcVisitor()

    @Test
    fun `calc should calc single number`() {
        assertEquals(calcVisitor.calc(listOf(NumberToken(10))), 10)
    }

    @Test
    fun `calc should calc single operation`() {
        assertEquals(calcVisitor.calc((listOf(NumberToken(1), NumberToken(2), OperationToken(ADD)))), 3)
    }

    @Test
    fun `calc should calc complex`() {
        assertEquals(
                calcVisitor.calc((listOf(
                        NumberToken(3),
                        NumberToken(2),
                        NumberToken(3),
                        OperationToken(ADD),
                        OperationToken(MUL)
                ))),
                15
        )
    }
}