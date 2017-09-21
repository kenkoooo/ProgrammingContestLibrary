struct UnionFind {
    parent: Vec<usize>,
    sizes: Vec<usize>,
    size: usize,
}

impl UnionFind {
    fn new(n: usize) -> UnionFind {
        UnionFind {
            parent: (0..n).map(|i| { i }).collect::<Vec<usize>>(),
            sizes: vec![1; n],
            size: n,
        }
    }

    fn find(&mut self, x: usize) -> usize {
        if x == self.parent[x] {
            x
        } else {
            let px = self.parent[x];
            self.parent[x] = self.find(px);
            self.parent[x]
        }
    }

    fn unite(&mut self, x: usize, y: usize) -> bool {
        let fx = self.find(x);
        let fy = self.find(y);
        if fx == fy {
            return false;
        }

        let (tx, ty) = if self.sizes[fx] < self.sizes[fy] {
            (fy, fx)
        } else {
            (fx, fy)
        };

        self.parent[ty] = tx;
        self.sizes[tx] += self.sizes[ty];
        self.sizes[ty] = 0;
        self.size -= 1;
        return true;
    }
}


#[cfg(test)]
mod test {
    use super::*;
    use test_helper::load_test_cases;

    #[test]
    fn solve_dsl_1_a() {
        for i in 1..33 {
            let input_file: String = format!("./resources/DSL_1_A/DSL_1_A_{}.in", i);
            let output_file: String = format!("./resources/DSL_1_A/DSL_1_A_{}.out", i);
            let mut input = load_test_cases(&input_file);
            let mut output = load_test_cases(&output_file);

            let n = input.pop_front().unwrap() as usize;
            let q = input.pop_front().unwrap() as usize;
            let mut uf = UnionFind::new(n);
            for _ in 0..q {
                let com = input.pop_front().unwrap();
                let x = input.pop_front().unwrap() as usize;
                let y = input.pop_front().unwrap() as usize;
                if com == 0 {
                    uf.unite(x, y);
                } else {
                    let ans = if uf.find(x) == uf.find(y) {
                        1
                    } else {
                        0
                    };
                    assert_eq!(ans, output.pop_front().unwrap());
                }
            }
        }
    }
}