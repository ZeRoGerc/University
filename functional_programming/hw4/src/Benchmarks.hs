module Benchmarks
  ( runBenchmark
  ) where

import Criterion.Main (bench, bgroup, defaultMain, nf)
import Data.List (nub)
import System.Random (randomRIO)

import BenchmarksAlgorithms
       (bruteNub, bruteNubFold, nubHashSet, nubSorted, nubTreeSet)

randomList :: Int -> Int -> IO [Int]
randomList 0 lim = return []
randomList n lim = do
  r <- randomRIO (1, lim)
  rs <- randomList (n - 1) lim
  return (r : rs)

runBenchmark :: IO ()
runBenchmark = do
  list1000 <- randomList 1000 100
  defaultMain $
    let testList = [1,2 .. 100] ++ [1,3 .. 100] ++ [1,5 .. 100]
    in [ bgroup
           "test300"
           [ bench "lib nub" $ nf (nub @Int) testList
           , bench "bruteNub" $ nf (bruteNub @Int) testList
           , bench "bruteNubFold" $ nf (bruteNubFold @Int) testList
           , bench "nubTreeSet" $ nf (nubTreeSet @Int) testList
           , bench "nubHashSet" $ nf (nubHashSet @Int) testList
           , bench "nubSorted" $ nf (nubSorted @Int) testList
           ]
       , bgroup
           "random1000"
           [ bench "lib nub" $ nf (nub @Int) list1000
           , bench "bruteNub" $ nf (bruteNub @Int) list1000
           , bench "bruteNubFold" $ nf (bruteNubFold @Int) list1000
           , bench "nubTreeSet" $ nf (nubTreeSet @Int) list1000
           , bench "nubHashSet" $ nf (nubHashSet @Int) list1000
           , bench "nubSorted" $ nf (nubSorted @Int) list1000
           ]
       ]