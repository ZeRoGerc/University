class Node:
    ALPHA = -1
    OMEGA = -2

    def evalute(self):
        pass

    def goto(self, doc_id):
        pass


class LeaveNode(Node):

    def __init__(self, ids):
        self.ids = ids
        self.doc_id = Node.ALPHA
        self.nearest_in_array = 0

    def evalute(self):
        if self.doc_id == Node.OMEGA:
            return Node.OMEGA

        if self.nearest_in_array == len(self.ids):
            return Node.OMEGA

        return self.ids[self.nearest_in_array]

    def goto(self, doc_id):
        self.doc_id = doc_id
        while self.nearest_in_array < len(self.ids) and self.doc_id > self.ids[self.nearest_in_array]:
            pass
