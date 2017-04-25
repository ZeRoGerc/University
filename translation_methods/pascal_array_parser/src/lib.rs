extern crate regex;

#[macro_use]
extern crate lazy_static;

#[macro_use]
extern crate nom;

pub mod core;
pub mod utils;

#[cfg(test)]
mod tests {
    use core::*;

    #[test]
    fn should_accept_base() {
        base_parse_test("var x: array[1..10] of char;", true);
    }

    #[test]
    fn should_accept_multidimensional() {
        base_parse_test("var x: array[1..10,1..124] of string;", true);
        base_parse_test("var x: array[1..10,1..124,1..124,1..124,1..124,1..124] of integer;", true);
    }

    #[test]
    fn should_accept_with_spaces() {
        base_parse_test("   var   x    : array [   1 .. 1245    ]   of integer  ;  ", true);
    }

    #[test]
    fn should_accept_negative_bounds() {
        base_parse_test("var x: array[-1000..-10] of char;", true);
    }

    #[test]
    fn should_accept_names_not_alphabetic_only() {
        base_parse_test("var __x__: array[1..10] of boolean;", true);
        base_parse_test("var x10: array[1..10] of integer;", true);
        base_parse_test("var __A10: array[1..10] of byte;", true);
    }

    #[test]
    fn should_reject_base() {
        base_parse_test("@ad$s%a& **g #dsgldak asdl;gkadlg ", false);
        base_parse_test("        ", false);
        base_parse_test("", false);
    }

    #[test]
    fn should_reject_wrong_follow() {
        base_parse_test("var x: array[1..10] of char; saasfsasag", false);
    }

    #[test]
    fn should_reject_wrong_names() {
        base_parse_test("var 1244x: array[1..10] of qword;", false);
        base_parse_test("var @x: array[1..10] of string;", false);
        base_parse_test("var $x: array[1..10] of boolean;", false);
    }

    #[test]
    fn should_reject_if_name_is_keyword_or_type() {
        base_parse_test("var var: array[1..10] of integer;", false);
        base_parse_test("var array: array[1..10] of int64;", false);
        base_parse_test("var integer: array[1..10] of string;", false);
    }

    #[test]
    fn should_reject_wrong_type() {
        base_parse_test("var 1244x: array[1..10] of XXX;", false);
    }

    #[test]
    fn should_reject_wrong_keywords() {
        base_parse_test("varr x: array[1..10] of int64;", false);
        base_parse_test("var x: arrray[1..10] of qword;", false);
        base_parse_test("var x: array[1..10] off char;", false);
    }

    #[test]
    fn should_reject_if_no_semicolon() {
        base_parse_test("var x: array[1..10] of integer", false);
    }

    fn base_parse_test(s: &str, expected: bool) {
        let mut temp = s.to_string();
        let mut p = Parser::new(&mut temp);
        let res = p.parse();

        assert_eq!(res.is_ok(), expected);
    }
}
