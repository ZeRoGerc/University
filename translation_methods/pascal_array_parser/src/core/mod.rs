pub use self::analyzer::*;
pub use self::tree::*;
pub use self::array_parser::*;

pub mod analyzer;
pub mod tree;
pub mod array_parser;

#[cfg(test)]
mod tests {
    use utils::*;
    use super::*;

    #[test]
    fn lexical_analyzer_works() {
        let mut declaration = String::from("var x: array [1..10] of integer;");

        let mut analyzer = LexicalAnalyzer::new(&mut declaration);

        assert_token(&mut analyzer, Token::Var);
        assert_token(&mut analyzer, Token::Variable);
        assert_token(&mut analyzer, Token::Colon);
        assert_token(&mut analyzer, Token::Array);
        assert_token(&mut analyzer, Token::LeftBr);
        assert_token(&mut analyzer, Token::Number);
        assert_token(&mut analyzer, Token::Range);
        assert_token(&mut analyzer, Token::Number);
        assert_token(&mut analyzer, Token::RightBr);
        assert_token(&mut analyzer, Token::Of);
        assert_token(&mut analyzer, Token::Type);
        assert_token(&mut analyzer, Token::Semicolon);
        assert_token(&mut analyzer, Token::End);
    }

    fn assert_token(analyzer: &mut LexicalAnalyzer, desired_token: Token) {
        analyzer.next_token();
        assert_eq!(analyzer.cur_token().unwrap(), desired_token);
    }

    #[test]
    fn tree_constructs() {
        let parent = Tree::node("parent", vec![
            Tree::leaf("leaf_1"),
            Tree::leaf("leaf_2"),
            Tree::leaf("leaf_3")
        ]);

        assert_eq!(parent.value, "parent");
        assert_eq!(parent.children[0].value, "leaf_1");
        assert_eq!(parent.children[1].value, "leaf_2");
        assert_eq!(parent.children[2].value, "leaf_3");
    }
}