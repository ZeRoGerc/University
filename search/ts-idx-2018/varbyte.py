import pickle
from struct import pack, unpack


class VarbyteEncoder:
    def __init__(self):
        self._encoding = 'varbyte'
        self._index = {}
        self._urls = []

        self._state = {
            'encoding': self._encoding,
            'index': self._index,
            'urls': self._urls
        }

    def set_state(self, state):
        self._encoding = state['encoding']
        self._index = state['index']
        self._urls = state['urls']
        self._state = state

    def add_document(self, url, words):
        self._urls.append(url)
        doc_id = VarbyteEncoder.encode(len(self._urls) - 1)
        for w in words:
            if w not in self._index:
                self._index[w] = doc_id
            else:
                self._index[w] += doc_id

    def get_doc_ids(self, word):
        if word not in self._index.keys():
            return []

        return VarbyteEncoder.decode(self._index[word])

    def get_urls(self, doc_ids):
        return [self._urls[doc_id] for doc_id in doc_ids]

    def write_to_file(self, path):
        pickle.dump(self._state, open(path, "wb"))

    @staticmethod
    def encode(doc_id):
        b = []
        while True:
            b.append(doc_id % 128)
            doc_id /= 128
            if doc_id == 0:
                break
        b[-1] += 128

        return pack('%dB' % len(b), *b)

    @staticmethod
    def decode(bs):
        bytestream = unpack('%dB' % len(bs), bs)
        doc_ids = []
        current = 0
        multiplier = 1
        for b in bytestream:
            if b >= 128:
                current = current + multiplier * (b - 128)
                doc_ids.append(current)
                current = 0
                multiplier = 1
            else:
                current = current + multiplier * b
                multiplier *= 128

        return doc_ids


if __name__ == '__main__':
    p = "t_index.txt"

    e = VarbyteEncoder()
    e.add_document("url", ["text"])
    e.add_document("url2", ["text"])
    VarbyteEncoder.write_to_file(e, p)

    e = VarbyteEncoder.from_file(p)
    # should be ['url']
    print e.get_doc_ids("text")
