use std::str;
use std::usize;
use std::cmp;
use std::collections::vec_deque::VecDeque;
use std::i64::MAX;

struct Edge {
    pub to: usize,
    pub rev: usize,
    pub cap: i64,
}

pub struct Dinitz {
    g: Vec<Vec<Edge>>,
    level: Vec<i32>,
    iter: Vec<usize>,
}

impl Dinitz {
    pub fn new(v: usize) -> Dinitz {
        let mut g: Vec<Vec<Edge>> = Vec::new();
        for _ in 0..v {
            g.push(Vec::new());
        }
        Dinitz {
            g: g,
            level: vec![0;v],
            iter: vec![0;v],
        }
    }

    pub fn add_edge(&mut self, from: usize, to: usize, cap: i64) {
        let to_len = self.g[to].len();
        let from_len = self.g[from].len();
        self.g[from].push(Edge {
                              to: to,
                              rev: to_len,
                              cap: cap,
                          });
        self.g[to].push(Edge {
                            to: from,
                            rev: from_len,
                            cap: 0,
                        });
    }

    fn dfs(&mut self, v: usize, t: usize, f: i64) -> i64 {
        if v == t {
            return f;
        }
        while self.iter[v] < self.g[v].len() {

            let (e_cap, e_to, e_rev);
            {
                let ref e = self.g[v][self.iter[v]];
                e_cap = e.cap;
                e_to = e.to;
                e_rev = e.rev;
            }
            if e_cap > 0 && self.level[v] < self.level[e_to] {
                let d = self.dfs(e_to, t, cmp::min(f, e_cap));
                if d > 0 {
                    {
                        let ref mut e = self.g[v][self.iter[v]];
                        e.cap -= d;
                    }
                    {
                        let ref mut rev_edge = self.g[e_to][e_rev];
                        rev_edge.cap += d;
                    }
                    return d;
                }
            }
            self.iter[v] += 1;
        }

        return 0;
    }

    fn bfs(&mut self, s: usize) {
        let v = self.level.len();
        self.level = vec![-1;v];
        self.level[s] = 0;
        let mut deque = VecDeque::new();
        deque.push_back(s);
        while !deque.is_empty() {
            let v = deque.pop_front().unwrap();
            for e in &self.g[v] {
                if e.cap > 0 && self.level[e.to] < 0 {
                    self.level[e.to] = self.level[v] + 1;
                    deque.push_back(e.to);
                }
            }
        }
    }

    pub fn max_flow(&mut self, s: usize, t: usize) -> i64 {
        let v = self.level.len();
        let mut flow: i64 = 0;
        loop {
            self.bfs(s);
            if self.level[t] < 0 {
                return flow;
            }
            self.iter = vec![0;v];
            loop {
                let f = self.dfs(s, t, MAX);
                if f == 0 {
                    break;
                }
                flow += f;
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use test_helper::load_test_cases;

    #[test]
    fn solve_grl_6_a() {
        let mut input: VecDeque<i64> = load_test_cases("./resources/GRL_6_A.in");
        let mut output: VecDeque<i64> = load_test_cases("./resources/GRL_6_A.out");

        let test_cases = input.pop_front().unwrap() as usize;
        assert_eq!(test_cases, output.pop_front().unwrap() as usize);
        for _ in 0..test_cases {
            let v = input.pop_front().unwrap() as usize;
            let e = input.pop_front().unwrap() as usize;

            let mut dinitz = Dinitz::new(v);
            for _ in 0..e {
                let from = input.pop_front().unwrap() as usize;
                let to = input.pop_front().unwrap() as usize;
                let c = input.pop_front().unwrap();
                dinitz.add_edge(from, to, c);
            }
            let flow = dinitz.max_flow(0, v - 1);
            let ans = output.pop_front().unwrap();
            assert_eq!(flow, ans);
        }
    }
}