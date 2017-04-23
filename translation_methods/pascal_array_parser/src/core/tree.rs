pub struct Tree {
    pub value: String,
    pub children: Vec<Tree>
}

impl Tree {

    pub fn leaf(node: &str) -> Tree {
        Tree { value: node.to_string(), children: Vec::new() }
    }

    pub fn node(node: &str, children: Vec<Tree>) -> Tree {
        Tree { value: node.to_string(), children: children }
    }
}