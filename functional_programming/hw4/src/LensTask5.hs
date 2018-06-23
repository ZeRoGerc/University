module LensTask5
  ( walker
  ) where

import Control.Lens
       (Lens(..), Lens'(..), LensLike'(..), Traversal'(..), (%=), (^.),
        (^..), (^?), _3, traversed, use, uses, view, views)
import Control.Monad ((>>), void)
import Control.Monad.Reader (ReaderT(..), runReaderT)
import Control.Monad.State
       (StateT(..), get, gets, liftIO, runStateT)
import Data.List (intercalate)
import Data.Maybe (isJust)

import LensTask2
       (FS(..), _Dir, _File, contents, fileName, name, scanDirectory)
import LensTask3 (cd, file, ls)

type DirCount = Int

type FileCount = Int

type Walker = StateT [(FileCount, DirCount, FilePath)] (ReaderT FS IO)

-- Walker run function --
walker :: FilePath -> IO ()
walker root = do
  fsStart <- scanDirectory root
  void $ runReaderT (runStateT walkerInner [getInitialState root fsStart]) fsStart

-- cd in directory dependent on state --
getFSW ::
     forall f. Applicative f
  => Walker (LensLike' f FS FS)
getFSW = do
  state <- get
  let k = map (^. _3) state
  pure $ foldr ((.) . cd) id $ (tail . reverse) k

-- print current counters in console --
printCurrentState :: Walker ()
printCurrentState = do
  (i, j, _) <- uses id head
  p <- uses id $ map (view _3)
  rootPath <- view name
  liftIO $ putStrLn $ "You're in " ++ intercalate "/" (reverse p)
  liftIO $ putStrLn $ "Files from root \"" ++ rootPath ++ "/\": " ++ show i
  liftIO $ putStrLn $ "Directories from \"" ++ rootPath ++ "/\": " ++ show j

-- Main function --
walkerInner :: Walker ()
walkerInner = do
  root <- view id
  printCurrentState
  cmd <- liftIO getLine
  case words cmd of
    ["cd", subdir] -> do
      f <- getFSW
      if isJust $ root ^? f . cd subdir
        then do
          f' <- getFSW
          let files', dirs' :: Int
              files' = length $ root ^.. f' . cd subdir . contents . traversed . _File
              dirs' = length $ root ^.. f' . cd subdir . contents . traversed . _Dir
          id %= \case
            [] -> [(files', dirs', subdir)]
            xs@((f, d, _):_) -> (files' + f, dirs' + d, subdir) : xs
        else liftIO $ print $ "Can't find directory " ++ subdir
    ["up"] -> id %= drop 1
  walkerInner

getInitialState :: String -> FS -> (FileCount, DirCount, String)
getInitialState rootPath fs =
  let files = length $ fs ^.. contents . traversed . _File
      dirs = length $ fs ^.. contents . traversed . _Dir
  in (files, dirs, rootPath)