module VariablesSpec
  ( spec
  ) where

import           Test.Hspec
import           Test.QuickCheck

import           Base                (InterpretState, InterpreterException (..))
import           Control.Monad.State (execStateT)
import qualified Data.Map.Strict     as M (empty, singleton)
import           Variables           (modify, new)

spec :: Spec
spec =
  describe "Variables" $ do
    describe "new" $ do
      it "simple" $ execStateT (new "x" 10) M.empty `shouldReturn` M.singleton "x" 10
      it "error" $ execStateT (new "x" 10) (M.singleton "x" 5) `shouldThrow` (== VariableWasAlreadyDeclared)
    describe "modify" $ do
      it "simple" $ execStateT (modify "x" 10) (M.singleton "x" 5) `shouldReturn` M.singleton "x" 10
      it "error" $ execStateT (modify "x" 10) M.empty `shouldThrow` (== NoSuchVariableInScope)
