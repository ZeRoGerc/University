module BenchmarksAlgorithms
  ( bruteNub
  , bruteNubFold
  , nubTreeSet
  , nubHashSet
  , nubSorted
  ) where

import           Control.Monad (when)
import           Data.Hashable (Hashable (hashWithSalt))
import qualified Data.HashSet  as HS (HashSet (..), empty, insert, member)
import qualified Data.List     as L (elem, head, length, nub, sort)
import           Data.Set      (Set (..), empty, insert, member)

bruteNub :: (Eq a) => [a] -> [a]
bruteNub lst = bruteNubAcc lst []
  where
    bruteNubAcc :: (Eq a) => [a] -> [a] -> [a]
    bruteNubAcc [] acc = acc
    bruteNubAcc (x:xs) acc
      | x `elem` acc = bruteNubAcc xs acc
      | otherwise = bruteNubAcc xs (x : acc)

bruteNubFold :: (Eq a) => [a] -> [a]
bruteNubFold = reverse . foldr addIfNotElem []
  where
    addIfNotElem :: (Eq a) => a -> [a] -> [a]
    addIfNotElem x acc =
      if x `elem` acc
        then acc
        else x : acc

nubTreeSet :: (Ord a) => [a] -> [a]
nubTreeSet lst = nubWithSet lst [] empty
  where
    nubWithSet :: (Ord a) => [a] -> [a] -> Set a -> [a]
    nubWithSet [] acc s = reverse acc
    nubWithSet (x:xs) acc s =
      if member x s
        then nubWithSet xs acc s
        else nubWithSet xs (x : acc) (insert x s)

nubHashSet :: (Eq a, Hashable a) => [a] -> [a]
nubHashSet lst = nubWithSet lst [] HS.empty
  where
    nubWithSet :: (Eq a, Hashable a) => [a] -> [a] -> HS.HashSet a -> [a]
    nubWithSet [] acc s = reverse acc
    nubWithSet (x:xs) acc s =
      if HS.member x s
        then nubWithSet xs acc s
        else nubWithSet xs (x : acc) (HS.insert x s)

nubSorted :: (Ord a) => [a] -> [a]
nubSorted lst = nubSortedHelper (L.sort lst) []
  where
    nubSortedHelper :: (Ord a) => [a] -> [a] -> [a]
    nubSortedHelper [] acc = acc
    nubSortedHelper (x:xs) acc =
      if null acc || (L.head acc /= x)
        then nubSortedHelper xs (x : acc)
        else nubSortedHelper xs acc
