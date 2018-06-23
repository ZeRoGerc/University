module Parser
  ( statementsParser
  , testParser
  ) where

import           Base                   (Expr (..), Statement (..))
import           Lexer                  (lexeme, line, number, parens,
                                         reservedWord, symbol, variableName)

import           Text.Megaparsec        (many, parseTest, try, (<|>))
import           Text.Megaparsec.Expr   (Operator (..), makeExprParser)
import           Text.Megaparsec.String (Parser)

testParser = parseTest statementsParser

-- Statements --
statementsParser :: Parser [Statement]
statementsParser = many (line statementParser)

statementParser :: Parser Statement
statementParser =
  forLoopParser <|> consoleInParser <|> consoleOutParser <|> variableDeclarationParser <|> variableUpdateParser

variableDeclarationParser :: Parser Statement
variableDeclarationParser = do
  reservedWord "mut"
  name <- variableName
  symbol "="
  expr <- exprParser
  return $ VariableDeclaration name expr

variableUpdateParser :: Parser Statement
variableUpdateParser = do
  name <- variableName
  symbol "="
  expr <- exprParser
  return $ VariableUpdate name expr

consoleOutParser :: Parser Statement
consoleOutParser = do
  symbol "<"
  expr <- exprParser
  return $ ConsoleOut expr

consoleInParser :: Parser Statement
consoleInParser = do
  symbol ">"
  name <- variableName
  return $ ConsoleIn name

forLoopParser :: Parser Statement
forLoopParser = do
  reservedWord "for"
  id <- variableName
  symbol "="
  from <- exprParser
  reservedWord "to"
  to <- exprParser
  symbol "{"
  statements <- statementsParser
  symbol "}"
  return $ ForLoop id from to statements

-- Expressions --
exprParser :: Parser Expr
exprParser = makeExprParser termParser operators

operators :: [[Operator Parser Expr]]
operators =
  [[InfixL (Mul <$ symbol "*"), InfixL (Div <$ symbol "/")], [InfixL (Add <$ symbol "+"), InfixL (Sub <$ symbol "-")]]

termParser :: Parser Expr
termParser = try parensExpr <|> Var <$> variableName <|> Lit <$> number

parensExpr :: Parser Expr
parensExpr = try (parens letParser) <|> parens exprParser

letParser :: Parser Expr
letParser = do
  reservedWord "let"
  varName <- variableName
  symbol "="
  varVal <- exprParser
  reservedWord "in"
  expr <- exprParser
  return $ Let varName varVal expr
