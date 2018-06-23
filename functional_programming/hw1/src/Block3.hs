module Block3 where

import           Data.Char
import           Data.List
import           Data.Semigroup

-- Task 1 --
data DayOfWeek
  = Monday
  | Tuesday
  | Wednesday
  | Thursday
  | Friday
  | Saturday
  | Sunday
  deriving (Show, Eq)

nextDay :: DayOfWeek -> DayOfWeek
nextDay Monday    = Tuesday
nextDay Tuesday   = Wednesday
nextDay Wednesday = Thursday
nextDay Thursday  = Friday
nextDay Friday    = Saturday
nextDay Saturday  = Sunday
nextDay Sunday    = Monday

afterDays :: DayOfWeek -> Int -> DayOfWeek
afterDays day 0 = day
afterDays day n = afterDays (nextDay day) (n - 1)

isWeekend :: DayOfWeek -> Bool
isWeekend day
  | day == Saturday || day == Sunday = True
  | otherwise = False

daysToParty :: DayOfWeek -> Int
daysToParty = helper 0
  where
    helper acc day
      | day == Friday = acc
      | otherwise = helper (acc + 1) (nextDay day)

-- Task 2 --
data Knight = Knight
  { name     :: String
  , strength :: Int
  , health   :: Int
  } deriving (Show)

data Monster = Monster
  { name     :: String
  , strength :: Int
  , health   :: Int
  } deriving (Show)

fight :: Knight -> Monster -> String
fight knight monster = getFightInfo knight monster (getHitsToWin knight monster)
  where
    getFightInfo :: Knight -> Monster -> (Int, Int) -> String
    getFightInfo knight monster (knightHits, monsterHits)
      | knightHits <= monsterHits =
        printResult ((name :: Knight -> String) knight) ((name :: Monster -> String) monster) knightHits
      | otherwise = printResult ((name :: Monster -> String) monster) ((name :: Knight -> String) knight) monsterHits
    getHitsToWin :: Knight -> Monster -> (Int, Int)
    getHitsToWin knight monster =
      let knightStrength = (strength :: Knight -> Int) knight
          knightHealth = (health :: Knight -> Int) knight
          monsterStrength = (strength :: Monster -> Int) monster
          monsterHealth = (health :: Monster -> Int) monster
          knightHits = (+ 1) $ div (monsterHealth - 1) knightStrength
          monsterHits = (+ 1) $ div (knightHealth - 1) monsterStrength
      in (knightHits, monsterHits)
    printResult :: String -> String -> Int -> String
    printResult name1 name2 rounds = name1 ++ " killed " ++ name2 ++ " in " ++ show rounds ++ " rounds!"

-- Task 3 --
data Vector a
  = Vector2D a
             a
  | Vector3D a
             a
             a
  deriving (Show, Eq)

-- Helper functions --
packVector :: Vector a -> [a]
packVector (Vector2D x y)   = [x, y]
packVector (Vector3D x y z) = [x, y, z]

vectorTo3D :: Num a => Vector a -> Vector a
vectorTo3D (Vector2D x y) = Vector3D x y 0
vectorTo3D v              = v

negateVector :: Num a => Vector a -> Vector a
negateVector (Vector2D x1 y1)    = Vector2D (-x1) (-y1)
negateVector (Vector3D x1 y1 z1) = Vector3D (-x1) (-y1) (-z1)

-- Task functions --
vectorLen :: Vector Double -> Double
vectorLen = sqrt . sum . map (^ 2) . packVector

vectorSum :: Num a => Vector a -> Vector a -> Vector a
vectorSum (Vector2D x1 y1) (Vector2D x2 y2) = Vector2D (x1 + x2) (y1 + y2)
vectorSum v1 v2 = sum3D (vectorTo3D v1) (vectorTo3D v2)
  where
    sum3D :: Num a => Vector a -> Vector a -> Vector a
    sum3D (Vector3D x1 y1 z1) (Vector3D x2 y2 z2) = Vector3D (x1 + x2) (y1 + y2) (z1 + z2)
    sum3D v1 v2 = error "Only 3D Vectors for helper function!"

scalarProduct :: Num a => Vector a -> Vector a -> a
scalarProduct (Vector2D x1 y1) (Vector2D x2 y2) = (x1 * x2) + (y1 * y2)
scalarProduct v1 v2 = scalar3D (vectorTo3D v1) (vectorTo3D v2)
  where
    scalar3D :: Num a => Vector a -> Vector a -> a
    scalar3D (Vector3D x1 y1 z1) (Vector3D x2 y2 z2) = (x1 * x2) + (y1 * y2) + (z1 * z2)
    scalar3D v1 v2 = error "Only 3D Vectors for helper function!"

vectorDistance :: Vector Double -> Vector Double -> Double
vectorDistance v1 v2 = vectorLen $ vectorSum v1 (negateVector v2)

vectorProduct :: Num a => Vector a -> Vector a -> Vector a
vectorProduct v1 v2 = vectorProduct3D (vectorTo3D v1) (vectorTo3D v2)
  where
    vectorProduct3D :: Num a => Vector a -> Vector a -> Vector a
    vectorProduct3D (Vector3D x1 y1 z1) (Vector3D x2 y2 z2) =
      Vector3D (y1 * z2 - z1 * y2) (z1 * x2 - x1 * z2) (x1 * y2 - y1 * x2)
    vectorProduct3D v1 v2 = error "Only 3D Vectors for helper function!"

-- Task 4 --
data Nat
  = Z
  | S Nat

instance Eq Nat where
  (==) Z Z         = True
  (==) x Z         = False
  (==) Z y         = False
  (==) (S x) (S y) = x == y

instance Ord Nat where
  (<=) Z Z         = True
  (<=) x Z         = False
  (<=) Z x         = True
  (<=) (S x) (S y) = x <= y

instance Num Nat where
  abs x = x
  signum Z = 0
  signum x = 1
  fromInteger 0 = Z
  fromInteger x = S $ fromInteger (x - 1)
  (+) x Z     = x
  (+) x (S y) = (+) (S x) y
  (-) x Z         = x
  (-) Z x         = error "There are no negative natural numbers!"
  (-) (S x) (S y) = (-) x y
  (*) = multNatHelper Z
    where
      multNatHelper :: Nat -> Nat -> Nat -> Nat
      multNatHelper acc x Z     = acc
      multNatHelper acc x (S y) = multNatHelper ((+) acc x) x y

natToInteger :: Nat -> Int
natToInteger Z = 0
natToInteger x = helper 0 x
  where
    helper :: Int -> Nat -> Int
    helper acc Z     = acc
    helper acc (S y) = helper (acc + 1) y

instance Show Nat where
  show x = show $ natToInteger x

-- Task 4 --
data Tree a
  = Leaf
  | Node a
         (Tree a)
         (Tree a)
  deriving (Eq, Show)

treeIsEmpty :: Tree a -> Bool
treeIsEmpty Leaf = True
treeIsEmpty _    = False

treeSize :: Tree a -> Int
treeSize Leaf           = 0
treeSize (Node x t1 t2) = 1 + treeSize t1 + treeSize t2

findElement :: Ord a => Tree a -> a -> Bool
findElement Leaf _ = False
findElement (Node rootValue t1 t2) toFind
  | toFind == rootValue = True
  | toFind <= rootValue = findElement t1 toFind
  | otherwise = findElement t2 toFind

insertElement :: Ord a => Tree a -> a -> Tree a
insertElement Leaf toInsert = Node toInsert Leaf Leaf
insertElement t@(Node rootValue t1 t2) toInsert
  | toInsert == rootValue = t
  | toInsert <= rootValue = Node rootValue (insertElement t1 toInsert) t2
  | otherwise = Node rootValue t1 (insertElement t2 toInsert)

fromList :: Ord a => [a] -> Tree a
fromList = foldl insertElement Leaf
