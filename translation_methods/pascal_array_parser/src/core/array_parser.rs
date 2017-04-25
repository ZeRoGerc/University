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
        let mut children = Vec::new();

        let res = self.parse_var().and_then(|t| Ok(children.push(t)))
            .and_then(|_| self.check_and_next(":", &mut children, Token::Colon))
            .and_then(|_| self.parse_type().and_then(|t| Ok(children.push(t))));

        match res {
            Ok(_) => self.check_follow_and_return(Tree::node("S", children), &[Token::End]),
            Err(message) => Err(message)
        }
    }

    fn parse_var(&mut self) -> Result<Tree, String> {
        let mut children = Vec::new();

        let res = self.check_and_next("var", &mut children, Token::Var)
            .and_then(|_| self.check_and_next("variable", &mut children, Token::Variable));

        match res {
            Ok(_) => self.check_follow_and_return(Tree::node("V", children), &[Token::Colon]),
            Err(message) => Err(message)
        }
    }

    fn parse_type(&mut self) -> Result<Tree, String> {
        let mut children = Vec::new();

        let res = self.check_and_next("array", &mut children, Token::Array)
            .and_then(|_| self.check_and_next("[", &mut children, Token::LeftBr))
            .and_then(|_| self.parse_range().and_then(|t| Ok(children.push(t))))
            .and_then(|_| self.check_and_next("]", &mut children, Token::RightBr))
            .and_then(|_| self.check_and_next("of", &mut children, Token::Of))
            .and_then(|_| self.check_and_next("type", &mut children, Token::Type))
            .and_then(|_| self.check_and_next("semicolon", &mut children, Token::Semicolon));

        match res {
            Ok(_) => self.check_follow_and_return(Tree::node("T", children), &[Token::End]),
            Err(message) => Err(message)
        }
    }

    fn parse_range(&mut self) -> Result<Tree, String> {
        let mut children = Vec::new();

        let res = self.parse_final_range()
            .and_then(|t| {
                children.push(t);
                self.parse_comma_separated()
            })
            .and_then(|t| {
                Ok(children.push(t))
            });

        match res {
            Ok(_) => self.check_follow_and_return(Tree::node("R", children), &[Token::RightBr]),
            Err(message) => Err(message)
        }
    }

    fn parse_final_range(&mut self) -> Result<Tree, String> {
        let mut children = Vec::new();

        let res = self.parse_bound().and_then(|t| Ok(children.push(t)))
            .and_then(|_| self.check_and_next("..", &mut children, Token::Range))
            .and_then(|_| self.parse_bound().and_then(|t| Ok(children.push(t))));

        match res {
            Ok(_) =>
                self.check_follow_and_return(
                    Tree::node("R\'", children),
                    &[Token::Comma, Token::RightBr]
                ),
            Err(message) => Err(message)
        }
    }

    fn parse_bound(&mut self) -> Result<Tree, String> {
        match self.analyzer.cur_token() {
            Ok(Token::Variable) => {
                self.analyzer.next_token();
                self.check_follow_and_return(
                    Tree::node("N", vec![Tree::leaf("variable")]),
                    &[Token::Comma, Token::RightBr, Token::Range]
                )
            },
            Ok(Token::Number) => {
                self.analyzer.next_token();
                self.check_follow_and_return(
                    Tree::node("N", vec![Tree::leaf("number")]),
                    &[Token::Comma, Token::RightBr, Token::Range]
                )
            },
            Ok(any) => {
                Err(format!("Expected {:?} or {:?} but {:?} found", Token::Variable, Token::Number, any))
            }
            Err(message) => Err(message)
        }
    }

    fn parse_comma_separated(&mut self) -> Result<Tree, String> {
        let mut children = Vec::new();

        match self.analyzer.cur_token() {
            Ok(Token::Comma) => {
                children.push(Tree::leaf(","));
                self.analyzer.next_token();

                let res = self.parse_range()
                    .and_then(|t| Ok(children.push(t)));

                match res {
                    Ok(_) => {
                        self.check_follow_and_return(
                            Tree::node("A", children),
                            &[Token::RightBr]
                        )
                    }
                    Err(message) => Err(message)
                }
            }
            Ok(Token::RightBr) =>
                self.check_follow_and_return(
                    Tree::node("A", vec![Tree::leaf("ε")]),
                    &[Token::RightBr]
                ),
            Ok(any) => Parser::expected_but(Token::RightBr, any),
            Err(message) => Err(message)
        }
    }

    fn check_and_next(&mut self, node: &str, children: &mut Vec<Tree>, need: Token) -> Result<Tree, String> {
        match self.analyzer.cur_token() {
            Ok(token) => {
                self.analyzer.next_token();
                if token == need {
                    children.push(Tree::leaf(node));
                    return Ok(Tree::leaf(node))
                }
                Parser::expected_but(need, token)
            }
            Err(message) => Err(message)
        }
    }

    fn check_follow_and_return(&self, tree: Tree, follow: &[Token]) -> Result<Tree, String> {
        match self.analyzer.cur_token() {
            Ok(token) => {
                for t in follow {
                    if token == *t { return Ok(tree) }
                }

                Err(format!("Expected one of {:?} but {:?} found", follow, token))
            }
            Err(message) => Err(message)
        }
    }

    fn expected_but(need: Token, actual: Token) -> Result<Tree, String> {
        Err(format!("Expected token {:?} but {:?} found", need, actual))
    }
}