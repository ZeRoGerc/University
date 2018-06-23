module Lexer
  ( lexeme
  , symbol
  , reservedWord
  , variableName
  , number
  , parens
  , line
  ) where

import           Text.Megaparsec            (alphaNumChar, letterChar, many,
                                             newline, notFollowedBy, numberChar,
                                             parseTest, some, space, string, char,
                                             try, (<|>))
import           Text.Megaparsec.Combinator (between)
import qualified Text.Megaparsec.Lexer      as L (lexeme, symbol)
import           Text.Megaparsec.String     (Parser)

line :: Parser a -> Parser a
line p = space *> p

parens :: Parser a -> Parser a
parens = between (symbol "(") (symbol ")")

variableName :: Parser String
variableName = lexeme . try $ ((:) <$> letterChar <*> many alphaNumChar) >>= check
  where
    check x =
      if x `elem` reserved
        then fail $ "keyword " ++ show x ++ " cannot be a variable Name"
        else return x

number :: Parser Int
number = read <$> lexeme (some numberChar)

reservedWord :: String -> Parser ()
reservedWord w = lexeme (string w *> notFollowedBy alphaNumChar)

reserved :: [String]
reserved = ["mut", "let", "in", "for", "to"]

lexeme :: Parser a -> Parser a
lexeme = L.lexeme space

symbol :: String -> Parser String
symbol = L.symbol space
