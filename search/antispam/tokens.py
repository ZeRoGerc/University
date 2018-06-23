from __future__ import print_function


def get_most_frequent_words(docs):
    words_freq = {}
    for doc in docs:
        for w in doc.features.words():
            if w not in words_freq:
                words_freq[w] = 0
            words_freq[w] += 1

    r = sorted(words_freq.keys(), key=lambda w: words_freq[w])
    r = filter(lambda x: not x.isdigit(), r)
    r.reverse()
    return r


def get_word2idx(words):
    word2idx = {}
    for i in range(len(words)):
        word2idx[words[i]] = i
    return word2idx

def write_words(words, filename='freq_words.txt', limit=3000):
    with open(filename, 'wb') as f:
        for w in words[:limit]:
            f.write((w + '\n').encode('utf-8'))


def read_words(filename='freq_words.txt'):
    words = []
    with open(filename, 'rb') as filename:
        for line in filename:
            words.append(line.decode('utf-8')[:-1])

    return words


if __name__ == '__main__':
    for w in read_words():
        print(w)
