extern crate pascal_array_parser;

use pascal_array_parser::core::*;
use std::env;

fn print_tree(tree: & Tree, id: i32) -> i32 {
    println!("{} [ label = {:?} ]", id, tree.value);
    let mut child_id = id;
    for node in &tree.children {
        child_id += 1;
        println!("{} -> {}", id, child_id);
        child_id = print_tree(&node, child_id);
    }
    child_id
}

fn main() {
    let mut word = env::args().nth(1).unwrap();

    let mut parser = Parser::new(&mut word);

    match parser.parse() {
        Ok(tree) => {
            println!("digraph G {{");
            print_tree(&tree, 1);
            println!("}}");
        }
        Err(message) => {
            println!("{:?}", message);
        }
    }

}