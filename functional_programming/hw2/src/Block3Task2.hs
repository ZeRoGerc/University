module Block3Task2
  ( Identity(Identity, runIdentity)
  , Either(Left, Right)
  , Tree(Leaf, Node)
  , Const(Const, getConst)
  ) where

import           Prelude (Applicative, Eq, Foldable, Functor, Monoid, Ord, Show,
                          Traversable, fmap, foldMap, foldr, mapM, mappend,
                          mempty, pure, sequence, sequenceA, traverse,
                          undefined, (<$>), (<*>), (>>=))

newtype Identity a = Identity
  { runIdentity :: a
  } deriving (Show, Eq)

data Either a b
  = Left a
  | Right b

data Tree a
  = Leaf
  | Node a
         (Tree a)
         (Tree a)
  deriving (Eq, Show)

newtype Const a b = Const
  { getConst :: a
  }

data Pair a b =
  Pair a
       b

instance Monoid a => Monoid (Const a b) where
  mempty = Const mempty
  c1 `mappend` c2 = Const (getConst c1 `mappend` getConst c2)

--------------------------------
--------------------------------
----------- Functors -----------
--fmap        :: (a -> b) -> f a -> f b
instance Functor Identity where
  fmap f (Identity x) = Identity (f x)

instance Functor (Either a) where
  fmap _ (Left x)  = Left x
  fmap f (Right y) = Right (f y)

instance Functor Tree where
  fmap f Leaf             = Leaf
  fmap f (Node value l r) = Node (f value) (fmap f l) (fmap f r)

instance Functor (Const m) where
  fmap _ (Const v) = Const v

instance Functor (Pair a) where
  fmap f (Pair x y) = Pair x (f y)

--         Pair:
--1.       fmap id         ≡ id
--         fmap id (Pair x y)
--[fmap]   Pair x (id y)
--[id]     Pair x y
--
--         Const:
--1.       fmap id           ≡ id
--         fmap id (Const x) ≡ id (Const x)
--[fmap]   (Const x) = id (Const x)
--[id]     (Const x) = (Const x)
--
--         Tree:
--1.       fmap id           ≡ id
--         fmap id Leaf         = id Leaf
--         Leaf                 = Leaf
--
--         fmap id (Node x l r) = id (Node x l r)
--[fmap]   Node (id x) (fmap f l) (fmap f r)
--[id]     Node x (fmap f l) (fmap f r)
--
--
--         Pair
--2.       fmap f . fmap g    ≡    fmap (f . g)
--         Left:
--[def]    fmap f (fmap g (Pair x y))
--[fmap]   fmap f (Pair x (g y))
--[fmap]   Pair x (f (g y))
--[.]      Pair x (f . g y)
--------------------------------
--------------------------------
--------------------------------
--------- Applicative ----------
--    pure :: a -> f a
--    (<*>) :: f (a -> b) -> f a -> f b
instance Applicative Identity where
  pure = Identity
  (Identity f) <*> (Identity x) = pure (f x)

instance Applicative (Either a) where
  pure = Right
  Left f <*> _ = Left f
  Right f <*> r = fmap f r

instance Applicative Tree where
  pure x = Node x (pure x) (pure x)
  Leaf <*> _ = Leaf
  _ <*> Leaf = Leaf
  (Node f fl fr) <*> (Node x l r) = Node (f x) (fl <*> l) (fr <*> r)

instance Monoid m => Applicative (Const m) where
  pure _ = Const mempty
  Const f <*> Const v = Const (f `mappend` v)

instance Monoid m => Applicative (Pair m) where
  pure = Pair mempty
  (Pair u f) <*> (Pair v x) = Pair (u `mappend` v) (f x)

--        Identity
--1.      (pure id) <*> v = v
--        Let's rewrite:
--        pure id <*> (Identity v) = (Identity v)
--        Left:
--[pure]  Identity id <*> (Identity v)
--[<*>]   Identity (id v)
--[id]    Identity v
--
--        Identity
--2.      pure (.) <*> u <*> v <*> w = u <*> (v <*> w)
--        Let's rewrite
--        pure (.) <*> (Identity u) <*> (Identity v) <*> (Identity w) = (Identity u) <*> ((Identity v) <*> (Identity w))
--        Left:
--[pure]  Identity (.) <*> (Identity v) <*> (Identity w)
--[<*>]   Identity (u .) <*> (Identity v) <*> (Identity w)
--[<*>]   Identity (u . v) <*> (Identity w)
--        Right:
--        (Identity u) <*> ((Identity v) <*> (Identity w))
--[<*>]   Identity (u . v) <*> (Identity w)
--
--        Identity
--3.      pure f <*> pure x = pure (f x)
--        Left:
--[pure]  Identity f <*> Identity x
--[<*>]   Identity (f x)
--        Right:
--[pure]  Identity (f x)
--
--        Identity
--4.      u <*> pure y = pure ($ y) <*> u
--        Let's rewrite:
--        Identity u <*> pure y = pure ($ y) <*> (Identity u)
--        Left:
--[pure]  Identity u <*> Identity y
--[<*>]   Identity (u y)
--[$]     Identity (u $ y)
--        Right:
--[pure]  Identity ($ y) <*> (Identity u)
--[<*>]   Identity (u $ y)
--------------------------------
--------------------------------
--------------------------------
----------- Foldable -----------
--fold :: Monoid m => t m -> m
--foldMap :: Monoid m => (a -> m) -> t a -> m
instance Foldable Identity where
  foldMap f (Identity x) = f x

instance Foldable (Either a)
  --fold (Left a) = mempty
  --fold (Right b) = b
                       where
  foldMap f (Left a)  = mempty
  foldMap f (Right b) = f b

instance Foldable Tree where
  foldMap f Leaf = mempty
  foldMap f (Node value l r) = (foldMap f l `mappend` f value) `mappend` foldMap f r

instance Foldable (Const m) where
  foldMap _ _ = mempty

instance Foldable (Pair a) where
  foldMap f (Pair x t) = f t

--     Either
--1. fold  ≡  foldMap id
--   fold (Left a)   = foldMap id (Left a)
--   mempty          = mempty
--
--   fold (Right b)  = foldMap id (Right b)
--   b               = id b
--   b               = b
--
--             Identity
--2.           foldMap f ≡ fold . fmap f
--             Left:
--             foldMap f (Identity x)
--[foldMap]    f x
--             Right:
--             fold . fmap f
--[fold]       foldMap id . fmap f (Identity x)
--[.]          foldMap id (fmap f (Identity x))
--[fmap]       foldMap id (Identity (f x))
--[foldMap]    id (f x)
--[id]         f x
-----------------------------------------
-----------------------------------------
-----------------------------------------
------------- Traversable ---------------
-- Functor
-- fmap :: (a -> b) -> f a -> f b
--
--Applicative
--    pure :: a -> f a
--    (<*>) :: f (a -> b) -> f a -> f b
--
--traverse :: Applicative f => (a -> f b) -> t a -> f (t b)
--sequenceA :: Applicative f => t (f a) -> f (t a)
instance Traversable Identity where
  traverse f (Identity x) = fmap Identity (f x)

instance Traversable (Either a) where
  traverse f (Left x)  = pure (Left x)
  traverse f (Right y) = fmap Right (f y)

instance Traversable Tree where
  traverse f Leaf         = pure Leaf
  traverse f (Node x l r) = fmap Node (f x) <*> traverse f l <*> traverse f r

instance Traversable (Const m) where
  traverse _ (Const m) = pure (Const m)

instance Traversable (Pair a) where
  traverse f (Pair x y) = fmap (Pair x) (f y)
-- fmap f (Identity x) = Identity (f x)
--1.       fmap id         ≡ id
--2.       fmap f . fmap g    ≡    fmap (f . g)
--
--  pure = Identity
--  (Identity f) <*> (Identity x) = pure (f x)
--1.      (pure id) <*> v = v
--2.      pure (.) <*> u <*> v <*> w = u <*> (v <*> w)
--3.      pure f <*> pure x = pure (f x)
--4.      u <*> pure y = pure ($ y) <*> u
--  newtype Id a = Id a
--  newtype Compose f g a = Compose (f (g a))
--
--                 Pair
--1.               t . traverse f = traverse (t . f)
--                 Left:
--                 t (traverse f (Const x))
--[traverse]       t (pure (Const x))
--[Applicative]    pure (Const x)
--
--                 Right:
--                 traverse (t . f) (Const x)
--[traverse]       pure (Const x)
--
--                 Pair
--2.               traverse Id = Id
--                 Left:
--                 traverse  Id (Pair x y)
--[traverse]       fmap (Pair x) (Id y)
--[fmap]           Id (Pair x y)
--
--
--                 Const
--                 traverse (Compose . fmap g . f) = Compose . fmap (traverse g) . traverse f
--
--                 Left:
--                 traverse (Compose . fmap g . f) (Const x)
--[traverse]       pure (Const x)
--[GHC]            Compose $ pure (pure (Const x))   // pure x = Compose (pure (pure x))
--
--                  Right:
--                  Compose ( fmap (traverse g) ( traverse f (Const x) ) )
--[traverse]        Compose ( fmap (traverse g) (pure (Const x)))
--[pure]            Compose ( fmap (traverse g) (Const mempty))
--[fmap]            Compose ( Const mempty )
--[Const mempty]    Compose $ pure (pure (Const x))
