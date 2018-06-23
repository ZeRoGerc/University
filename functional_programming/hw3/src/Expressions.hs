module Expressions
  ( Expr(..)
  , eval
  ) where

import           Base                 (Expr (..), InterpretState,
                                       InterpreterException (..))

import           Control.Exception    (Exception)
import           Control.Monad        (liftM2)
import           Control.Monad.Catch  (MonadThrow, throwM)
import           Control.Monad.Except (lift)
import           Control.Monad.Reader (ReaderT, ask, runReaderT)
import qualified Data.Map.Strict      as M (Map, empty, insert, lookup,
                                            singleton)

eval :: MonadThrow m => Expr -> ReaderT InterpretState m Int
eval (Lit x) = return x
eval (Var name) = do
  env <- ask
  case M.lookup name env of
    Nothing -> throwM NoSuchVariableInScope
    Just x  -> return x
eval (Add e1 e2) = liftM2 (+) (eval e1) (eval e2)
eval (Sub e1 e2) = liftM2 (-) (eval e1) (eval e2)
eval (Mul e1 e2) = liftM2 (*) (eval e1) (eval e2)
eval (Div e1 e2) =
  eval e2 >>= \y ->
    if y == 0
      then lift (throwM DivisionByZero)
      else eval e1 >>= \x -> return (x `div` y)
eval (Let name v e) = do
  env <- ask
  vr <- eval v
  runReaderT (eval e) (M.insert name vr env)
