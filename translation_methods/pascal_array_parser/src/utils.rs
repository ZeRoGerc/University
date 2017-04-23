use std::collections::HashSet;
use regex::Regex;

lazy_static! {
    pub static ref SYMBOLS: Regex = Regex::new(r"[\w|\d|_]").unwrap();
}

lazy_static! {

    pub static ref TYPES: HashSet<&'static str> = {
        let mut t = HashSet::new();
        t.insert("byte");
        t.insert("integer");
        t.insert("real");
        t.insert("double");
        t.insert("string");
        t.insert("char");
        t
    };
}

#[derive(Clone, Debug, PartialEq)]
pub enum Token {
    Var,
    Variable,
    Colon,
    Array,
    Range,
    Of,
    Type,
    LeftBr,
    RightBr,
    Number,
    Semicolon,
    End,
}

pub fn is_blank(c: char) -> bool {
    c == ' ' || c == '\r' || c == '\n' || c == '\t'
}