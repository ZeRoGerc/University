module Main where

import Executor(execFromFile, fibFile)

main :: IO ()
main = execFromFile fibFile