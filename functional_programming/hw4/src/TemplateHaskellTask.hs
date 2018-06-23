module TemplateHaskellTask
  ( chooseByIndices
  , MyData(..)
  ) where

import qualified Data.Text as T
import Language.Haskell.TH
       (Exp(..), Q(..), lamE, mkName, newName, tupE, tupP, varE, varP,
        wildP)
import TemplateHaskellTextShow (textShowInstance)

chooseByIndices :: Int -> [Int] -> Q Exp
chooseByIndices n indices =
  lamE [tupP [varP $ mkName ('x' : show i) | i <- [0 .. (n - 1)]]] (tupE $ createTupleArgs indices)
  where
    createTupleArgs :: [Int] -> [Q Exp]
    createTupleArgs = map (\x -> varE (mkName ('x' : show x)))

data MyData = MyData
  { foo :: String
  , bar :: Int
  }

textShowInstance ''MyData

test = MyData {foo = "bar", bar = 5}