module Block1 where

import Control.Category (Category, (.), id)
import Control.Monad ((>=>), (>>=))
import Data.Either (Either(..))
import Data.Maybe (Maybe(..), fromMaybe, isJust)
import GHC.Base ((<|>))
import Prelude
       (Bool, Enum, Eq, Int, Show, ($), (*), (+), (-), (==), (^), div)

-- Task 1 --
data ArithmeticError
  = DivisionByZero
  | Unknown
  deriving (Eq, Enum, Show)

data Expr
  = Const Int
  | Add Expr
        Expr
  | Subst Expr
          Expr
  | Mult Expr
         Expr
  | Div Expr
        Expr
  | Pow Expr
        Expr
  deriving (Show)

eval :: Expr -> Either ArithmeticError Int
eval (Const x) = Right x
eval (Add expr1 expr2) = eval expr1 >>= \x -> eval expr2 >>= \y -> Right (x + y)
eval (Subst expr1 expr2) = eval expr1 >>= \x -> eval expr2 >>= \y -> Right (x - y)
eval (Mult expr1 expr2) = eval expr1 >>= \x -> eval expr2 >>= \y -> Right (x * y)
eval (Div expr1 expr2) =
  eval expr1 >>= \x ->
    eval expr2 >>= \y ->
      if y == 0
        then Left DivisionByZero
        else Right (x `div` y)
eval (Pow expr1 expr2) = eval expr1 >>= \x -> eval expr2 >>= \y -> Right (x ^ y)

-- Task 2 --
data a ~> b
  = Partial (a -> Maybe b) -- a partial function
  | Defaulted (a ~> b)
              b -- a partial function with a default value

partial :: (a -> Maybe b) -> a ~> b
partial = Partial

total :: (a -> b) -> a ~> b
total f = Partial $ \x -> Just $ f x

apply :: (a ~> b) -> a -> Maybe b
apply (Partial f) x = f x
apply (Defaulted f y) x = Just $ fromMaybe y (apply f x)

applyOrElse :: (a ~> b) -> a -> b -> b
applyOrElse f x y = fromMaybe y (apply f x)

withDefault :: (a ~> b) -> b -> (a ~> b)
withDefault (Defaulted f y) = Defaulted f
withDefault partial = Defaulted partial

isDefinedAt :: (a ~> b) -> a -> Bool
isDefinedAt f x = isJust $ apply f x

orElse :: (a ~> b) -> (a ~> b) -> a ~> b
orElse f1 f2 = Partial $ \x -> apply f1 x <|> apply f2 x

instance Category (~>) where
  id = Partial Just
  p1 . p2 = Partial $ apply p2 >=> apply p1
  --
  -- Left identity: id . f
  --[.]                  Partial $ apply f >=> apply id
  --[id]                 Partial $ apply f >=> apply (Partial Just)
  --[apply]              Partial $ apply f >=> Just
  --[>=>]                Partial $ \x -> apply f x >>= Just
  --[Maybe monad]        Partial $ \x -> apply f x
  --Let's prove: apply (id . f) = apply f
  --                     apply (Partial $ \x -> apply f x) = apply f
  --[apply]              \x -> apply f x
  --[reduction]          apply f
  --
  -- Right identity: f . id
  --[.]          Partial $ apply id >=> apply f
  --[id]         Partial $ apply (Partial Just) >=> apply f
  --[apply]      Partial $ Just >=> apply f
  --[>=>]        Partial \x -> Just x >>= apply f
  --[Maybe monad] Partial $ \x -> apply f x
  --[...] Copy of previous case
  -- 
  -- Associativity
  -- (f . g) . h = f . (g . h)
  --
  -- Left:
  --[.]                (Partial $ apply g >=> apply f) . h
  --[>=>]              (Partial $ \y -> apply g y >>= apply f) . h
  --[.]                Partial $ apply h >=> (apply (Partial $ \y -> apply g y >>= apply f))
  --[apply]            Partial $ apply h >=> (\y -> apply g y >>= apply f)
  --[>=>]              Partial $ (\z -> apply h z >>= (\y -> apply g y >>= apply f))
  --[Monad3]           Partial $ \z -> (apply h z >>= apply g) >>= apply f
  --
  -- Right:
  --[.]                f . (Partial $ apply h >=> apply g)
  --[>=>]              f . (Partial $ \z -> apply h z >>= apply g)
  --[.]                Partial $ (apply (Partial $ \z -> apply h z >>= apply g)) >=> apply f
  --[apply]            Partial $ (\z -> apply h z >>= apply g) >=> apply f
  --[>=>]              Partial $ (\z1 -> (\z -> apply h z >>= apply g) z1 >>= apply f)
  --[reduction]        Partial $ \z1 -> (apply h z1 >>= apply g) >>= apply f
  --[rename]           Partial $ \z -> (apply h z >>= apply g) >>= apply f