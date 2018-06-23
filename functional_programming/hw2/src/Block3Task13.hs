module Block3Task13 where

import           Block3Task1Shared (Monad (return, (>>=)),
                                    MonadFish (returnFish, (>=>)))
import           Prelude           hiding (Monad, return, (>>=))

-- Task 1.3 --
instance MonadFish m => Monad m where
  return = returnFish
  m >>= f = (id >=> f) m
--
--                 m >>= return
--[return]         m >>= returnFish
--[>>=]            (id >=> returnFish) m
--[MonadFish1]     id m
--[id]             m
