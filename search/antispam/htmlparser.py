# -*- coding: UTF-8 -*-
from __future__ import unicode_literals

import re
from HTMLParser import HTMLParser

from decorators import *


@convert2unicode
def html2info_parser(text):
    parser = TextHTMLParser()
    parser.feed(text)
    return parser.info()


class TextHTMLParser(HTMLParser):
    def __init__(self):
        HTMLParser.__init__(self)
        self._text = []
        self._title = []
        self._anchor = []
        self._in_title = False
        self._in_anchor = False

    def text(self):
        return ''.join(self._text).strip()

    def title(self):
        return ''.join(self._title).strip()

    def info(self):
        return HtmlInfo(
            title=self._title,
            text=self._text,
            anchor=self._anchor
        )

    def handle_data(self, data):
        text = data.strip()
        if len(text) > 0:
            text = re.sub('[ \t\r\n]+', ' ', text)

            if self._in_title:
                self._title.append(text + ' ')
            elif self._in_anchor:
                self._anchor.append(text + ' ')
            else:
                self._text.append(text + ' ')

    def handle_starttag(self, tag, attrs):
        if tag == 'p':
            self._text.append('\n\n')
        elif tag == 'br':
            self._text.append('\n')
        elif tag == 'title':
            self._in_title = True
        elif tag == 'a':
            self._in_anchor = True

    def handle_endtag(self, tag):
        if tag == 'title':
            self._in_title = False
        elif tag == 'a':
            self._in_anchor = False

    def handle_startendtag(self, tag, attrs):
        if tag == 'br':
            self._text.append('\n\n')


class HtmlInfo:
    """
    title - array of words in title
    text - array of chunks in body
    anchor - array of anchor text
    """

    def __init__(self, title, text, anchor):
        self.title = title
        self.text = text
        self.anchor = anchor

    def pretty_print(self):
        print "\nTitle:"
        for w in self.title:
            print w

        print "\nText:"
        for w in self.text:
            print w

        print "\nAnchor:"
        for w in self.anchor:
            print w


# def html2text_bs(raw_html):
#     from bs4 import BeautifulSoup
#     """
#     Тут производится извлечения из html текста
#     """
#     soup = BeautifulSoup(raw_html, "html.parser")
#     [s.extract() for s in soup(['script', 'style'])]
#     return soup.get_text()

def html2info_bs_visible(raw_html):
    from bs4 import BeautifulSoup
    """
    Тут производится извлечения из html текста, который видим пользователю
    """
    soup = BeautifulSoup(raw_html, "html.parser")
    [s.extract() for s in soup(['style', 'script', '[document]', 'head', 'title'])]

    return HtmlInfo(
        title='',
        text=soup.get_text(),
        anchor=''
    )

# def html2text_boilerpipe(raw_html):
#     import boilerpipe
#     """
#     еще одна библиотека очень хорошо извлекающая именно видимый пользователю текст,
#     но она завязана на java
#     """
#     pass


if __name__ == '__main__':
    test_html_data = u'''
    <html>
    <title> Ололо ололош шик шика шишки шоколадкой Ololo </title>
    спам спама <a href="url">link text</a> <a href="url">гипер ссылка</a> людей группу 1 2 3
    </html>
    '''
    info = html2info_bs_visible(test_html_data)
    info.pretty_print()
