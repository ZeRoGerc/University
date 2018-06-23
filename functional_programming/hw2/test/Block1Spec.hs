{-# LANGUAGE ExplicitNamespaces #-}

module Block1Spec where

import           Block1          (ArithmeticError (DivisionByZero, Unknown),
                                  Expr (Add, Const, Div, Mult, Pow, Subst),
                                  apply, eval, orElse)
import           Test.Hspec
import           Test.QuickCheck

spec :: Spec
spec =
  describe "Block1" $
    describe "eval" $ do
      it "const" $ eval (Const 5) `shouldBe` Right 5
      it "add" $ eval (Add (Const 10) (Const 5)) `shouldBe` Right 15
      it "substr" $ eval (Subst (Const 20) (Const 5)) `shouldBe` Right 15
      it "mult" $ eval (Mult (Const 2) (Const 4)) `shouldBe` Right 8
      describe "div" $ do
        it "regular" $ eval (Div (Const 10) (Const 3)) `shouldBe` Right 3
        it "by zero" $ eval (Div (Const 1) (Const 0)) `shouldBe` Left DivisionByZero
      it "pow" $ eval (Pow (Const 2) (Const 4)) `shouldBe` Right 16
