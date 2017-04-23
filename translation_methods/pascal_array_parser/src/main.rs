extern crate pascal_array_parser;

use pascal_array_parser::core::*;
use std::env;

fn main() {
    let mut word = env::args().nth(1).unwrap();

    let mut parser = Parser::new(&mut word);

    match parser.parse() {
        Ok(tree) => {
            println!("{:?}", tree.value);
            for c in &tree.children {
                println!("    {:?}", c.value);
                for c1 in &c.children {
                    println!("        {:?}", c1.value);
                }
            }
        }
        Err(message) => {
            println!("{:?}", message);
        }
    }

}