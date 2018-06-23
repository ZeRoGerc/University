module Block4Spec
  ( spec
  ) where

import           Block3
import           Block4
import           Control.Exception (evaluate)
import           Data.Foldable
import           Test.Hspec

spec :: Spec
spec = do
  describe "Foldable" $ do
    it "from to list should sort" $ (toList . fromList) [1, 4, 10, 5, 100, 3, 20] `shouldBe` [1, 3, 4, 5, 10, 20, 100]
    it "foldmap should work" $ foldMap (: []) (Node 10 Leaf (Node 20 Leaf Leaf)) `shouldBe` [10, 20]
  describe "splitOn" $ it "should work" $ splitOn '/' "path/to/file" `shouldBe` ["path", "to", "file"]
