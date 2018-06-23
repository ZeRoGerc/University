module Block2 where

import           Control.Applicative (liftA2)
import           Control.Monad       (fmap, liftM2, replicateM, return, (>>=))
import           Data.List           (head, tail, tails, (++))
import           Prelude             (Int, foldr, pure, (-), (.), (>))

-- Task 1 --
bin :: Int -> [[Int]]
bin n = replicateM n [0, 1]

-- Task 2 --
combinations :: Int -> Int -> [[Int]]
combinations n 0 = pure []
combinations n k = foldr (addAll n (k - 1)) [] (combinations n (k - 1))
  where
    addAll :: Int -> Int -> [Int] -> [[Int]] -> [[Int]]
    addAll n k [] acc         = fmap (: []) [k .. n] ++ acc
    addAll n k cur@(x:xs) acc = fmap (: cur) [k .. (x - 1)] ++ acc

-- Task 3 --
permutations :: [a] -> [[a]]
permutations [] = return []
permutations (x:xs) = permutations xs >>= insert x
  where
    insert :: a -> [a] -> [[a]]
    insert x []     = [[x]]
    insert x (y:ys) = (x : y : ys) : fmap (y :) (insert x ys)
