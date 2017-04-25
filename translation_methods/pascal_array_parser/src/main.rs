extern crate pascal_array_parser;

use pascal_array_parser::core::*;
use std::env;
use std::fs::File;
use std::io::prelude::*;

fn print_tree(file: &mut File, tree: & Tree, id: i32) -> i32 {
    write!(file, "{} [ label = {:?} ]\n", id, tree.value).unwrap();
    let mut child_id = id;
    for node in &tree.children {
        child_id += 1;
        write!(file, "{} -> {}\n", id, child_id).unwrap();
        child_id = print_tree(file, &node, child_id);
    }
    child_id
}

fn main() {
    let mut word = env::args().nth(1).unwrap();

    let mut parser = Parser::new(&mut word);

    let mut file = File::create("tree.txt").unwrap();

    match parser.parse() {
        Ok(tree) => {
            write!(file, "digraph G {{\n").unwrap();
            print_tree(&mut file, &tree, 1);
            write!(file, "}}").unwrap();
        }
        Err(message) => {
            println!("{:?}", message);
        }
    }

}