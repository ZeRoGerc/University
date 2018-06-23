module Variables
  ( new
  , modify
  ) where

import           Base                   (InterpretState,
                                         InterpreterException (..),
                                         Statement (..))

import           Control.Exception      (Exception)
import           Control.Monad.Catch    (MonadThrow, throwM)
import           Control.Monad.IO.Class (MonadIO)
import           Control.Monad.State    (StateT, get, gets, runStateT, state)
import           Data.Functor.Identity
import qualified Data.Map.Strict        as M (Map, empty, insert, lookup)

new :: (MonadIO m, MonadThrow m) => String -> Int -> StateT InterpretState m ()
new name value = do
  st <- get
  case M.lookup name st of
    Nothing -> state $ \s -> ((), M.insert name value s)
    Just x  -> throwM VariableWasAlreadyDeclared

modify :: (MonadIO m, MonadThrow m) => String -> Int -> StateT InterpretState m ()
modify name value = do
  st <- get
  case M.lookup name st of
    Nothing -> throwM NoSuchVariableInScope
    Just x  -> state $ \s -> ((), M.insert name value s)
