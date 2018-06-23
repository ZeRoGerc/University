module Block4 where

import           Block3
import           Data.Foldable
import           Data.List
import           Data.Semigroup

-- Task 1 --
instance Foldable Tree where
  foldr f s Leaf             = s
  foldr f s (Node value l r) = foldr f (f value (foldr f s r)) l
  foldMap f Leaf = mempty
  foldMap f (Node value l r) = (foldMap f l `mappend` f value) `mappend` foldMap f r

-- Task 2 --
splitOn :: Char -> String -> [String]
splitOn element s = fst $ foldl process ([], "") (s ++ [element])
  where
    process :: ([String], String) -> Char -> ([String], String)
    process (arr, cur) c
      | c == element = (arr ++ [cur], "")
      | otherwise = (arr, cur ++ [c])
