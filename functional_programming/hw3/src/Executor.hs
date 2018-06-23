module Executor
  ( mainExec
  , execFromFile
  , fibFile
  , factFile
  , testFile
  ) where

import           Base                  (Expr (..), InterpretState,
                                        Statement (..))
import           Interpreter           (interpret)

import           Control.Monad.State   (evalStateT)
import qualified Data.Map.Strict       as M (empty)
import           Parser                (statementsParser)
import           System.Environment    (getArgs, withArgs)
import           Text.Megaparsec       (runParser)
import           Text.Megaparsec.Error (Dec, ParseError, parseErrorPretty)

basePath = "src/programs/"

fibFile = basePath ++ "fib.txt"

factFile = basePath ++ "fact.txt"

testFile = basePath ++ "test.txt"

mainExec :: IO ()
mainExec = do
  args <- getArgs
  execFromFile (head args)

execFromFile :: String -> IO ()
execFromFile file = do
  p <- parseFromFile file
  case p of
    Left err     -> fail (parseErrorPretty err)
    Right parsed -> runInterpret parsed

parseFromFile :: String -> IO (Either (ParseError Char Dec) [Statement])
parseFromFile file = runParser statementsParser file <$> readFile file

runInterpret :: [Statement] -> IO ()
runInterpret statements = evalStateT (interpret statements) M.empty
