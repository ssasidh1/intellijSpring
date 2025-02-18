package com.example.demo.student;

import java.util.ArrayList;
import java.util.List;

    public class Sample {

        // Define the types of edit operations.
        enum OperationType {
            MATCH, INSERT, DELETE
        }

        // A simple class to store a diff operation.
        static class DiffOperation {
            OperationType type;
            String line;

            public DiffOperation(OperationType type, String line) {
                this.type = type;
                this.line = line;
            }

            @Override
            public String toString() {
                return type + ": " + line;
            }
        }

        /**
         * Computes the diff between two arrays of strings (representing lines of files)
         * using a simplified version of Myers’ algorithm.
         *
         * @param a The lines of the original file.
         * @param b The lines of the new file.
         * @return A list of DiffOperation representing the edit script.
         */
        public static List<DiffOperation> diff(String[] a, String[] b) {
            int N = a.length;
            int M = b.length;
            int max = N + M;
            // V array to hold the furthest x reached for a given diagonal.
            int size = 2 * max + 1;
            int[] V = new int[size];
            // The offset is used to handle negative indices.
            int offset = max;

            // Trace stores the V arrays for each iteration (for backtracking).
            List<int[]> trace = new ArrayList<>();

            // Main loop: for each number of edit steps D = 0 ... (N+M)
            for (int d = 0; d <= max; d++) {
                // Make a copy of V at the start of this iteration.
                int[] currentV = new int[size];
                System.arraycopy(V, 0, currentV, 0, size);

                // For each possible diagonal k = x - y.
                for (int k = -d; k <= d; k += 2) {
                    int index = k + offset;
                    int x;
                    // Decide whether we move "down" (an insertion) or "right" (a deletion).
                    if (k == -d || (k != d && V[index - 1] < V[index + 1])) {
                        x = V[index + 1]; // move down: insertion in a.
                    } else {
                        x = V[index - 1] + 1; // move right: deletion from a.
                    }
                    int y = x - k;
                    // Follow the "diagonal" (matching lines).
                    while (x < N && y < M && a[x].equals(b[y])) {
                        x++;
                        y++;
                    }
                    currentV[index] = x;

                    // If we’ve reached the end of both sequences, record the trace and backtrack.
                    if (x >= N && y >= M) {
                        trace.add(currentV);
                        return backtrack(trace, a, b, N, M, offset);
                    }
                }
                // Store the trace of this iteration.
                trace.add(currentV);
                // Update V for the next iteration.
                System.arraycopy(currentV, 0, V, 0, size);
            }
            throw new RuntimeException("Diff not found");
        }

        /**
         * Backtracks through the trace to reconstruct the edit script.
         */
        private static List<DiffOperation> backtrack(List<int[]> trace, String[] a, String[] b, int N, int M, int offset) {
            List<DiffOperation> result = new ArrayList<>();
            int x = N;
            int y = M;
            // Iterate backwards through the trace.
            for (int d = trace.size() - 1; d >= 0; d--) {
                int[] currentV = trace.get(d);
                int k = x - y;
                int index = k + offset;
                int prevK;
                // Determine which move was taken.
                if (k == -d || (k != d && currentV[index - 1] < currentV[index + 1])) {
                    prevK = k + 1;
                } else {
                    prevK = k - 1;
                }
                int prevX = currentV[prevK + offset];
                int prevY = prevX - prevK;

                // Add the matching (diagonal) operations.
                while (x > prevX && y > prevY) {
                    result.add(0, new DiffOperation(OperationType.MATCH, a[x - 1]));
                    x--;
                    y--;
                }
                if (d > 0) { // For all steps except the first one, determine the edit.
                    if (x == prevX) {
                        // Insertion: a line in b was added.
                        result.add(0, new DiffOperation(OperationType.INSERT, b[prevY]));
                    } else {
                        // Deletion: a line from a was removed.
                        result.add(0, new DiffOperation(OperationType.DELETE, a[prevX]));
                    }
                }
                x = prevX;
                y = prevY;
            }
            return result;
        }

        public static void main(String[] args) {
            // Example arrays representing lines from two versions of a file.
            String[] original = {
                    "line1",
                    "line2",
                    "line3",
                    "line4"
            };

            String[] modified = {
                    "line1",
                    "line2 modified",  // Modified line.
                    "line3",
                    "line4",
                    "line5"            // An extra inserted line.
            };

            // Compute the diff.
            List<DiffOperation> diffOps = diff(original, modified);

            // Print the resulting edit operations.
            System.out.println("Diff Operations:");
            for (DiffOperation op : diffOps) {
                System.out.println(op);
            }
        }
    }


