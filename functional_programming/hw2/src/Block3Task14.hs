module Block3Task14 where

import           Block3Task1Shared (MonadFish (returnFish, (>=>)),
                                    MonadJoin (join, returnJoin))
import           Prelude           (id)

-- Task 1.4 --
instance MonadFish m => MonadJoin m where
  returnJoin = returnFish
  join = id >=> id
--
--                      join . returnJoin
--[returnJoin]          join . returnFish
--[join]                (id >=> id) . returnFish
--[Monad from Task1.3]  bind id . returnFish    // m >>= id -> bind id && m >>= id -> (id >=> id) m
--[returnFish]          bind id . return
--[Monad 2]             id
