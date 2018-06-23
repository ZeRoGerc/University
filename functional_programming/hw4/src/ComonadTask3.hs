module ComonadTask3
  ( Tree(..)
  ) where

import           Control.Comonad

data Tree a =
  Node a
       [Tree a]

instance Functor Tree where
  fmap f (Node n subtrees) = Node (f n) (map (fmap f) subtrees)

instance Comonad Tree where
  extract (Node n subtrees) = n -- w a -> a
  duplicate obj@(Node n subtrees) = Node obj (map (\x -> Node x (map duplicate subtrees)) subtrees) -- w a -> w (w a)
  extend mp obj@(Node n subtrees) = fmap mp (Node obj (map (\x -> Node x (map duplicate subtrees)) subtrees)) -- (w a -> b) -> w a -> w b
