use core::*;
use utils::*;

pub struct Parser<'a> {
    analyzer: LexicalAnalyzer<'a>
}

impl<'a> Parser<'a> {
    pub fn new(input_string: &'a mut String) -> Parser<'a> {
        Parser { analyzer: LexicalAnalyzer::new(input_string) }
    }

    pub fn parse(&mut self) -> Result<Tree, String> {
        self.analyzer.next_token();
        self.parse_s()
    }

    fn parse_s(&mut self) -> Result<Tree, String> {
        match self.analyzer.cur_token() {
            Ok(Token::Var) => {
                let mut children = Vec::new();

                let res = self.parse_var();
                if res.is_err() { return res } else { children.push(res.unwrap()) }

                let res = self.check_and_next(":", Token::Colon);
                if res.is_err() { return res } else { children.push(res.unwrap()) }

                let res = self.parse_type();
                if res.is_err() { return res } else { children.push(res.unwrap()) }

                Ok(Tree::node("S", children))
            }
            Ok(any) => Parser::unexpected(Token::Var, any),
            Err(message) => Err(message)
        }
    }

    fn parse_var(&mut self) -> Result<Tree, String> {
        match self.analyzer.cur_token() {
            Ok(Token::Var) => {
                let mut children = Vec::new();
                children.push(Tree::leaf("var"));

                self.analyzer.next_token();

                let res = self.check_and_next("variable", Token::Variable);
                if res.is_err() { return res } else { children.push(res.unwrap()) }

                return Ok(Tree::node("V", children));
            }
            Ok(any) => Parser::unexpected(Token::Var, any),
            Err(message) => Err(message)
        }
    }

    fn parse_type(&mut self) -> Result<Tree, String> {
        match self.analyzer.cur_token() {
            Ok(Token::Array) => {
                let mut children = Vec::new();
                children.push(Tree::leaf("array"));
                self.analyzer.next_token();

                let res = self.check_and_next("[", Token::LeftBr);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                let res = self.check_and_next("number", Token::Number);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                let res = self.check_and_next("..", Token::Range);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                let res = self.check_and_next("number", Token::Number);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                let res = self.check_and_next("]", Token::RightBr);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                let res = self.check_and_next("of", Token::Of);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                let res = self.check_and_next("type", Token::Type);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                let res = self.check_and_next("semicolon", Token::Semicolon);
                if res.is_err() { return res } else { children.push(res.unwrap()) };

                Ok(Tree::node("T", children))
            }
            Ok(any) => Parser::unexpected(Token::Array, any),
            Err(message) => Err(message)
        }
    }

    fn check_and_next(&mut self, node: &str, need: Token) -> Result<Tree, String> {
        match self.analyzer.cur_token() {
            Ok(token) => {
                self.analyzer.next_token();
                if token == need {
                    return Ok(Tree::leaf(node))
                }
                Parser::unexpected(need, token)
            }
            Err(message) => Err(message)
        }
    }


    fn unexpected(need: Token, actual: Token) -> Result<Tree, String> {
        Err(format!("Expected token {:?} but {:?} found", need, actual))
    }
}