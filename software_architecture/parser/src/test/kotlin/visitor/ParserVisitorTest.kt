package visitor

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import token.BraceToken
import token.BraceToken.Type.LEFT
import token.BraceToken.Type.RIGHT
import token.NumberToken
import token.OperationToken
import token.OperationToken.Type.*

class ParserVisitorTest {

    val parserVisitor = ParserVisitor()

    @Test
    fun `convertToPolish should convert simple`() {
        assertArrayEquals(
                parserVisitor.convertToPolish(listOf(
                        NumberToken(1),
                        OperationToken(ADD),
                        NumberToken(2)
                )).toTypedArray(),
                arrayOf(
                        NumberToken(1),
                        NumberToken(2),
                        OperationToken(ADD)
                )
        )
    }

    @Test
    fun `convertToPolish should convert with different priority`() {
        assertArrayEquals(
                parserVisitor.convertToPolish(listOf(
                        NumberToken(1),
                        OperationToken(ADD),
                        NumberToken(2),
                        OperationToken(MUL),
                        NumberToken(3)
                )).toTypedArray(),
                arrayOf(
                        NumberToken(1),
                        NumberToken(2),
                        NumberToken(3),
                        OperationToken(MUL),
                        OperationToken(ADD)
                )
        )
    }

    @Test
    fun `convertToPolish should convert with braces`() {
        assertArrayEquals(
                parserVisitor.convertToPolish(listOf(
                        NumberToken(1),
                        OperationToken(ADD),
                        BraceToken(LEFT),
                        NumberToken(1),
                        OperationToken(SUB),
                        NumberToken(2),
                        BraceToken(RIGHT)
                )).toTypedArray(),
                arrayOf(
                        NumberToken(1),
                        NumberToken(1),
                        NumberToken(2),
                        OperationToken(SUB),
                        OperationToken(ADD)
                )
        )
    }
}