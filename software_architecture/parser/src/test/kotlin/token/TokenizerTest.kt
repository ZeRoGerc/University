package token

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import token.BraceToken.Type.LEFT
import token.BraceToken.Type.RIGHT
import token.OperationToken.Type.*

class TokenizerTest {

    val tokenizer = Tokenizer()

    @Test
    fun `tokenize should process number`() {
        assertArrayEquals(tokenizer.tokenize("123").toTypedArray(), arrayOf(NumberToken(123)))
    }

    @Test
    fun `tokenize should process operations`() {
        assertArrayEquals(
                tokenizer.tokenize("1 + 2 - 3 * 4 / 5").toTypedArray(),
                arrayOf(
                        NumberToken(1),
                        OperationToken(ADD),
                        NumberToken(2),
                        OperationToken(SUB),
                        NumberToken(3),
                        OperationToken(MUL),
                        NumberToken(4),
                        OperationToken(DIV),
                        NumberToken(5)
                ))
    }

    @Test
    fun `tokenize should process braces`() {
        assertArrayEquals(
                tokenizer.tokenize("(1 + (2))").toTypedArray(),
                arrayOf(
                        BraceToken(LEFT),
                        NumberToken(1),
                        OperationToken(ADD),
                        BraceToken(LEFT),
                        NumberToken(2),
                        BraceToken(RIGHT),
                        BraceToken(RIGHT)
                ))
    }
}
