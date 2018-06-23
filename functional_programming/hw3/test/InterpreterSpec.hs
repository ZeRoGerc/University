module InterpreterSpec
  ( spec
  ) where

import           Test.Hspec
import           Test.QuickCheck

import           Control.Monad.Catch  (MonadThrow)
import           Control.Monad.Reader (ReaderT, runReaderT)
import           Control.Monad.State  (execStateT)
import qualified Data.Map.Strict      as M (Map, empty, insert, singleton)

import           Base                 (Expr (..), InterpretState,
                                       InterpreterException (..),
                                       Statement (..))
import           Expressions          (eval)
import           Interpreter          (interpret)

fact :: Int -> [Statement]
fact n =
  [ VariableDeclaration "x0" (Lit 0)
  , VariableDeclaration "x1" (Lit 1)
  , ForLoop
      "i"
      (Lit 1)
      (Lit n)
      [ VariableDeclaration "temp" (Add (Var "x0") (Var "x1"))
      , VariableUpdate "x0" (Var "x1")
      , VariableUpdate "x1" (Var "temp")
      ]
  ]

factState :: Int -> Int -> InterpretState
factState x0 x1 = M.insert "x1" x1 (M.singleton "x0" x0)

spec :: Spec
spec =
  describe "Interpreter" $
  describe "eval" $ do
    it "single decalration" $
      execStateT (interpret [VariableDeclaration "x" (Lit 10)]) M.empty `shouldReturn` M.singleton "x" 10
    it "declaration and update" $
      execStateT (interpret [VariableDeclaration "x" (Lit 10), VariableUpdate "x" (Lit 5)]) M.empty `shouldReturn`
      M.singleton "x" 5
    it "declaration and update with same variable on the right" $
      execStateT (interpret [VariableDeclaration "x" (Lit 10), VariableUpdate "x" (Add (Var "x") (Lit 5))]) M.empty `shouldReturn`
      M.singleton "x" 15
    describe "fact" $ do
      it "1" $ execStateT (interpret $ fact 1) M.empty `shouldReturn` factState 0 1
      it "10" $ execStateT (interpret $ fact 10) M.empty `shouldReturn` factState 34 55
