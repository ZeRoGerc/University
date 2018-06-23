module Block2Spec where

import           Block2          (bin, combinations, permutations)
import           Test.Hspec
import           Test.QuickCheck

spec :: Spec
spec =
  describe "Block2" $ do
    describe "bin" $ do
      it "0" $ bin 0 `shouldBe` [[]]
      it "1" $ bin 1 `shouldBe` [[0], [1]]
      it "2" $ bin 2 `shouldBe` [[0, 0], [0, 1], [1, 0], [1, 1]]
      it "3" $ bin 3 `shouldBe` [[0, 0, 0], [0, 0, 1], [0, 1, 0], [0, 1, 1], [1, 0, 0], [1, 0, 1], [1, 1, 0], [1, 1, 1]]
    describe "combinations" $ do
      it "0 0" $ combinations 0 0 `shouldBe` [[]]
      it "9 0" $ combinations 9 0 `shouldBe` [[]]
      it "1 1" $ combinations 1 1 `shouldBe` [[0], [1]]
      it "4 2" $ combinations 4 2 `shouldBe` [[1, 2], [1, 3], [2, 3], [1, 4], [2, 4], [3, 4]]
    describe "permutations" $ do
      it "[1]" $ permutations [1] `shouldBe` [[1]]
      it "[1,2]" $ permutations [1, 2] `shouldBe` [[1, 2], [2, 1]]
      it "[1,9,2]" $
        permutations [1, 9, 2] `shouldBe` [[1, 9, 2], [9, 1, 2], [9, 2, 1], [1, 2, 9], [2, 1, 9], [2, 9, 1]]
