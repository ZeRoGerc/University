import time
import sys

from docreader import DocumentStreamReader
from doc2words import extract_words
from varbyte import VarbyteEncoder
from simple9 import Simple9Encoder


def main(encoding, paths):
    reader = DocumentStreamReader(paths)

    if encoding == 'varbyte':
        encoder = VarbyteEncoder()
    elif encoding == 'simple9':
        encoder = Simple9Encoder()
    else:
        raise Exception("Unsupported encoding!")

    ct = time.clock()
    for doc in reader:
        url = doc.url
        words = set([w.encode('utf-8') for w in extract_words(doc.text)])
        encoder.add_document(url, words)

    encoder.write_to_file("index.txt")
    print "Time for index creation: {}".format(1000 * (time.clock() - ct))


if __name__ == '__main__':
    arg_encoding = sys.argv[1]
    arg_paths = sys.argv[2:]
    main(arg_encoding, arg_paths)
