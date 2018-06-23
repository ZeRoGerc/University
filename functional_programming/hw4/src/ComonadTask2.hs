module ComonadTask2
  ( Project
  , buildProject
  , benchs
  , github
  , travis
  ) where

import           Control.Comonad.Traced (Traced (..), runTraced, traced)
import           Data.Text              (Text (..))

data ProjectSettings = ProjectSettings
  { settingsBenchs :: Bool
  , settingsGithub :: Bool
  , settingsTravis :: Bool
  }

-- Traced A is Comonad if A is Monoid --
instance Monoid ProjectSettings where
  mempty = ProjectSettings False False False
  mappend ps1 ps2 =
    ProjectSettings
      (settingsBenchs ps1 || settingsBenchs ps2)
      (settingsGithub ps1 || settingsGithub ps2)
      (settingsTravis ps1 || settingsTravis ps2)

data Project = Project
  { projectName :: Text
  , hasBenchs   :: Bool
  , hasGithub   :: Bool
  , hasTravis   :: Bool
  } deriving (Show)

type ProjectBuilder = Traced ProjectSettings Project

buildProject :: Text -> ProjectBuilder
buildProject projectName =
  traced $ \sett -> Project projectName (settingsBenchs sett) (settingsGithub sett) (settingsTravis sett)

benchs :: ProjectBuilder -> Project
benchs builder = runTraced builder $ ProjectSettings True False False

github :: ProjectBuilder -> Project
github builder = runTraced builder $ ProjectSettings False True False

travis :: ProjectBuilder -> Project
travis builder =
  let project = runTraced builder $ ProjectSettings False False True
  in project {hasTravis = hasTravis project && hasGithub project}
