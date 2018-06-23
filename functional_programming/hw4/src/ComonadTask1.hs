module ComonadTask1
  ( Renew(..)
  ) where

import Control.Comonad (Comonad, duplicate, extend, extract)

data Renew s e a =
  Renew (e -> a)
        s

class Monoid e =>
      MonoidAction s e where
  act :: s -> e -> s

instance Functor (Renew s e) where
  fmap f (Renew fm c) = Renew (f . fm) c

instance MonoidAction s e => Comonad (Renew s e) where
  extract (Renew fm c) = fm mempty -- w a -> a
  duplicate (Renew fm c) = Renew (\e -> Renew (fm . mappend e) (act c e)) c -- w a -> w (w a)
  extend mp (Renew fm c) = Renew (\e -> mp $ Renew (fm . mappend e) (act c e)) c -- (w a -> b) -> w a -> w b