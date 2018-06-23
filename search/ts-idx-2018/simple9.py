import pickle
from struct import pack, unpack

PAYLOADS = [(28, 1), (14, 2), (9, 3), (7, 4), (5, 5), (4, 7), (3, 9), (2, 14), (1, 28)]


def number_of_bytes(doc_id):
    for _, count in PAYLOADS:
        if doc_id < 2 ** count:
            return count
    raise Exception("DOC_ID should have less than 28 bits!")


class Simple9Encoder:
    def __init__(self):
        self._encoding = 'simple9'
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
        doc_id = len(self._urls)
        for w in words:
            if w not in self._index:
                self._index[w] = []

            self._index[w].append(doc_id)

    def get_doc_ids(self, word):
        if word not in self._index.keys():
            return []
        return Simple9Encoder.decode(self._index[word])

    def get_urls(self, doc_ids):
        return [self._urls[doc_id - 1] for doc_id in doc_ids]

    def write_to_file(self, path):
        for w in self._index.keys():
            doc_ids = self._index[w]
            bytes = Simple9Encoder.encode_doc_ids(doc_ids)
            self._index[w] = bytes

        pickle.dump(self._state, open(path, "wb"))

    @staticmethod
    def encode_doc_ids(doc_ids):
        bytes = ""
        current = 0
        while current < len(doc_ids):
            code_id = Simple9Encoder.get_code(current, doc_ids)
            number_of_ids, id_len = PAYLOADS[code_id]
            bytes += Simple9Encoder.encode_head(code_id, doc_ids, current, current + number_of_ids)
            current += number_of_ids

        return bytes

    @staticmethod
    def get_code(current, doc_ids):
        code_id = 0
        max_len = 0
        i = 0
        while max_len < PAYLOADS[code_id][1]:
            max_len = max(max_len, number_of_bytes(doc_ids[current + i]))
            temp_id = code_id
            while PAYLOADS[temp_id][1] < max_len:
                temp_id += 1


    @staticmethod
    def encode_head(code_id, doc_ids, start, finish):
        number_of_ids, id_len = PAYLOADS[code_id]
        assert finish - start <= number_of_ids

        b = code_id << 28
        for i in range(finish - start):
            if start + i >= len(doc_ids):
                break
            b |= doc_ids[start + i] << (i * id_len)

        return pack('I', b)

    @staticmethod
    def decode(bs):
        int_stream = unpack('%dI' % (len(bs) / 4), bs)
        i = 0
        numbers = []
        for num in int_stream:
            code_id = num >> 28
            numbers += Simple9Encoder.decode_one_int(code_id, num)

        return numbers

    @staticmethod
    def decode_one_int(code_id, num):
        number_of_ids, id_len = PAYLOADS[code_id]
        result = []
        mask = (2 ** id_len) - 1
        for i in range(number_of_ids):
            current = (num & mask) >> (i * id_len)
            mask = mask << id_len
            if current != 0:
                result.append(current)

        return result


if __name__ == '__main__':
    # print all(elem < 10 for elem in [1, 2, 3, 11])
    p = "t_index.txt"

    e = Simple9Encoder()
    e.add_document("url1", ["text"])
    e.add_document("url2", ["text"])
    e.add_document("url3", ["text"])
    e.add_document("url4", ["text"])
    e.add_document("url5", ["text"])
    e.add_document("url6", ["text"])
    e.add_document("url8", ["text"])
    e.add_document("url9", ["text"])
    e.add_document("url10", ["text"])
    e.add_document("url11", ["text"])
    e.add_document("url12", ["text"])
    e.add_document("url13", ["text"])
    Simple9Encoder.write_to_file(e, p)


    state = pickle.load(open(p, "rb"))
    e = Simple9Encoder()
    e.set_state(state)
    # should be ['url']
    print e.get_urls(e.get_doc_ids("text"))
