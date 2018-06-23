module Block4Task2Spec where

import           Block4Task1     (Parser (Parser, runParser), abParser,
                                  abParser_, char, intOrUppercase, intPair,
                                  posInt, satisfy)
import           Block4Task2     (Atom (..), Ident, SExpr (..), ident,
                                  oneOrMore, parseSExpr, spaces, zeroOrMore)
import           Data.Char       (isUpper)
import           Test.Hspec
import           Test.QuickCheck

spec :: Spec
spec =
  describe "Block4Task2" $ do
    describe "zeroOrMore" $ do
      it "zero" $ runParser (zeroOrMore (satisfy isUpper)) "asd" `shouldBe` Just ("", "asd")
      it "one" $ runParser (zeroOrMore (satisfy isUpper)) "Asd" `shouldBe` Just ("A", "sd")
      it "many" $ runParser (zeroOrMore (satisfy isUpper)) "ASD1" `shouldBe` Just ("ASD", "1")
    describe "oneOrMore" $ do
      it "zero" $ runParser (oneOrMore (satisfy isUpper)) "asd" `shouldBe` Nothing
      it "one" $ runParser (oneOrMore (satisfy isUpper)) "Asd" `shouldBe` Just ("A", "sd")
      it "many" $ runParser (oneOrMore (satisfy isUpper)) "ASD1" `shouldBe` Just ("ASD", "1")
    describe "spaces" $ do
      it "zero" $ runParser spaces "asd" `shouldBe` Just ("", "asd")
      it "one" $ runParser spaces " asd" `shouldBe` Just (" ", "asd")
      it "many" $ runParser spaces "   asd" `shouldBe` Just ("   ", "asd")
    describe "ident" $ do
      it "fail" $ runParser ident "123" `shouldBe` Nothing
      it "oneLetter" $ runParser ident "a 123" `shouldBe` Just ("a", " 123")
      it "lettersAndDigits" $ runParser ident "x1 x2" `shouldBe` Just ("x1", " x2")
    describe "parseSExpr" $ do
      it "5" $ runParser parseSExpr "5" `shouldBe` Just ((A . N) 5, "")
      it "foo3" $ runParser parseSExpr "foo3" `shouldBe` Just ((A . I) "foo3", "")
      it "(bar (foo) 3 5 874)" $
        runParser parseSExpr "(bar (foo) 3)" `shouldBe` Just (Comb [(A . I) "bar", Comb [(A . I) "foo"], (A . N) 3], "")
      it "( lots of ( spaces in ) this ( one ) )" $
        runParser parseSExpr "( lots of ( spaces in ) this ( one ) )" `shouldBe`
        Just
          ( Comb
              [ (A . I) "lots"
              , (A . I) "of"
              , Comb [(A . I) "spaces", (A . I) "in"]
              , (A . I) "this"
              , Comb [(A . I) "one"]
              ]
          , "")
