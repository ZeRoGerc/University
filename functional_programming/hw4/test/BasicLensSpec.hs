module BasicLensSpec
  ( spec
  ) where

import           Control.Exception (evaluate)
import           Test.Hspec

import           LensTask1         (over, set, view, _1, _2)

spec :: Spec
spec = do
  describe "_1" $ do
    it "set" $ set _1 2 (1, 'x') `shouldBe` (2, 'x')
    it "view" $ view _1 (1, 'x') `shouldBe` 1
    it "over" $ over _1 (+ 10) (1, 'x') `shouldBe` (11, 'x')
  describe "_2" $ do
    it "set" $ set _2 'y' (1, 'x') `shouldBe` (1, 'y')
    it "view" $ view _2 (1, 'x') `shouldBe` 'x'
    it "over" $ over _2 (const 'z') (1, 'x') `shouldBe` (1, 'z')
