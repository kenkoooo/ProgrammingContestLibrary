use std::time::Instant;

pub fn radix_sort(input: &mut Vec<u64>) {
    let mut count = vec![0; 2];
    let mut out = vec![0; input.len()];
    for p in 0..64 {
        count_sort(input, p, &mut count, &mut out);
    }
}

fn count_sort(input: &mut Vec<u64>, place: usize, count: &mut Vec<usize>, out: &mut Vec<u64>) {
    let n = input.len();
    for i in 0..n {
        let digit = ((input[i] >> place) & 1) as usize;
        count[digit] += 1;
    }
    count[1] += count[0];
    for i in (0..n).rev() {
        let digit = ((input[i] >> place) & 1) as usize;
        out[count[digit] - 1] = input[i];
        count[digit] -= 1;
    }
    for i in 0..n {
        input[i] = out[i];
    }
    count[0] = 0;
    count[1] = 0;
}

#[cfg(test)]
mod test {
    extern crate rand;

    use super::*;
    use radix_sort::test::rand::Rng;

    #[test]
    fn it_works_random_array() {
        let n = 50000;
        let mut arr = (0..n).map(|_| {
            rand::thread_rng().gen::<u64>()
        }).collect::<Vec<_>>();

        let mut expected = arr.clone();
        let standard = Instant::now();
        expected.sort();
        println!("{}.{:03}", standard.elapsed().as_secs(), standard.elapsed().subsec_nanos() / 1_000_000);

        let radix = Instant::now();
        radix_sort(&mut arr);
        println!("{}.{:03}", radix.elapsed().as_secs(), radix.elapsed().subsec_nanos() / 1_000_000);
//        assert_eq!(expected, arr);
    }
}