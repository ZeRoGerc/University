module Console
  ( logToConsole
  , readFromConsole
  ) where

import           Base                   (Expr (..), InterpretState (..))
import           Expressions            (eval)

import           Control.Applicative    ((<|>))
import           Control.Exception      (throwIO)
import           Control.Monad          (liftM2)
import           Control.Monad.Catch    (MonadThrow, throwM)
import           Control.Monad.Except   (lift)
import           Control.Monad.IO.Class (MonadIO, liftIO)
import           Control.Monad.Reader   (ReaderT, ask, runReaderT)
import           Control.Monad.State    (MonadState, StateT (..), evalStateT, modify,
                                         execStateT, get, put, runStateT, state)
import qualified Data.Map.Strict        as M (Map, empty, insert, lookup,
                                              singleton)

logToConsole :: (MonadIO m, MonadThrow m) => Expr -> StateT InterpretState m ()
logToConsole expr = do
  env <- get
  toLog <- runReaderT (eval expr) env
  liftIO $ print toLog

readFromConsole :: (MonadIO m, MonadThrow m) => String -> StateT InterpretState m ()
readFromConsole name = do
  number <- liftIO getLine
  modify $ \s -> M.insert name (read number) s
