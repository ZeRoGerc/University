module Block4Task2
  ( zeroOrMore
  , oneOrMore
  , spaces
  , ident
  , parseSExpr
  , SExpr(..)
  , Atom(..)
  , Ident
  ) where

import           Block4Task1         (Parser, abParser, abParser_, char,
                                      intOrUppercase, intPair, posInt,
                                      runParser, satisfy)
import           Control.Applicative (Alternative, (<|>))
import           Control.Monad       ((>=>))
import           Data.Char           (isAlpha, isAlphaNum, isSpace, isUpper)
import           Prelude

-- Exercise 1 --
zeroOrMore :: Parser a -> Parser [a]
zeroOrMore p = (pure (:) <*> p <*> zeroOrMore p) <|> pure []

oneOrMore :: Parser a -> Parser [a]
oneOrMore p = pure (:) <*> p <*> zeroOrMore p

-- Exercise 2 --
spaces :: Parser String
spaces = zeroOrMore (satisfy isSpace)

ident :: Parser String
ident = pure (:) <*> satisfy isAlpha <*> zeroOrMore (satisfy isAlphaNum)

-- Exercise 3 --
type Ident = String

data Atom
  = N Integer
  | I Ident
  deriving (Show, Eq)

data SExpr
  = A Atom
  | Comb [SExpr]
  deriving (Show, Eq)

parseSExpr :: Parser SExpr
parseSExpr = (Comb <$> parseComb) <|> (A <$> parseAtom)

parseAtom :: Parser Atom
parseAtom = spaces *> ((N <$> posInt) <|> (I <$> ident)) <* spaces

parseComb :: Parser [SExpr]
parseComb = spaces *> satisfy (== '(') *> spaces *> zeroOrMore parseSExpr <* spaces <* satisfy (== ')') <* spaces
