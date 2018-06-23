module Block3Task12 where

import           Block3Task1Shared (Monad (return, (>>=)),
                                    MonadJoin (join, returnJoin))
import           Prelude           hiding (Monad, return, (>>=))

-- Task 1.2 --
instance Monad m => MonadJoin m where
  returnJoin = return
  join mma = mma >>= id
--
--                  join . returnJoin
--[returnJoin]      join . return
--[join]            bind id . return
--[Monad2]          id
