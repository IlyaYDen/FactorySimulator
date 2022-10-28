package com.example.factorysimulation.models.nodes;

public class Connections {
    ModelNode n1; ModelNode n2;
    public Connections(ModelNode n1, ModelNode n2){
        this.n1 = n1;
        this.n2 = n2;
    }

    public ModelNode getfirst() {
        return n1;
    }
    public ModelNode getSecond() {
        return n2;
    }

    public boolean contains(ModelNode mn) {
        return n1==mn||n2==mn;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Connections c) {
            return ( this.n1.equals(c.n1) && this.n2.equals(c.n2) ) ||
                    (this.n1.equals(c.n2) && this.n2.equals(c.n1));
        }
        return false;
    }
}
