import boolean

def main():
    algebra = boolean.BooleanAlgebra()
    expr = algebra.parse(u'(oranges | banana) & apples')
    x = 0

if __name__ == '__main__':
    main()