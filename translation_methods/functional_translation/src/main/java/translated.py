def returnSmth(x1, x2):
    if x2 == True:
        return -x1 + 3
    if x2 == False:
        return -x1

def isMinusOne(x1):
    if x1 == -1:
        return True
    return False

def isMinusTen(x1):
    if x1 == -10:
        return True
    return False

def doubleMe(x1):
    return x1 + x1

def doubleUs(x1, x2):
    return doubleMe(x1) + doubleMe(x2)

def first(x1, x2):
    return x1

def second(x1, x2):
    return x2

def fact(x1):
    if x1 == 0:
        return 1
    return x1 * fact(x1, -1)

def findRecursively(x1, x2):
    if x2 == 1:
        return False
    return (x1 % x2 == 0) or findRecursively(x1, x2, -1)

def isPrime(x1):
    return not (findRecursively(x1, x1, -1))

def doubleSmallNumber(x1):
    if (x1 > 100):
        return x1
    else:
        return x1 * 2

def factHelper(x1, x2):
    if x2 == True:
        return x1
    if x2 == False:
        if (x1 == 0):
            return 1
        else:
            if (x1 > 2):
                return x1 * factHelper(x1, -1, False)
            else:
                return factHelper(x1, True)

def hardFact(x1):
    return factHelper(x1, False)

def findRecIf(x1, x2):
    if (x2 <= 1):
        return False
    else:
        return (x1 % x2 == 0) or findRecIf(x1, x2, -1)

def ifPrime(x1):
    return not (findRecIf(x1, x1, -1))

def fibHelper(x1, x2, x3):
    if x3 == 0:
        return x2
    return fibHelper(x2, x1 + x2, x3, -1)

def fib(x1):
    if x1 == 0:
        return 0
    return fibHelper(0, 1, x1, -1)

def compPoints(x1, x2, x3, x4):
    if (x1 != x3):
        if (x1 < x3):
            return -1
        else:
            return 1
    else:
        if (x2 != x4):
            if (x2 < x4):
                return -1
            else:
                return 1
        else:
            return 0

def add(x1, x2):
    return x1 + x2

def diff(x1, x2):
    return x1 - x2

def transform(x1, x2, x3):
    if (not (x3)):
        return diff(x1, x2)
    else:
        return add(x1, x2)

