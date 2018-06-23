module Block3Task11 where

import           Block3Task1Shared (Monad (return, (>>=)),
                                    MonadFish (returnFish, (>=>)))
import           Prelude           hiding (Monad, return, (>>=))

-- Task 1.1 --
instance Monad m => MonadFish m where
  returnFish = return
  f1 >=> f2 = \x -> f1 x >>= f2
--
--                     f >=> returnFish
--[returnFish]         f >=> return
--[>=>]                \x -> f x >>= return
--[Monad1]             \x -> f x
--[reduction]          f
