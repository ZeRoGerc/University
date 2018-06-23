module LensTask4
  ( changeExt
  , changeExtInDir
  , getNamesRecursive
  , deleteIfEmpty
  ) where

import           Control.Lens    (Lens' (..), Traversal' (..), transform,
                                  traversed, (%~), (&), (.~), (^.), (^..), (^?))
import           Data.Maybe      (isNothing)
import           LensTask2       (FS (..), contents, name, _Dir, _File)
import           System.FilePath (splitExtension)

changeExt :: FilePath -> String -> FilePath
changeExt path ext = fst (splitExtension path) ++ ('.' : ext)

changeExtInDir :: FS -> String -> FS
changeExtInDir directory ext = directory & _Dir . contents . traversed . _File . name %~ (`changeExt` ext)

getNamesRecursive :: FS -> [FilePath]
getNamesRecursive directory@(Dir n c) = n : concatMap getNamesRecursive (directory ^.. _Dir . contents . traversed)
getNamesRecursive (File n) = [n]

deleteIfEmpty :: FS -> String -> FS
deleteIfEmpty dir subDirName =
  dir & _Dir . contents %~
  filter (\c -> not $ (c ^. name == subDirName) && isNothing (c ^? _Dir . contents . traversed))
