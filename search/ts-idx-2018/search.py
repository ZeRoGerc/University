import sys

import boolean
import time
import pickle

from varbyte import VarbyteEncoder
from simple9 import Simple9Encoder
from queryids import IdMerger


def get_doc_ids(encoder, tree):
    if isinstance(tree, boolean.Symbol):
        word = tree.obj.strip().lower().encode('utf-8')
        return IdMerger(encoder.get_doc_ids(word))
    elif isinstance(tree, boolean.NOT):
        ids = get_doc_ids(encoder, tree.args[0])
        return ids.apply_not()
    elif isinstance(tree, boolean.AND):
        left_ids = get_doc_ids(encoder, tree.args[0])
        right_ids = get_doc_ids(encoder, tree.args[1])
        return left_ids.apply_and(right_ids)
    elif isinstance(tree, boolean.OR):
        left_ids = get_doc_ids(encoder, tree.args[0])
        right_ids = get_doc_ids(encoder, tree.args[1])
        return left_ids.apply_or(right_ids)
    else:
        raise Exception(u"Unexpected operator!")


def main():
    # ct = time.clock()

    state = pickle.load(open("index.txt", "rb"))
    if state['encoding'] == 'varbyte':
        encoder = VarbyteEncoder()
    elif state['encoding'] == 'simple9':
        encoder = Simple9Encoder()
    else:
        raise Exception("Unsupported encoding!")

    encoder.set_state(state)
    # print("Time for loading: {}".format(1000 * (time.clock() - ct)))

    IdMerger.MAX_ID = len(encoder._urls)
    algebra = boolean.BooleanAlgebra()
    for query in sys.stdin:
        tree_query = algebra.parse(query.decode('utf-8'))

        result = get_doc_ids(encoder, tree_query)

        sys.stdout.write(query)
        sys.stdout.write(str(len(result.ids)))
        sys.stdout.write('\n')
        for url in encoder.get_urls(sorted(result.ids)):
            sys.stdout.write(url)
            sys.stdout.write('\n')


if __name__ == '__main__':
    main()
