module LensTask3
  ( cd
  , ls
  , file
  ) where

import           Control.Lens (Lens' (..), Traversal' (..), filtered, traversed,
                               (^.))
import           LensTask2    (FS (..), contents, name, _Dir, _File)

cd :: FilePath -> Traversal' FS FS
cd path = _Dir . ifExists path . _Dir

ls :: Traversal' FS FilePath
ls = _Dir . contents . traversed . name

file :: FilePath -> Traversal' FS FilePath
file path = _Dir . ifExists path . name

ifExists :: FilePath -> Traversal' FS FS
ifExists path = _Dir . contents . traversed . filtered (\cont -> cont ^. name == path)
