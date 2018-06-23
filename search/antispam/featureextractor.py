# -*- coding: UTF-8 -*-
from decorators import *
import zlib


class Features:
    def __init__(self, html_info, tokenizer):
        self.html_info = html_info
        self.tokenizer = tokenizer
        self._init_vector_()

    def feature_vector(self):
        return [self.words_num, self.avg_word_len, self.title_words_num, self.anchor_words_num, self.compression_level]

    def words(self):
        title_words, text_words, anchor_words = self._extract_words()
        return title_words + text_words + anchor_words

    def _init_vector_(self):
        title_words, text_words, anchor_words = self._extract_words()
        html_bytes = (' '.join(title_words + text_words + anchor_words)).encode('utf-8')

        self.words_num = len(text_words)
        self.avg_word_len = sum([len(w) for w in text_words]) / (self.words_num + 1)
        self.title_words_num = len(title_words)
        self.anchor_words_num = len(anchor_words)
        self.compression_level = len(zlib.compress(html_bytes)) / (len(html_bytes) + 1)

    def _extract_words(self):
        title = ''.join(self.html_info.title).lower()
        text = ''.join(self.html_info.text).lower()
        anchor = ''.join(self.html_info.anchor).lower()

        title_words = list(self.tokenizer(title))
        text_words = list(self.tokenizer(text))
        anchor_words = list(self.tokenizer(anchor))

        return title_words, text_words, anchor_words


@convert2lower
@convert2unicode
def easy_tokenizer(text):
    word = unicode()
    for symbol in text:
        if symbol.isalnum():
            word += symbol
        elif word:
            yield word
            word = unicode()
    if word: yield word


PYMORPHY_CACHE = {}
MORPH = None


def get_lemmatizer():
    import pymorphy2
    global MORPH
    if MORPH is None: MORPH = pymorphy2.MorphAnalyzer()
    return MORPH


@convert2lower
@convert2unicode
def pymorphy_tokenizer(text):
    global PYMORPHY_CACHE
    for word in easy_tokenizer(text):
        word_hash = hash(word)
        if word_hash not in PYMORPHY_CACHE:
            PYMORPHY_CACHE[word_hash] = get_lemmatizer().parse(word)[0].normal_form
        yield PYMORPHY_CACHE[word_hash]


def calc_features(url, raw_html, info_extractor, tokenizer=easy_tokenizer):
    html_info = info_extractor(raw_html)
    #     html_info.pretty_print()
    features = Features(html_info, tokenizer)

    return features
