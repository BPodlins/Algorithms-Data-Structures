package com.example.algorithmsanddatastructures.DataStructures.VanEmdeBoasTree;

import java.util.ArrayList;

class VanEmdeBoasTree {
    public int universe_size;
    public int minimum;
    public int maximum;
    public VanEmdeBoasTree summary;
    public ArrayList<VanEmdeBoasTree> clusters;

    public VanEmdeBoasTree(int size) {
        universe_size = size;
        minimum = -1;
        maximum = -1;

        if (size <= 2) {
            summary = null;
            clusters = new ArrayList<>(0);
        } else {
            int no_clusters = (int) Math.ceil(Math.sqrt(size));
            summary = new VanEmdeBoasTree(no_clusters);
            clusters = new ArrayList<>(no_clusters);

            for (int i = 0; i < no_clusters; i++) {
                clusters.add(new VanEmdeBoasTree((int) Math.ceil(Math.sqrt(size))));
            }
        }
    }

    public int high(int x) {
        return x / (int) Math.ceil(Math.sqrt(universe_size));
    }

    public int low(int x) {
        return x % (int) Math.ceil(Math.sqrt(universe_size));
    }

    public int generate_index(int x, int y) {
        return x * (int) Math.ceil(Math.sqrt(universe_size)) + y;
    }

    // function to insert a key into the tree
    // insert method to distinguish between inserting into the tree, its summary, or its clusters.
    public void insert(int key) {
        // if the tree is empty, set the key as both the minimum and maximum.
        if (minimum == -1) {
            minimum = key;
            maximum = key;
            return;
        }

        // if the key is less than the current minimum, swap them.
        if (key < minimum) {
            int temp = key;
            key = minimum;
            minimum = temp;
        }

        // for non-base cases, proceed with inserting into clusters.
        if (universe_size > 2) {
            int clusterIndex = high(key);
            // if the cluster is empty, insert the key into the summary and adjust the cluster's minimum and maximum.
            if (clusters.get(clusterIndex).minimum == -1) {
                insertIntoSummary(clusterIndex);
                clusters.get(clusterIndex).minimum = low(key);
                clusters.get(clusterIndex).maximum = low(key);
            } else {
                // recursively insert into the appropriate cluster.
                clusters.get(clusterIndex).insert(low(key));
            }
        }

        // update the tree's maximum if necessary.
        if (key > maximum) {
            maximum = key;
        }
    }

    // inserts a cluster index into the summary.
    private void insertIntoSummary(int clusterIndex) {
        if (summary.minimum == -1) {
            summary.minimum = summary.maximum = clusterIndex;
        } else {
            if (clusterIndex < summary.minimum) {
                int temp = clusterIndex;
                clusterIndex = summary.minimum;
                summary.minimum = temp;
            }
            if (clusterIndex > summary.maximum) {
                summary.maximum = clusterIndex;
            }
            if (summary.universe_size > 2) {
                if (summary.clusters.get(high(clusterIndex)).minimum == -1) {
                    summary.insertIntoSummary(high(clusterIndex));
                    summary.clusters.get(high(clusterIndex)).minimum = low(clusterIndex);
                    summary.clusters.get(high(clusterIndex)).maximum = low(clusterIndex);
                } else {
                    summary.clusters.get(high(clusterIndex)).insert(low(clusterIndex));
                }
            }
        }
    }

    // function to check if a key is a member of the tree
    public boolean isMember(int key) {
        if (key < minimum || key > maximum) {
            return false;
        }
        if (key == minimum || key == maximum) {
            return true;
        }
        if (universe_size == 2) {
            return false;
        }
        return clusters.get(high(key)).isMember(low(key));
    }

    // function to find the successor of a given key in the tree
    public int successor(int key) {
        if (universe_size == 2) {
            if (key == 0 && maximum == 1) {
                return 1;
            } else {
                return -1;
            }
        } else if (minimum != -1 && key < minimum) {
            return minimum;
        } else {
            int maxLow = maximumInCluster(high(key));
            if (maxLow != -1 && low(key) < maxLow) {
                int offset = clusters.get(high(key)).successor(low(key));
                return generate_index(high(key), offset);
            } else {
                int succCluster = summary.successor(high(key));
                if (succCluster == -1) {
                    return -1;
                } else {
                    int offset = clusters.get(succCluster).minimum;
                    return generate_index(succCluster, offset);
                }
            }
        }
    }

    // function to find the predecessor of a given key in the tree
    public int predecessor(int key) {
        if (universe_size == 2) {
            if (key == 1 && minimum == 0) {
                return 0;
            } else {
                return -1;
            }
        } else if (maximum != -1 && key > maximum) {
            return maximum;
        } else {
            int minLow = minimumInCluster(high(key));
            if (minLow != -1 && low(key) > minLow) {
                int offset = clusters.get(high(key)).predecessor(low(key));
                return generate_index(high(key), offset);
            } else {
                int predCluster = summary.predecessor(high(key));
                if (predCluster == -1) {
                    if (minimum != -1 && key > minimum) {
                        return minimum;
                    } else {
                        return -1;
                    }
                } else {
                    int offset = clusters.get(predCluster).maximum;
                    return generate_index(predCluster, offset);
                }
            }
        }
    }

    private int maximumInCluster(int cluster) {
        if (clusters.get(cluster).maximum != -1) {
            return clusters.get(cluster).maximum;
        } else {
            return -1;
        }
    }

    private int minimumInCluster(int cluster) {
        if (clusters.get(cluster).minimum != -1) {
            return clusters.get(cluster).minimum;
        } else {
            return -1;
        }
    }
}
