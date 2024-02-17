package view;

public record Pair<A, B>(A row, B column) {
//    @Override
//    public boolean equals(Object other) {
//        if (this == other) return true;
//        if (!(other instanceof Pair))
//            return false;
//        Pair<A, B> that = (Pair) other;
//        return this.row.equals(that.row) && this.column.equals(that.column);
//    }

    @Override
    public int hashCode() {
        return (113*row.hashCode() * column.hashCode())%17;
    }
}
