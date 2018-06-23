module Block4Task1Spec where

import           Block4Task1     (Parser (Parser, runParser), abParser,
                                  abParser_, char, intOrUppercase, intPair,
                                  posInt, satisfy)
import           Test.Hspec
import           Test.QuickCheck

spec :: Spec
spec =
  describe "Block4Task1" $ do
    describe "abParser" $ do
      it "testOk" $ runParser abParser "abcde" `shouldBe` Just (('a', 'b'), "cde")
      it "testFail" $ runParser abParser "fff" `shouldBe` Nothing
    describe "abParser_" $ do
      it "testOk" $ runParser abParser_ "abcde" `shouldBe` Just ((), "cde")
      it "testFail" $ runParser abParser_ "fff" `shouldBe` Nothing
    describe "intPair" $ do
      it "testOk" $ runParser intPair "12 34   " `shouldBe` Just ([12, 34], "   ")
      it "testFail" $ runParser intPair "asfas" `shouldBe` Nothing
    describe "intOrUppercase" $ do
      it "testOkInt" $ runParser intOrUppercase "12asb" `shouldBe` Just ((), "asb")
      it "testOKUppercase" $ runParser intOrUppercase "Asc" `shouldBe` Just ((), "sc")
      it "testFail" $ runParser intOrUppercase "sad" `shouldBe` Nothing


