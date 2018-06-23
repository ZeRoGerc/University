module ExpressionsSpec
  ( spec
  ) where

import           Test.Hspec
import           Test.QuickCheck

import           Base                 (Expr (..), InterpretState,
                                       InterpreterException (..))
import           Expressions          (eval)

import           Control.Monad.Catch  (MonadThrow)
import           Control.Monad.Reader (ReaderT, runReaderT)
import qualified Data.Map.Strict      as M (Map, empty, singleton)

test :: MonadThrow m => Expr -> M.Map String Int -> m Int
test exp = runReaderT (eval exp)

spec :: Spec
spec =
  describe "Expressions" $
  describe "eval" $ do
    it "Lit" $ test (Lit 10) M.empty `shouldReturn` 10
    it "Var" $ test (Var "x") (M.singleton "x" 15) `shouldReturn` 15
    it "Div error" $ test (Div (Lit 10) (Var "x")) (M.singleton "x" 0) `shouldThrow` (== DivisionByZero)
    it "Let" $ test (Let "x" (Lit 2) (Add (Var "x") (Lit 5))) M.empty `shouldReturn` 7
    it "Let error" $ test (Let "x" (Lit 2) (Var "y")) M.empty `shouldThrow` (== NoSuchVariableInScope)
