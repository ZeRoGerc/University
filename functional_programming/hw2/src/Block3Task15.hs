module Block3Task15 where

import           Block3Task1Shared (Functor (fmap), Monad (return, (>>=)),
                                    MonadJoin (join, returnJoin))
import           Prelude           hiding (Functor, Monad, fmap, return, (>>=))

-- Task 1.5 --
instance (Functor m, MonadJoin m) => Monad m where
  return = returnJoin
  (>>=) m f = (join . fmap f) m
  -- bind f = (join . fmap f)
--
--                bind return
--[>>=]           (join . fmap return)
--[return]        (join . fmap returnJoin)
--[MonadJoin 2]   id
