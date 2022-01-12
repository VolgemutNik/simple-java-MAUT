package com.sudomeow.maut;

import com.sudomeow.maut.exceptions.MAUTCalculationException;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * MAUTNode represents one branch in the Multi-Attribute Utility Theory decision making model.
 */
public class MAUTNode implements MutableTreeNode, Serializable {

    private MAUTNode parent;
    private final ArrayList<MAUTNode> children;

    private MAUTData mautData = null;

    /**
     * Creates a new MAUTNode Object.
     *
     * @param parent   Parent object in the Tree model.
     * @param nodeName Text that is rendered for this object.
     */
    public MAUTNode(MAUTNode parent, String nodeName) {
        this.parent = parent;
        this.mautData = new MAUTData();
        this.mautData.setNodeName(nodeName);
        children = new ArrayList<>();
    }

    public ArrayList<? extends TreeNode> getChildren() {
        return this.children;
    }

    public void add(MutableTreeNode child) {
        children.add((MAUTNode) child);
    }

    /**
     * Returns an Object that holds all data needed to calculate the final MAUT result.
     *
     * @return The MAUTData Object.
     */
    public MAUTData getMautData() {
        return mautData;
    }

    /**
     * Used for calculating the data of MAUTNode(s) lower than this one in the tree structure.
     *
     * @param altName Name of the alternative we're calculating the results for.
     * @return Result of the calculation.
     * @throws MAUTCalculationException Thrown when mandatory data is missing.
     */
    public double calculate(String altName) throws MAUTCalculationException {
        if (this.mautData.getUsefulnessFunction() == null && this.children.isEmpty()) {
            throw new MAUTCalculationException("Calculation not possible. Add children or a way to calculate the value.");
        }

        if (!this.children.isEmpty()) {
            double sum = 0.0d;
            double weightSum = 0.0;

            for (TreeNode child : children) {
                try {
                    MAUTNode node = (MAUTNode) child;
                    double weight = node.getMautData().getWeight();
                    weightSum += weight;

                    sum += (node.calculate(altName) * weight);
                } catch (MAUTCalculationException e) {
                    e.printStackTrace();
                }
            }

            if (!(weightSum >= 0.99d && weightSum <= 1.01)) //Used range here because of how double values work
                throw new MAUTCalculationException("Summation of weights must be exactly 1!");

            return sum;
        }

        return this.mautData.getUsefulnessFunction().getResult(this.mautData.getMappedValues().get(altName));
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        children.add(index, (MAUTNode) child);
    }

    @Override
    public void remove(int index) {
        children.remove(index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        children.remove(node);
    }

    @Override
    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
            children.forEach(children::remove);
        }
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        this.parent = (MAUTNode) newParent;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(children);
    }

    @Override
    public void setUserObject(Object object) {
        if (object instanceof MAUTData)
            this.mautData = (MAUTData) object;
        else
            try {
                throw new Exception("User Object must be of type MAUTData!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * @return text representation of this object when rendered on screen
     */
    @Override
    public String toString() {
        return this.mautData.toString();
    }
}
