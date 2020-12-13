class SocialNet {
	private int[] id;
	private int[] sz;

	SocialNet(int N) {
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}

	int root(int k) {
		int r = k;
		while (id[r] != r) {
			r = id[r];
		}
		return r;
	}

	void newFriend(int p, int q) { // find root of p and q, see which is smaller.
		int rootP = root(p);
		int rootQ = root(q);
		if (sz[rootP] < sz[rootQ]) {
			id[p] = rootQ;
			sz[rootQ] += sz[rootP];
		} else {
			id[q] = rootP;
			sz[rootP] += sz[rootQ];
		}
	}

	boolean isConnected(int p, int q) {
		return root(p) == root(q);
	}

	int find(int k) {
		
	}
}