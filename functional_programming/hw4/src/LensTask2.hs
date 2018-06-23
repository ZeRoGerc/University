module LensTask2
  ( FS(..)
  , name
  , contents
  , FSException
  , scanDirectory
  , _Dir
  , _File
  , subtrees
  , maybeDir
  , fileName
  , changeToRoot
  , addSuffix
  , maybeFirstSubDir
  , listFiles
  ) where

import Control.Exception (Exception, throwIO)
import Control.Lens
       (Traversal'(..), (%~), (&), (.~), (^..), (^?), _Just, _Show,
        makeLenses, makePrisms, over, set, traversed, view)
import Data.Maybe (fromMaybe)
import System.Directory
       (doesDirectoryExist, doesFileExist, doesPathExist, listDirectory)
import System.FilePath (splitPath)

data FS
  = Dir { _name :: FilePath -- название папки, не полный путь
        , _contents :: [FS] }
  | File { _name :: FilePath -- название файла, не полный путь
          }
  deriving (Show, Eq)

data FSException =
  PathDoesNotExists
  deriving (Show)

instance Exception FSException

scanDirectory :: FilePath -> IO FS
scanDirectory path = do
  p <- doesPathExist path
  if not p
    then throwIO PathDoesNotExists
    else do
      d <- doesDirectoryExist path
      if d
        then do
          files <- listDirectory path
          fss <- mapM (\f -> scanDirectory (path ++ "/" ++ f)) files
          return $ Dir (last . splitPath $ path) fss
        else return $ File (last . splitPath $ path)

makeLenses ''FS

_Dir :: Traversal' FS FS
_Dir f =
  \case
    obj@(Dir files p) -> f obj
    other -> pure other

_File :: Traversal' FS FS
_File f =
  \case
    obj@(File p) -> f obj
    other -> pure other

testDir =
  Dir "root" [File "rootFile.fs", File "rootFile2.fs", Dir "dir1" [Dir "dirinner" [], File "inner.tt"], Dir "dir2" []]

testFile = File "file"

-- Practice --
-- 1
subtrees :: FS -> [FS]
subtrees directory = directory ^.. _Dir . contents . traversed . _Dir

-- 2
maybeDir :: FS -> Maybe FilePath
maybeDir directory = directory ^? _Dir . name

-- 3
fileName :: FS -> FilePath
fileName file = fromMaybe "" (file ^? _File . name)

-- 4
changeToRoot :: FS -> FS
changeToRoot directory = directory & _Dir . name .~ "/"

-- 5
addSuffix :: FS -> String -> FS
addSuffix directory suffix = directory & _Dir . name %~ (++ suffix)

-- 6
maybeFirstSubDir :: FS -> Maybe FilePath
maybeFirstSubDir directory = directory ^? _Dir . contents . traversed . _Dir . name

-- 7
listFiles :: FS -> [FilePath]
listFiles directory = directory ^.. _Dir . contents . traversed . _File . name