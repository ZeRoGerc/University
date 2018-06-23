module LensTask1 where

import Data.Functor.Const (Const(..), getConst)
import Data.Functor.Identity (Identity(..), runIdentity)

type Lens s t a b
   = forall f. Functor f =>
                 (a -> f b) -> s -> f t

type Lens' s a = Lens s s a a

set :: Lens' s a -> a -> s -> s -- set    value (setter)
set l d = runIdentity . l (Identity . const d)

view :: Lens' s a -> s -> a -- lookup value (getter)
view l = getConst . l Const

over :: Lens' s a -> (a -> a) -> s -> s -- change value (modifier)
over l f = runIdentity . l (Identity . f)

_1 :: Lens (a, x) (b, x) a b
_1 vMap p1 =
  let b = vMap (fst p1)
  in fmap (\b -> (b, snd p1)) b

_2 :: Lens (x, a) (x, b) a b
_2 vMap p1 =
  let b = vMap (snd p1)
  in fmap (\b -> (fst p1, b)) b
