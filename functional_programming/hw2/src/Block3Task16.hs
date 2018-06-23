module Block3Task16 where

import           Block3Task1Shared (Functor (fmap),
                                    MonadFish (returnFish, (>=>)),
                                    MonadJoin (join, returnJoin))
import           Prelude           hiding (Functor, fmap)

-- Task 1.6 --
instance (Functor m, MonadJoin m) => MonadFish m where
  returnFish = returnJoin
  (>=>) f1 f2 = join . fmap f2 . f1
--
--                 f >=> returnFish
--[>=>]            join . fmap returnFish . f
--[returnJFish]    join . fmap returnJoin . f
--[MonadJoin2]     id . f
--[id]             f
