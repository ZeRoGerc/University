module TemplateHaskellTaskMod
  ( lengthT
  ) where

import           Data.Data          (Data, gfoldl)
import           Data.Functor.Const (Const (..))

-- Для того чтобы не передавать аргумент числом можно воспользоваться функцией gfold.
-- Однако у нее есть свои ограничения (она работает только до определнной длины)
lengthT :: Data a => a -> Int
lengthT = getConst . gfoldl (\(Const c) _ -> Const (c + 1)) (const 0)
-- Напишем функцию которая будет получать длину кортежа и затем вызывать chooseByIndices
-- Однако cгенерированная функция не может принимать произвольную Data =(
--chooseByIndicesGeneral :: (Data a) => [Int] -> a -> Q Exp
--chooseByIndicesGeneral indices tuple = do
--  let len = lengthT tuple
--  [| $(chooseByIndices len indices) tuple |]
