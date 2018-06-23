module Block5 where

import           Block3
import           Block4
import           Data.Char
import           Data.Coerce
import           Data.Foldable
import           Data.List
import           Data.Semigroup

-- Task 1 --
maybeConcat :: [Maybe [a]] -> [a]
maybeConcat l = printResult (foldl mappend (Just []) l)
  where
    printResult :: Maybe [a] -> [a]
    printResult (Just l) = l
    printResult Nothing  = error "Result can be only Just!"

data NonEmpty a =
  a :| [a]
  deriving (Show, Eq)

-- Task 2 --
instance Semigroup (NonEmpty a) where
  (<>) (a :| as) (b :| bs) = a :| (as ++ (b : bs))

newtype Identity a = Identity
  { runIdentity :: a
  } deriving (Show, Eq)

instance Semigroup a => Semigroup (Identity a) where
  (<>) a b = Identity {runIdentity = runIdentity a <> runIdentity b}

instance Monoid a => Monoid (Identity a) where
  mempty = Identity {runIdentity = mempty}
  mappend x y = Identity {runIdentity = mappend (runIdentity x) (runIdentity y)}

-- Task 3 --
instance Ord a => Semigroup (Tree a) where
  (<>) t1 t2 = foldr (flip insertElement) t1 (toList t2)

instance Ord a => Monoid (Tree a) where
  mempty = Leaf
  mappend t1 t2 = foldr (flip insertElement) t1 (toList t2)
