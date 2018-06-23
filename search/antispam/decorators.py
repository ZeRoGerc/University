# -*- coding: UTF-8 -*-

def to_utf8(text):
    if isinstance(text, unicode): text = text.encode('utf8')
    return text


def convert2unicode(f):
    def tmp(text):
        if not isinstance(text, unicode): text = text.decode('utf8')
        return f(text)

    return tmp


def convert2lower(f):
    def tmp(text):
        return f(text.lower())

    return tmp
