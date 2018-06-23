module Block4Task1
  ( Parser(..)
  , satisfy
  , char
  , posInt
  , abParser
  , abParser_
  , intPair
  , intOrUppercase
  ) where

import           Control.Applicative (Alternative, empty, (<|>))
import           Control.Monad       ((>=>))
import           Data.Char           (isDigit, isUpper)
import           Prelude

newtype Parser a = Parser
  { runParser :: String -> Maybe (a, String)
  }

satisfy :: (Char -> Bool) -> Parser Char
satisfy p = Parser f
  where
    f [] = Nothing -- fail on the empty input
    f (x:xs)
      | p x = Just (x, xs)
      | otherwise = Nothing -- otherwise, fail

char :: Char -> Parser Char
char c = satisfy (== c)

posInt :: Parser Integer
posInt = Parser f
  where
    f xs
      | null ns = Nothing
      | otherwise = Just (read ns, rest)
      where
        (ns, rest) = span isDigit xs

first :: (a -> b) -> (a, c) -> (b, c)
first f (x, y) = (f x, y)

instance Functor Parser where
  fmap f (Parser t) = Parser $ \raw -> fmap (first f) (t raw)

instance Applicative Parser where
  pure a = Parser $ \s -> Just (a, s)
  (Parser p1) <*> (Parser p2) = Parser (p1 >=> \(f, rm) -> p2 rm >>= \res -> Just (first f res))

abParser :: Parser (Char, Char)
abParser = pure (,) <*> char 'a' <*> char 'b'

abParser_ :: Parser ()
abParser_ = pure (\x y -> ()) <*> char 'a' <*> char 'b'

intPair :: Parser [Integer]
intPair = pure (\x y z -> [x, z]) <*> posInt <*> char ' ' <*> posInt

instance Alternative Parser where
  empty = Parser $ const Nothing
  p1 <|> p2 = Parser $ \s -> runParser p1 s <|> runParser p2 s

intOrUppercase :: Parser ()
intOrUppercase = (pure (const ()) <*> posInt) <|> (pure (const ()) <*> satisfy isUpper)
--
--               fmap id          =     id
--               fmap id (Parser t)
--[fmap]         Parser $ \raw -> fmap (first id) (t raw)
--[first]        Parser $ \raw -> fmap \(x,y) -> (id x, y) (t raw)
--[id]           Parser $ \raw -> fmap id (t raw)
--[functor law]  Parser $ \raw -> id (t raw)
--[id]           Parser $ \raw - t raw
--[reduction]    Parser t
