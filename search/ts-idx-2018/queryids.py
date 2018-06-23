class IdMerger:
    MAX_ID = -1

    def __init__(self, ids):
        self.ids = ids

    def apply_not(self):
        i = 0
        id = 0
        res = []
        while id < IdMerger.MAX_ID:
            if i < len(self.ids):
                if id == self.ids[i]:
                    i += 1
                else:
                    res.append(id)
            else:
                res.append(id)

            id += 1

        self.ids = res
        return self

    def apply_and(self, other):
        i = 0
        j = 0

        result = []
        while (i < len(self.ids)) and (j < len(other.ids)):
            if self.ids[i] == other.ids[j]:
                result.append(self.ids[i])
                i += 1
                j += 1
            else:
                if self.ids[i] < other.ids[j]:
                    i += 1
                else:
                    j += 1

        self.ids = result
        return self

    def apply_or(self, other):
        i = 0
        j = 0

        result = []
        while (i < len(self.ids)) and (j < len(other.ids)):
            if self.ids[i] == other.ids[j]:
                result.append(self.ids[i])
                i += 1
                j += 1
            else:
                if self.ids[i] < other.ids[j]:
                    result.append(self.ids[i])
                    i += 1
                else:
                    result.append(other.ids[j])
                    j += 1

        while i < len(self.ids):
            result.append(self.ids[i])
            i += 1

        while j < len(other.ids):
            result.append(other.ids[j])
            j += 1

        self.ids = result
        return self


if __name__ == '__main__':
    IdMerger.MAX_ID = 5
    m = IdMerger([1, 2, 3])
    print m.apply_not().ids
