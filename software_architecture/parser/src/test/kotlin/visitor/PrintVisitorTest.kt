package visitor

import org.junit.Assert.assertEquals
import org.junit.Test
import token.BraceToken
import token.BraceToken.Type.LEFT
import token.BraceToken.Type.RIGHT
import token.NumberToken
import token.OperationToken
import token.OperationToken.Type.ADD

class PrintVisitorTest {

    val printVisitor = PrintVisitor()

    @Test
    fun `print should print`() {
        assertEquals(
                printVisitor.print(listOf(
                        BraceToken(LEFT),
                        NumberToken(1),
                        OperationToken(ADD),
                        NumberToken(2),
                        BraceToken(RIGHT)
                )),
                "BRACE(LEFT) NUMBER(1) ADD NUMBER(2) BRACE(RIGHT) "
        )
    }
}