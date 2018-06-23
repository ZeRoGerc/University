module Block2Spec
  ( spec
  ) where

import           Block2
import           Control.Exception (evaluate)
import           Test.Hspec

spec :: Spec
spec = do
  describe "removeAt" $ do
    it "should return Maybe if in bound" $ removeAt 1 [1, 2, 3] `shouldBe` (Just 2, [1, 3])
    it "should return Nothing if index is negative" $ removeAt (-10) [1, 2, 3] `shouldBe` (Nothing, [1, 2, 3])
    it "should return Nothing if out of bound" $ removeAt 10 [1, 2, 3] `shouldBe` (Nothing, [1, 2, 3])
  describe "collectEvery" $ it "should work" $ collectEvery 3 [1 .. 8] `shouldBe` ([1, 2, 4, 5, 7, 8], [3, 6])
  describe "stringSum" $ do
    it "should work for empty" $ stringSum "" `shouldBe` 0
    it "should work for leading zero" $ stringSum "010 020 030" `shouldBe` 60
    it "should work for +-" $ stringSum "-1 +1 -100 +0" `shouldBe` (-100)
    it "should fail for non spaced +" $ evaluate (stringSum "1+1") `shouldThrow` anyErrorCall
    it "should work" $ stringSum "-100 10 -1 11" `shouldBe` -80
  describe "mergeSort" $ it "should sort" $ mergeSort [5, 4, 10, 8, 100] `shouldBe` [4, 5, 8, 10, 100]
