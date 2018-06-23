module Block5Spec
  ( spec
  ) where

import           Block3
import           Block4
import           Block5
import           Control.Exception (evaluate)
import           Data.Foldable
import           Data.Semigroup
import           Test.Hspec

spec :: Spec
spec = do
  describe "maybeConcat" $
    it "should work" $ maybeConcat [Just [1, 2, 3], Nothing, Nothing, Just [4, 5]] `shouldBe` [1, 2, 3, 4, 5]
  describe "Semigroup for NonEmpty" $
    it "should work" $ ((1 :| [2 .. 5]) <> (10 :| [20])) == (1 :| [2, 3, 4, 5, 10, 20])
  describe "Semigroup for Identity" $ do
    let x = 1 :| [2]
    let y = 10 :| [20, 30]
    let ix = Identity x
    let iy = Identity y
    it "<> should work" $ ix <> iy `shouldBe` Identity (1 :| [2, 10, 20, 30])
  describe "Monoid for Identity" $ do
    let x = [1, 2, 3]
    let y = [4, 5, 6]
    let ix = Identity x
    let iy = Identity y
    it "mempty" $ (mempty :: Identity [Int]) `shouldBe` Identity []
    it "mappend should work" $ mappend ix iy `shouldBe` Identity [1, 2, 3, 4, 5, 6]
  describe "Semigroup for Tree" $ do
    let x = Node 10 (Node 5 Leaf Leaf) Leaf
    let y = Node 100 Leaf Leaf
    it "<> should work" $ x <> y `shouldBe` Node 10 (Node 5 Leaf Leaf) (Node 100 Leaf Leaf)
  describe "Monoid for Tree" $ do
    let x = Node 10 (Node 5 Leaf Leaf) Leaf
    let y = Node 100 Leaf Leaf
    it "mempty" $ (mempty :: Tree Int) `shouldBe` Leaf
    it "mappend should work" $ (x `mappend` y) `shouldBe` Node 10 (Node 5 Leaf Leaf) (Node 100 Leaf Leaf)
