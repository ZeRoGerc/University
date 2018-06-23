module TemplateHaskellTextShow
  ( textShowInstance
  , TextShow(..)
  ) where

import qualified Data.Text as T
import Language.Haskell.TH
       (Con(..), Dec(..), Exp(..), Info(..), Name(..), Q(..), conT, listE,
        mkName, nameBase, reify, tupE, varE)

class TextShow a where
  textShow :: a -> T.Text

textShowInstance :: Name -> Q [Dec]
textShowInstance name = do
  TyConI (DataD _ _ _ _ [RecC _ fields] _) <- reify name
  let names = map (\(name', _, _) -> name') fields
  let showField :: Name -> Q Exp
      showField name' = [|\x -> T.pack (s ++ " = " ++ show ($(varE name') x))|]
        where
          s = nameBase name'
  let showFields :: Q Exp
      showFields = listE $ map showField names
  [d| instance TextShow $( conT name ) where
        textShow x = T.intercalate (T.pack ", ") (map ($ x) $showFields) |]
