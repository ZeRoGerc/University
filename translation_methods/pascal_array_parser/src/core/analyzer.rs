use nom::{is_alphabetic, is_digit};
use std::result;
use std::str::*;
use utils::*;
use std::clone::Clone;

pub struct LexicalAnalyzer<'a> {
    word: Chars<'a>,
    cur_char: char,
    cur_token: Result<Token, String>
}

impl<'a> LexicalAnalyzer<'a> {
    pub fn new(word: &'a mut String) -> LexicalAnalyzer<'a> {
        word.push('$'); // mark the end of the string
        let mut word_chars = word.as_str().chars();
        LexicalAnalyzer {
            cur_char: word_chars.next().unwrap(),
            word: word_chars,
            cur_token: Err("Parsing not started!".to_string())
        }
    }

    pub fn cur_token(&self) -> Result<Token, String> {
        self.cur_token.clone()
    }

    pub fn next_token(&mut self) {
        while is_blank(self.cur_char) {
            self.cur_char = self.word.next().unwrap();
        }

        self.cur_token = match self.cur_char {
            '$' => Ok(Token::End),
            ':' => self.next_and_return(Token::Colon),
            ';' => self.next_and_return(Token::Semicolon),
            ',' => self.next_and_return(Token::Comma),
            '[' => self.next_and_return(Token::LeftBr),
            ']' => self.next_and_return(Token::RightBr),
            '.' => self.read_range(),
            '-' => self.read_number(),
            '0' ... '9' => self.read_number(),
            symbol if (is_alphabetic(symbol as u8) || symbol == '_') =>
                self.read_variable_name_or_keyword(),
            any =>
                Err(format!("Could not read token near the symbol: {}", any))
        };
    }

    fn read_range(&mut self) -> result::Result<Token, String> {
        self.cur_char = self.word.next().unwrap();
        match self.cur_char {
            '.' => self.next_and_return(Token::Range),
            any => Err(format!("Expected . but {} found", any))
        }
    }

    fn read_number(&mut self) -> result::Result<Token, String> {
        let mut already_read = String::new();
        if is_digit(self.cur_char as u8) {
            already_read.push(self.cur_char);
        }

        loop {
            self.cur_char = self.word.next().unwrap();

            if !is_digit(self.cur_char as u8) {
                break;
            }

            already_read.push(self.cur_char);
        }

        if already_read.as_str().len() > 1 && already_read.chars().nth(0).unwrap() == '0' {
            return Err(format!("Incorrect number {:?}", already_read))
        }

        Ok(Token::Number)
    }

    fn read_variable_name_or_keyword(&mut self) -> result::Result<Token, String> {
        let mut already_read = String::new();
        already_read.push(self.cur_char);

        loop {
            self.cur_char = self.word.next().unwrap();

            if !SYMBOLS.is_match(self.cur_char.to_string().as_str()) {
                break;
            }

            already_read.push(self.cur_char);
        }

        match already_read.as_str() {
            "var" => Ok(Token::Var),
            "array" => Ok(Token::Array),
            "of" => Ok(Token::Of),
            t if (TYPES.contains(t)) => Ok(Token::Type),
            _ => Ok(Token::Variable),
        }
    }

    fn next_and_return(&mut self, token: Token) -> result::Result<Token, String> {
        self.cur_char = self.word.next().unwrap();
        Ok(token)
    }
}