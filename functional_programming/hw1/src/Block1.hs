module Block1 where

import           Data.Char
import           Data.List
import           Data.Maybe
import           Data.Semigroup

-- Task 1 --
order3 :: Ord a => (a, a, a) -> (a, a, a)
order3 (x, y, z) = (\[x, y, z] -> (x, y, z)) $ sort [x, y, z]

-- Task 2 --
highestBit :: Int -> (Int, Int)
highestBit lim = until (\(x, pow) -> x * 2 > lim) (\(x, pow) -> (x * 2, pow + 1)) (1, 0)

-- Task 3 --
smartReplicate :: [Int] -> [Int]
smartReplicate = foldr (\x -> (++) (replicate x x)) []

-- Task 4 --
contains :: Eq a => a -> [[a]] -> [[a]]
contains c = filter (isJust . find (== c))
