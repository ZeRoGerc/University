module Block3Task1Shared where

import           Prelude hiding (Functor, Monad, fmap, return, (>>=))

class Functor f where
  fmap :: (a -> b) -> f a -> f b
     -- LAWS
     --    1. fmap id         ≡ id
     --    2. fmap f . fmap g ≡ fmap (f . g)

class Monad m where
  return :: a -> m a
  (>>=) :: m a -> (a -> m b) -> m b
    -- LAWS
    --    1. m >>= return     ≡  m
    --    1. bind return      ≡  id
    --
    --    2. return a >>= f   ≡  f a
    --    2. bind f . return  ≡  f
    --
    --    3. (m >>= f) >>= g  ≡  m >>= (\x -> f x >>= g)
    --    3. bind g . bind f  ≡  bind (bind g . f)

class MonadFish m where
  returnFish :: a -> m a
  (>=>) :: (a -> m b) -> (b -> m c) -> (a -> m c)
     -- LAWS
     --    1. f >=> returnFish ≡ f
     --    2. returnFish >=> f ≡ f
     --    3. (f >=> g) >=> h  ≡ f >=> (g >=> h)

class MonadJoin m where
  returnJoin :: a -> m a
  join :: m (m a) -> m a
    -- LAWS
    --     1. join . returnJoin      ≡ id
    --     2. join . fmap returnJoin ≡ id
    --     3. join . fmap join       ≡ join . join
