# vigil

I've decided to implement the solution using recursion. It was easier to think about it in a declarative manner. As the solution is tail recursive, it's stack safe.

I assume that the text will be short enough to fit into memory, otherwise we would need to read it in some streaming manner from a source (e.g. file).

While testing `IntoWordsSplitter` I figured out that it would be nice to keep the original spaces in the result (initially I was ignoring spaces and split text into words). That's why it keeps the spaces as words with size 1 to build lines using them too.

The domain is rather simple, most operations are done on raw types as adding the Value Classes for them would overcomplicate things. In such a small application it's not worth it. I've added one for the `Limit` to be able to do some initial validation.