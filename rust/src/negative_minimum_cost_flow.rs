use std::cmp::Ordering;
use std::cmp;
use std::collections::BinaryHeap;

pub struct NegativeMinimumCostFlow {
    n: usize,
    top: Vec<usize>,
    graph: Vec<Vec<Edge>>,
}

struct Edge {
    to: usize,
    cap: i64,
    cost: i64,
    rev: usize,
}

#[derive(Debug, Copy, Clone, Eq, PartialEq)]
struct Q {
    dist: i64,
    v: usize,
}

impl Ord for Q {
    fn cmp(&self, other: &Q) -> Ordering {
        other.dist.cmp(&self.dist)
    }
}

impl PartialOrd for Q {
    fn partial_cmp(&self, other: &Q) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl NegativeMinimumCostFlow {
    fn new(n: usize) -> NegativeMinimumCostFlow {
        NegativeMinimumCostFlow {
            n: n,
            top: vec![0; n],
            graph: (0..n).map(|_| { Vec::new() }).collect::<Vec<_>>(),
        }
    }

    fn add_edge(&mut self, from: usize, to: usize, cap: i64, cost: i64) {
        let e1 = Edge { to: to, cap: cap, cost: cost, rev: self.graph[to].len() };
        let e2 = Edge { to: from, cap: 0, cost: -cost, rev: self.graph[from].len() };
        self.graph[from].push(e1);
        self.graph[to].push(e2);
    }

    fn flow(&mut self, s: usize, t: usize, f: i64) -> i64 {
        let inf = 1_000_000_000_000_000_000;

        let mut result = 0;
        let mut f = f;
        let mut h = vec![0; self.n];
        for i in 0..self.n {
            let v = self.top[i];
            for edge in &self.graph[v] {
                if edge.cap == 0 {
                    continue;
                }
                let u = edge.to;
                h[u] = cmp::min(h[u], h[v] + edge.cost);
            }
        }

        while f > 0 {
            let mut prevv = vec![0; self.n];
            let mut preve = vec![0; self.n];
            let mut queue = BinaryHeap::new();
            let mut dist = vec![inf; self.n];
            dist[s] = 0;
            queue.push(Q { dist: 0, v: s });

            while !queue.is_empty() {
                let q = queue.pop().unwrap();
                let v = q.v;
                if dist[v] < q.dist {
                    continue;
                }

                for i in 0..self.graph[v].len() {
                    let e = &self.graph[v][i];
                    if e.cap > 0 && dist[e.to] > dist[v] + e.cost + h[v] - h[e.to] {
                        dist[e.to] = dist[v] + e.cost + h[v] - h[e.to];

                        prevv[e.to] = v;
                        preve[e.to] = i;
                        queue.push(Q { dist: dist[e.to], v: e.to });
                    }
                }
            }

            if dist[t] == inf {
                return -1;
            }

            for i in 0..self.n {
                h[i] += dist[i];
            }

            let d = {
                let mut d = f;
                let mut v = t;
                while v != s {
                    d = cmp::min(d, self.graph[prevv[v]][preve[v]].cap);
                    v = prevv[v];
                }
                d
            };
            f -= d;
            result += d * h[t];

            let mut v = t;
            while v != s {
                self.graph[prevv[v]][preve[v]].cap -= d;
                let rev = self.graph[prevv[v]][preve[v]].rev;
                self.graph[v][rev].cap += d;
                v = prevv[v];
            }
        }
        result
    }
}

#[cfg(test)]
mod test {
    use super::*;
    use test_helper::load_test_cases;

    #[test]
    fn solve_jag_summer_2011_day2_a() {
        for i in 1..39 {
            let input_file: String = format!("./resources/summer-camp-2011-day2-A/in{}.txt", i);
            let output_file: String = format!("./resources/summer-camp-2011-day2-A/out{}.txt", i);
            let mut input = load_test_cases(&input_file);
            let mut output = load_test_cases(&output_file);

            let n = input.pop_front().unwrap() as usize;
            let v = input.pop_front().unwrap();
            let sl = input.pop_front().unwrap();
            let sr = input.pop_front().unwrap();
            let xtp = (0..n).map(|_| {
                vec![input.pop_front().unwrap(), input.pop_front().unwrap(), input.pop_front().unwrap()]
            }).collect::<Vec<_>>();
            let x = (0..n).map(|i| { xtp[i][0] }).collect::<Vec<_>>();
            let t = (0..n).map(|i| { xtp[i][1] }).collect::<Vec<_>>();
            let p = (0..n).map(|i| { xtp[i][2] }).collect::<Vec<_>>();

            let source = 2 * n;
            let sink = source + 1;
            let l = source + 2;
            let r = source + 3;
            let mut mcf = NegativeMinimumCostFlow::new(r + 1);

            for i in 0..n {
                for j in 0..n {
                    if i == j {
                        continue;
                    }

                    if (t[i] - t[j]).abs() * v >= (x[i] - x[j]).abs() {
                        mcf.add_edge(i + n, j, 2, 0);
                    }
                }
            }
        }
    }
}