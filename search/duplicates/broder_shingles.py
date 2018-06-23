#!/usr/bin/env python

"""
This is just a draft for homework 'near-duplicates'
Use MinshinglesCounter to make result closer to checker
"""

import sys
import re
import mmh3
from docreader import DocumentStreamReader

WINDOW = 5
N = 20


class MinshinglesCounter:
    SPLIT_RGX = re.compile(r'\w+', re.U)

    def __init__(self, window=5, n=20):
        self.window = window
        self.n = n

    def count(self, text):
        words = MinshinglesCounter._extract_words(text)
        shs = self._count_shingles(words)
        mshs = self._select_minshingles(shs)

        if len(mshs) == self.n:
            return mshs

        if len(shs) >= self.n:
            return sorted(shs)[0:self.n]

        return None

    def _select_minshingles(self, shs):
        buckets = [None]*self.n
        for x in shs:
            bkt = x % self.n
            buckets[bkt] = x if buckets[bkt] is None else min(buckets[bkt], x)

        return filter(lambda a: a is not None, buckets)

    def _count_shingles(self, words):
        shingles = []
        for i in xrange(len(words) - self.window):
            h = mmh3.hash(' '.join(words[i:i+self.window]).encode('utf-8'))
            shingles.append(h)
        return sorted(shingles)

    @staticmethod
    def _extract_words(text):
        words = re.findall(MinshinglesCounter.SPLIT_RGX, text)
        return words


def main():
    mhc = MinshinglesCounter(window=WINDOW, n=N)

    # groups = {}
    docurls = []
    docshingles = []
    for path in sys.argv[1:]:
        reader = DocumentStreamReader(path)
        for doc in reader:
            docurls.append(doc.url)
            index = len(docurls) - 1

            minshingles = mhc.count(doc.text)
            docshingles.append(minshingles)

            # if minshingles is None:
            #     print("None")
            # if minshingles is not None:
            #     for i in range(N):
            #         key = (i, minshingles[i])
            #         if not (key in groups):
            #             groups[key] = []
            #         groups[key].append(index)

    number = 0
    for i in range(len(docurls)):
        for j in range(i + 1, len(docurls)):
            if (docshingles[i] is not None) and (docshingles[j] is not None):
                s = 0
                for k in range(N):
                    if docshingles[i][k] in docshingles[j]:
                        s += 1

                temp = s / float(s + 2 * (N - s))
                if temp > 0.75:
                    number += 1
                    print "{} {} {}".format(docurls[i], docurls[j], temp)

    # similarity = {}
    # for key in groups:
    #     for i in range(len(groups[key])):
    #         for j in range(i + 1, len(groups[key])):
    #             s_key = (groups[key][i], groups[key][j])
    #             if s_key not in similarity:
    #                 similarity[s_key] = 0
    #             similarity[s_key] += 1
    #
    # number = 0
    # for key in similarity:
    #     temp = similarity[key] / float(similarity[key] + 2 * (N - similarity[key]))
    #     if temp > 0.75:
    #         number += 1
    #         print "{} {} {}".format(docurls[key[0]], docurls[key[1]], temp)
    #
    print "Total = " + str(number)


if __name__ == '__main__':
    main()
