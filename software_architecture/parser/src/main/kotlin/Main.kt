
import token.Tokenizer
import visitor.CalcVisitor
import visitor.ParserVisitor
import java.util.*

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val raw = Scanner(System.`in`).nextLine()
            val t = Tokenizer().tokenize(raw)

            val tokens = ParserVisitor().convertToPolish(t)
            System.out.println(visitor.PrintVisitor().print(tokens))
            System.out.println("Result is ${CalcVisitor().calc(tokens)}")
        }
    }
}