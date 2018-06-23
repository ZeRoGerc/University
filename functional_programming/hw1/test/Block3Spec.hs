module Block3Spec
  ( spec
  ) where

import           Block3
import           Control.Exception (evaluate)
import           Test.Hspec

spec :: Spec
spec = do
  describe "nextDay" $ it "should work" $ nextDay Monday `shouldBe` Tuesday
  describe "afterDays" $ it "should work" $ afterDays Friday 3 `shouldBe` Monday
  describe "isWeekend" $ do
    it "should work for Monday" $ isWeekend Monday `shouldBe` False
    it "should work for Tuesday" $ isWeekend Tuesday `shouldBe` False
    it "should work for Wednesday" $ isWeekend Wednesday `shouldBe` False
    it "should work for Thursday" $ isWeekend Thursday `shouldBe` False
    it "should work for Friday" $ isWeekend Friday `shouldBe` False
    it "should work for Saturday" $ isWeekend Saturday `shouldBe` True
    it "should work for Sunday" $ isWeekend Sunday `shouldBe` True
  describe "daysToParty" $ it "should work" $ daysToParty Monday `shouldBe` 4
  describe "fight" $
    it "should work" $
    fight (Knight "Arthur" 100 100) (Monster "Mighty Dragon" 10 1000) `shouldBe`
    "Arthur killed Mighty Dragon in 10 rounds!"
  describe "vectorLen" $ it "should work" $ vectorLen (Vector2D 3 4) `shouldBe` 5
  describe "vectorSum" $ do
    it "should work for 2D" $ vectorSum (Vector2D 1 2) (Vector2D 3 4) `shouldBe` Vector2D 4 6
    it "should work for 3D" $ vectorSum (Vector3D 1 2 0) (Vector3D 3 4 5) `shouldBe` Vector3D 4 6 5
    it "should work for mixed" $ vectorSum (Vector2D 1 2) (Vector3D 3 4 5) `shouldBe` Vector3D 4 6 5
  describe "scalarProduct" $ do
    it "should work for 2D" $ scalarProduct (Vector2D 1 2) (Vector2D 3 4) `shouldBe` 11
    it "should work for 3D" $ scalarProduct (Vector3D 1 2 0) (Vector3D 3 4 5) `shouldBe` 11
    it "should work for mixed" $ scalarProduct (Vector2D 1 2) (Vector3D 3 4 5) `shouldBe` 11
  describe "vectorDistance" $ it "should work" $ vectorDistance (Vector2D 0 0) (Vector2D 3 4) `shouldBe` 5
  describe "vectorProduct" $
    it "should work" $ vectorProduct (Vector2D 1 2) (Vector3D 1 1 1) `shouldBe` Vector3D 2 (-1) (-1)
  describe "Nat" $ do
    let n0 = Z
    let n1 = S Z
    let n2 = S n1
    let n3 = S n2
    it "should plus" $ n1 + n2 `shouldBe` n3
    it "should multiply" $ n1 * n2 `shouldBe` n2
    it "should substract" $ n3 - n2 `shouldBe` n1
    it "should construct from integer" $ 3 `shouldBe` n3
    it "should convert to int" $ natToInteger n3 `shouldBe` 3
    it "should ord" $ n0 <= n1 && n2 >= n1 `shouldBe` True
  describe "Tree" $ do
    let t0 = Leaf
    let t1 = Node 40 Leaf Leaf
    let t2 = Node 20 (Node 15 Leaf Leaf) Leaf
    let t3 = Node 20 (Node 15 Leaf Leaf) t1
    it "treeIsEmpty" $ treeIsEmpty t0 `shouldBe` True
    it "findElement" $ findElement t2 20 `shouldBe` True
    it "insertEmelent" $ insertElement t2 40 `shouldBe` t3
    it "fromList" $ fromList [20, 15] `shouldBe` t2
