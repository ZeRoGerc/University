{-# LANGUAGE ConstraintKinds #-}

module Interpreter
  ( interpret
  ) where

import           Base                 (Expr (..), InterpretState,
                                       InterpreterException (..),
                                       Statement (..))
import           Console              (logToConsole, readFromConsole)
import           Expressions          (eval)
import           Variables            (modify, new)

import           Control.Applicative  ((<|>))
import           Control.Exception    (Exception)
import           Control.Monad        (liftM2, (>>=))
import           Control.Monad.Catch  (MonadThrow, throwM)
import           Control.Monad.Cont   (MonadCont, MonadIO, mapCont, runCont)
import           Control.Monad.Except (lift)
import           Control.Monad.Reader (ReaderT, ask, runReaderT)
import           Control.Monad.State  (MonadState, StateT (..), evalStateT,
                                       execStateT, get, put, runStateT, state,
                                       withStateT)
import qualified Data.Map.Strict      as M (Map, delete, empty, insert,
                                            intersection, lookup, singleton)

interpret :: (MonadIO m, MonadThrow m) => [Statement] -> StateT InterpretState m ()
interpret [] = StateT $ \cur -> return ((), cur)
interpret (x:xs) = do
  st <- get
  case x of
    VariableDeclaration name expr -> runReaderT (eval expr) st >>= new name
    VariableUpdate name expr -> runReaderT (eval expr) st >>= modify name
    ConsoleOut expr -> logToConsole expr
    ConsoleIn name -> readFromConsole name
    ForLoop id fromE toE statements ->
      liftM2 (,) (runReaderT (eval fromE) st) (runReaderT (eval toE) st) >>= \(f, t) -> for id f t statements
  interpret xs

for :: (MonadIO m, MonadThrow m) => String -> Int -> Int -> [Statement] -> StateT InterpretState m ()
for i from to statements
  | from == to = state $ \s -> ((), s)
  | otherwise = do
    before <- get
    withStateT (M.insert i from) (interpret statements)
    after <- get
    put $ M.intersection after before
    for i (from + 1) to statements
