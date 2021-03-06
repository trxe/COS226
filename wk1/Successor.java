class Successor {
	private int[] next;

	Successor(int N) {
		next = new int[N];
		for (int i = 0; i < N; i++) {
			next[i] = i;
		}
	}

	void remove(int k) {
		if (next[k] == k) {
			next[k]++;
		} else {
			next[k] = next[next[k]];
		}
	}

	int findSucc(int k) {
		int r = k;
		while (next[r] != r) {
			next[k] = next[next[k]];
			r = next[r];
		}
		return  r;
	}

	void printSucc(int k) {
		System.out.println("succ to " + k + ": " + findSucc(k));
	}

	private static void main(String args[]) {
		int n = 500;
		Successor succ = new Successor(n);
		succ.remove(3);
		succ.remove(303);
		succ.remove(304);
		succ.remove(305);
		succ.printSucc(2);
		succ.printSucc(3);
		succ.printSucc(303);
		succ.remove(303);
		succ.printSucc(303);
	}
}
