module Block2 where

import           Data.Char
import           Data.List
import           Data.Semigroup

-- Task 1 --
removeAt :: Int -> [a] -> (Maybe a, [a])
removeAt i list
  | i <= 0 || i > length list = (Nothing, list)
  | otherwise =
    let split = splitAt i list
    in (Just ((head . snd) split), fst split ++ (tail . snd) split)

-- Task 2 --
collectEvery :: Int -> [a] -> ([a], [a])
collectEvery k l = snd $ foldl helper (1, ([], [])) l
  where
    helper :: (Int, ([a], [a])) -> a -> (Int, ([a], [a]))
    helper (id, (l1, l2)) el
      | id == k = (1, (l1, l2 ++ [el]))
      | otherwise = (id + 1, (l1 ++ [el], l2))

-- Task 3 --
stringSum :: Num a => String -> Int
stringSum s = sum (map (readInt False 0) (words s))
  where
    readInt :: Bool -> Int -> String -> Int
    readInt b n ""          = n
    readInt False n ('+':s) = readInt False n s
    readInt False n ('-':s) = negate $ readInt False n s
    readInt b n (f:s)       = readInt True (n * 10 + digitToInt f) s

-- Task 4 --
mergeSort :: Ord a => [a] -> [a]
mergeSort [] = []
mergeSort [x] = [x]
mergeSort l = sortAndMerge (splitAt (length l `div` 2) l)
  where
    sortAndMerge :: Ord a => ([a], [a]) -> [a]
    sortAndMerge (l1, l2) = merge (mergeSort l1) (mergeSort l2)
    merge :: Ord a => [a] -> [a] -> [a]
    merge = helper []
      where
        helper acc l [] = acc ++ l
        helper acc [] l = acc ++ l
        helper acc (x:xs) (y:ys)
          | x < y = helper (acc ++ [x]) xs (y : ys)
          | otherwise = helper (acc ++ [y]) (x : xs) ys
