module Block1Spec
  ( spec
  ) where

import           Block1
import           Control.Exception (evaluate)
import           Test.Hspec

spec :: Spec
spec = do
  describe "order3" $ it "should sort elements" $ order3 (6, 5, 4) `shouldBe` (4, 5, 6)
  describe "highestBit" $ do
    it "should work for 1" $ highestBit 1 `shouldBe` (1, 0)
    it "should work return same number if power of two" $ highestBit 16 `shouldBe` (16, 4)
    it "should work for regular number" $ highestBit 36 `shouldBe` (32, 5)
  describe "smartReplicate" $ it "should replicate" $ smartReplicate [1, 2, 3] `shouldBe` [1, 2, 2, 3, 3, 3]
