module Base
  ( InterpreterException(..)
  , InterpretState
  , Interpreter
  , Statement(..)
  , Expr(..)
  ) where

import           Control.Exception      (Exception)
import           Control.Monad.Catch    (MonadThrow)
import           Control.Monad.IO.Class (MonadIO)
import           Control.Monad.State    (StateT)
import qualified Data.Map.Strict        as M (Map, insert)

type InterpretState = M.Map String Int

type Interpreter = StateT InterpretState IO ()

data InterpreterException
  = DivisionByZero
  | NoSuchVariableInScope
  | VariableWasAlreadyDeclared
  deriving (Eq, Show)

instance Exception InterpreterException

data Statement
  = VariableDeclaration String
                        Expr
  | VariableUpdate String
                   Expr
  | ConsoleOut Expr
  | ConsoleIn String
  | ForLoop String
            Expr
            Expr
            [Statement]
  deriving (Show, Eq)

data Expr
  = Lit Int
  | Var String
  | Add Expr
        Expr
  | Sub Expr
        Expr
  | Mul Expr
        Expr
  | Div Expr
        Expr
  | Let String
        Expr
        Expr
  deriving (Show, Eq)
